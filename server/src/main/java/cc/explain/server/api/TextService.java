package cc.explain.server.api;

import cc.explain.server.core.CommonDao;
import cc.explain.server.core.WordType;
import cc.explain.server.model.*;
import com.google.common.collect.HashMultimap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

public class TextService {

    @Autowired
    TextDAO textDAO;

    @Autowired
    CommonDao commonDao;

    @Autowired
    LuceneService luceneService;

    public Set<String> getExcludeSet(User user) {
        Map<String, Set<String>> excludedWords = getExcludedWords(user);
        Set<String> userExcludeSet = new HashSet<String>();
        for (Set<String> exWords : excludedWords.values()) {
            userExcludeSet.addAll(exWords);
        }
        return userExcludeSet;
    }

    public String createCSVString(Map<String, Set<String>> excludedWords) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : excludedWords.entrySet()) {
            builder.append(entry.getKey());
            builder.append(";");
            builder.append(StringUtils.join(entry.getValue(), " "));
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

    public Map<String, Set<String>> getStringWordFamilyForIds(List<String> wordValues) {
        if (!wordValues.isEmpty()) {
            List<WordRelation> wordRelations = textDAO.findWordRelationsByRootWordValues(wordValues);
            HashMultimap<String, String> multimap = HashMultimap.create();
            for (WordRelation wr : wordRelations) {
                multimap.put(wr.getRootWord().getRootWord().getValue(), wr.getWord().getValue());
            }
            return (Map<String, Set<String>>) (Map<?, ?>) multimap.asMap();
        }
        return Collections.<String, Set<String>>emptyMap();
    }

    public Map<String, Set<String>> getExcludedWords(User user) {
        Set<RootWord> excludedWords = user.getExcludedWords();
        return createWordFamilyForRootWords(excludedWords);
    }

    public Map<String, Set<String>> getIncludedWords(User user) {
        Set<RootWord> includedWords = user.getIncludedWords();
        return createWordFamilyForRootWords(includedWords);
    }

    private Map<String, Set<String>> createWordFamilyForRootWords(Set<RootWord> rootWords) {
        List<String> rootWordValues = new ArrayList<String>(rootWords.size());
        for (RootWord rw : rootWords) {
            rootWordValues.add(rw.getRootWord().getValue());
        }
        return getStringWordFamilyForIds(rootWordValues);
    }

    public String getTranslatedWord(String word) {
        List<Translation> translatedWords = textDAO.findTranslations(word);
        if (translatedWords.size() > 0) {
            return translatedWords.get(0).getTransWord();
        }
        return word;
    }

    public List<String[]> getTranslatedWord(List<String> words) {
        ArrayList<String[]> translatedWords = new ArrayList<String[]>(words.size());
        for (String w : words) {
            translatedWords.add(new String[]{getTranslatedWord(w)});
        }
        return translatedWords;
    }
}
