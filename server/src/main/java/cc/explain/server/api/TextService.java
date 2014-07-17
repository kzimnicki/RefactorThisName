package cc.explain.server.api;

import cc.explain.server.core.CommonDao;
import cc.explain.server.core.WordType;
import cc.explain.server.dto.WordDetailDTO;
import cc.explain.server.model.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TextService {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    TextDAO textDAO;

    @Autowired
    CommonDao commonDao;

    @Autowired
    LuceneService luceneService;

    public Set<String> getExcludeSet(User user) {
        List<WordDetailDTO> excludedWords = getExcludedWords(user);
        Set<String> userExcludeSet = new HashSet<String>();
        for (WordDetailDTO wd  : excludedWords) {
            userExcludeSet.add(wd.getRootWord());
            userExcludeSet.addAll(wd.getWordFamily());
        }
        return userExcludeSet;
    }

    public String createCSVString(List<WordDetailDTO> excludedWords) {
        StringBuilder builder = new StringBuilder();
        for (WordDetailDTO wd : excludedWords) {
            builder.append(wd.getRootWord());
            builder.append(";");
            builder.append(StringUtils.join(wd.getWordFamily(), " "));
            builder.append(";\n");
        }
        return builder.toString();
    }

    public Map<String, WordDetails> getWordsToTranslate(User user, String text) throws IOException {
        Set<String> userExcludeSet = getExcludeSet(user);
        List<String> list = luceneService.getWords(userExcludeSet, text);
        Map<String, WordDetails> wordsToTranslate = filterWordsToTranslateWithFrequency(list,
                user.getConfig().getMin(),
                user.getConfig().getMax());

        return wordsToTranslate;
    }



    public Map<String, WordDetails> filterWordsToTranslateWithFrequency(List<String> list, int minFrequency, int maxFrequency) {
        Map<String, WordDetails> results = new HashMap<String, WordDetails>();
        List<Word> words = getWords(list);
        for (Word w : words) {
            Integer frequency = w.getFrequency();
            WordType wordType = w.getWordType();
            if (minFrequency <= frequency && frequency <= maxFrequency && wordType != WordType.NoP) {
                results.put(w.getValue(), new WordDetails(frequency));
            }
        }
        return results;
    }


    public List<Word> getWords(List<String> wordValues) {
        List<Word> words = Collections.<Word>emptyList();
        if (!wordValues.isEmpty()) {
            words = textDAO.findWordByWordValues(wordValues);
        }
        return words;
    }

    public RootWord getRootWord(String wordValue) {
        List<WordRelation> wordRelations = textDAO.findWordRelationByWordValue(wordValue);
        TreeMap<Integer, RootWord> sortRootWordContainer = new TreeMap<Integer, RootWord>();
        for (WordRelation wr : wordRelations) {
            List family = textDAO.findWordRelationByRootWord(wr.getRootWord());
            sortRootWordContainer.put(family.size(), wr.getRootWord());
        }
        return sortRootWordContainer.size() > 0 ? sortRootWordContainer.get(sortRootWordContainer.lastKey()) : null;
    }

    public Set<Word> getWordFamily(String wordValue) {
        List wordRelations = new LinkedList();
        wordRelations.addAll(textDAO.findWordRelationByRootWordValue(wordValue));
        wordRelations.addAll(textDAO.findWordRelationByWordValue(wordValue));
        Set<Word> wordFamily = new HashSet<Word>(wordRelations.size());
        for (Object o : wordRelations) {
            WordRelation wr = (WordRelation) o;
            wordFamily.add(wr.getWord());
        }
        return wordFamily;
    }

    public List<WordDetailDTO> getStringWordFamilyForIds(Map<String,String> wordValues) {
        if (!wordValues.isEmpty()) {
            List<WordRelation> wordRelations = textDAO.findWordRelationsByRootWordValues(wordValues.keySet());
            WordDetailDTO wordDetail = new WordDetailDTO();
            List<WordDetailDTO> wd = Lists.newArrayListWithCapacity(wordRelations.size());
            for (WordRelation wr : wordRelations) {
                if(!StringUtils.equals(wordDetail.getRootWord(), wr.getRootWord().getRootWord().getValue())){
                    wordDetail = new WordDetailDTO();
                    wordDetail.setFrequency(wr.getRootWord().getRootWord().getFrequency());
                    wordDetail.setRootWord(wr.getRootWord().getRootWord().getValue());
                    wordDetail.setAddDate(wordValues.get(wr.getRootWord().getRootWord().getValue()));
                    wd.add(wordDetail);
                }
                wordDetail.addWordFamily(wr.getWord().getValue());
            }
            return wd;
        }
        return Collections.<WordDetailDTO>emptyList();
    }

    public List<WordDetailDTO> getExcludedWords(User user) {
        Set<UserExcludeWord> excludedWords = user.getExcludedWords();
        return createWordFamilyForRootWords(excludedWords);
    }

    public List<WordDetailDTO> getIncludedWords(User user) {
        Set<UserIncludeWord> includedWords = user.getIncludedWords();
        return createWordFamilyForRootWords(includedWords);
    }

    private List<WordDetailDTO> createWordFamilyForRootWords(Set<? extends UserWord> userWords) {
        Map<String,String> rootWordValues = new HashMap<String,String>(userWords.size());
        for (UserWord uw : userWords) {
            rootWordValues.put(uw.getRootWord().getRootWord().getValue(), formatDate(uw.getCreated()));
        }
        return getStringWordFamilyForIds(rootWordValues);
    }

    private String formatDate(Date created) {
        return format.format(created);
    }

}
