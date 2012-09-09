package server.api;

import com.google.common.collect.ForwardingSetMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import server.api.LoginService;
import server.api.WordDetails;
import server.core.CommonDao;
import server.core.StanfordNLP;
import server.core.WordType;
import server.model.newModel.*;

import java.io.StringReader;
import java.util.*;

public class WordExtractor {

    public static final String PHRASAL_VERB_QUERY = "FROM PhrasalVerb pv " +
                                                    "WHERE pv.verb=:verb " +
                                                    "AND pv.suffix1=:suffix1 " +
                                                    "AND pv.suffix2=:suffix2";

    public static final String PHRASAL_VERB_QUERY_SUFFIX2_IS_NULL = "FROM PhrasalVerb pv " +
                                                    "WHERE pv.verb=:verb " +
                                                    "AND pv.suffix1=:suffix1 " +
                                                    "AND pv.suffix2=:suffix2";
    @Autowired
    CommonDao commonDao;

    @Autowired
    LoginService loginService;

    private StanfordNLP stanfordNLP;

    private static final String FREQUENCY_FILE = "files/freq.txt";
    public static final Version LUCENE_VER = Version.LUCENE_30;
    public static final String LANGUAGE = "English";
    public static final String EXLUDE_FILE_PATH = "C:\\Users\\kzimnick\\IdeaProjects\\RefactorThisname\\src\\main\\resources\\stoplists\\excludeFile.txt";
    private RAMDirectory idx;
    private HashSet<String> stopWordsSet;


    public WordExtractor() {
//        this.stanfordNLP = new StanfordNLP();
    }

    public void init() {
        try {
            List<String> stopWords = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("stoplists/stopWords.txt"));
            List<String> onomatopoeicWords = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("stoplists/onomatopoeicWords.txt"));
            List<String> femaleNamesWords = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("stoplists/femaleNames.txt"));
            List<String> maleNamesWords = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("stoplists/maleNames.txt"));
            List<String> surnamesWords = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("stoplists/surnames.txt"));
            stopWordsSet = new HashSet<String>();
            stopWordsSet.addAll(stopWords);
            stopWordsSet.addAll(onomatopoeicWords);
            stopWordsSet.addAll(femaleNamesWords);
            stopWordsSet.addAll(maleNamesWords);
            stopWordsSet.addAll(surnamesWords);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public Set<String> getUserExcludeSet() {
        Map<String, Set<String>> excludedWords = getExcludedWords();
        Set<String> userExcludeSet = new HashSet<String>();
        for (Set<String> exWords : excludedWords.values()){
            userExcludeSet.addAll(exWords);
        }
        return userExcludeSet;
    }

    public void analyseText(String content) throws Exception {
        Set<String> userWordsSet = new HashSet<String>();
        userWordsSet.addAll(stopWordsSet);
        userWordsSet.addAll(getUserExcludeSet());
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer(LUCENE_VER, userWordsSet);
        idx = new RAMDirectory();
        IndexWriter writer = new IndexWriter(idx, standardAnalyzer, MaxFieldLength.UNLIMITED);
        writer.addDocument(createDocument(content));
        writer.optimize();
        writer.close();
    }

    public List<String> getWordsToTranslate() throws Exception {
        IndexReader reader = IndexReader.open(idx);
        TermEnum terms = reader.terms();

        List<String> list = new LinkedList<String>();
        while (terms.next()) {
            Term term = terms.term();
            String termText = term.text();
            if (termText.matches("[A-Za-z-]+") && termText.length() > 2) {
                list.add(termText.toLowerCase());
            }
        }
        return list;
    }

    public Map<String, WordDetails> filterWordsToTranslateWithFrequency(List<String> list, int minFrequency, int maxFrequency) throws Exception {
        List<String> debugList = debugWordsToTranslate(list, minFrequency, maxFrequency, false);
        Map<String, WordDetails> results = new HashMap<String, WordDetails>();
        int x = 0;
        for (String element : debugList) {
            if (x++ % 3 == 0) {
//                Set<Word> wordFamilyList = getWordFamily(element);

                results.put(element, new WordDetails(debugList.get(x + 1)/*, wordFamilyList*/));
            }
        }
        return results;
    }

    public List<String> debugWordsToTranslate(List<String> wordsToTranslate, int minFrequency, int maxFrequency, boolean includeNoP) {
        List<String> debugList = new LinkedList<String>();
        List<Word> words = getWords(wordsToTranslate);
        for (Word w : words) {
            Integer frequency = w.getFrequency();
            WordType wordType = w.getWordType();
            if (minFrequency < frequency && frequency < maxFrequency && includeNoPForParameters(includeNoP, wordType)) {
                debugList.add(w.getValue());
                debugList.add(String.valueOf(wordType));
                debugList.add(String.valueOf(frequency));
            }
        }
        return debugList;
    }

    public List<Word> getWords(List<String> wordValues) {
        if(!wordValues.isEmpty()){
            return (List<Word>)commonDao.getByHQL("FROM Word w WHERE w.value IN :wordValues GROUP BY w.value", "wordValues", wordValues);
        }
        return Collections.<Word>emptyList();
    }

    private boolean includeNoPForParameters(boolean includeNoP, WordType wordType) {
        if (!WordType.NoP.equals(wordType)) {
            return true;
        } else {
            return includeNoP;
        }
    }

    private Document createDocument(String content) {
        Document doc = new Document();
        doc.add(new Field("content", new StringReader(content)));
        return doc;
    }

    public RootWord getRootWord(String wordValue) {
        List wordRelations = commonDao.getByHQL("FROM WordRelation wr WHERE wr.word.value = :wordValue ORDER BY wr.word.frequency", "wordValue", wordValue);
        if(wordRelations.size() > 0){
            WordRelation wr =  (WordRelation)wordRelations.get(0);
            return wr.getRootWord();
        }
        return null;
    }

    public Word getWord(String wordValue) {
        List words = commonDao.getByHQL("FROM Word w WHERE w.value = :wordValue ORDER BY w.frequency", "wordValue", wordValue);
        if(words.size() > 0){
            return (Word)words.get(0);
        }
        return null;
    }

    public WordType getWordType(String wordValue){
        Word word = getWord(wordValue);
        return word != null ? word.getWordType() : WordType.undefined;
    }

    public Integer getFrequency(String wordValue){
         Word word = getWord(wordValue);
        return word != null ? word.getFrequency() : 0;
    }


    public Set<Word> getWordFamily(String wordValue) {
        List wordRelations = new LinkedList();
        wordRelations.addAll(commonDao.getByHQL("FROM WordRelation wr WHERE wr.rootWord.rootWord.value = :wordValue ORDER BY wr.word.frequency", "wordValue", wordValue));
        wordRelations.addAll(commonDao.getByHQL("FROM WordRelation wr WHERE wr.word.value = :wordValue ORDER BY wr.word.frequency", "wordValue", wordValue));
        if(wordRelations.size() > 0){
            Set<Word> wordFamily = new HashSet<Word>(wordRelations.size());
            for (Object o : wordRelations){
                WordRelation wr = (WordRelation)o;
                wordFamily.add(wr.getWord());
            }
            return wordFamily;
        }
        return Collections.<Word>emptySet();
    }

    public Set<Word> getWordFamily(RootWord rootWord) {
        return getWordFamily(rootWord.getRootWord().getValue());
    }

    public Set<String> getStringWordFamily(RootWord rootWord) {
        Set<Word> wordFamily = getWordFamily(rootWord);
        HashSet<String> simpleWordFamily = new HashSet<String>();
        for (Word w : wordFamily){
            simpleWordFamily.add(w.getValue());
        }
        return simpleWordFamily;
    }

    public Map<String, Set<String>> getStringWordFamilyForIds(List<String> wordValues) {
        if(!wordValues.isEmpty()){
            List<WordRelation> wordRelations = (List<WordRelation>)commonDao.getByHQL("FROM WordRelation wr WHERE wr.rootWord.rootWord.value IN :wordValues", "wordValues", wordValues);
            HashMultimap<String, String> multimap = HashMultimap.create();
            for (WordRelation wr : wordRelations){
                multimap.put(wr.getRootWord().getRootWord().getValue(),wr.getWord().getValue());
            }
            return (Map<String, Set<String>>)(Map<?,?>)multimap.asMap();
        }
        return Collections.<String, Set<String>>emptyMap();
    }

    public Map<String, Set<String>> getExcludedWords() {
        User user = loginService.getLoggedUser();
        Set<RootWord> excludedWords = user.getExcludedWords();
        return createWordFamilyForRootWords(excludedWords);
    }

    public Map<String, Set<String>> getIncludedWords() {
        User user = loginService.getLoggedUser();
        Set<RootWord> includedWords = user.getIncludedWords();
        return createWordFamilyForRootWords(includedWords);
    }

    private Map<String, Set<String>> createWordFamilyForRootWords(Set<RootWord> rootWords) {
        List<String> rootWordValues = new ArrayList<String>(rootWords.size());
        for (RootWord rw : rootWords){
            rootWordValues.add(rw.getRootWord().getValue());
        }
        return getStringWordFamilyForIds(rootWordValues);
    }
}
