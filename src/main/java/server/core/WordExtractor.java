package server.core;

import edu.stanford.nlp.ling.HasWord;
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
import server.api.CommonDao;
import server.api.LoginService;
import server.api.WordDetails;
import server.model.newModel.PhrasalVerb;
import server.model.newModel.User;
import server.model.newModel.Word;
import server.model.newModel.WordFamily;

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

    private static final String FREQUENCY_FILE = "freq.txt";
    public static final Version LUCENE_VER = Version.LUCENE_30;
    public static final String LANGUAGE = "English";
    public static final String EXLUDE_FILE_PATH = "C:\\Users\\kzimnick\\IdeaProjects\\RefactorThisname\\src\\main\\resources\\stoplists\\excludeFile.txt";
    private RAMDirectory idx;
    private HashSet<String> stopWordsSet;
    private WordTypeFrequencyContainer container;


    public WordExtractor() {
        this.stanfordNLP = new StanfordNLP();
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
            container = createContainer();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
        //TODO refactor
    public Set<String> getUserExcludeSet() {
        Set<String> userExcludeSet = new HashSet<String>();
        try {
            User user = loginService.getLoggedUser();
            Set<WordFamily> excludedWords = user.getExcludedWords();
            for (WordFamily wordFamily : excludedWords){
                userExcludeSet.add(wordFamily.getRoot().getValue());
                if(wordFamily.getFamily() != null){
                    for (Word word: wordFamily.getFamily()){
                                   userExcludeSet.add(word.getValue());
                    }
                }
            }

//            userExcludeSet.add(user.loadExcludedWords());
        } catch (Exception e) {
            e.printStackTrace();
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
                list.add(termText);
            }
        }
        return list;
    }

    public List<String> filterWordsToTranslate(List<String> list, int minFrequency, int maxFrequency) throws Exception {
        List<String> debugList = debugWordsToTranslate(list, minFrequency, maxFrequency, false);
        List<String> resultList = new LinkedList<String>();
        int x = 0;
        for (String element : debugList) {
            if (x++ % 3 == 0) {
                resultList.add(element);
            }
        }
        return resultList;
    }

    public Map<String, WordDetails> filterWordsToTranslateWithFrequency(List<String> list, int minFrequency, int maxFrequency) throws Exception {
        List<String> debugList = debugWordsToTranslate(list, minFrequency, maxFrequency, false);
        Map<String, WordDetails> results = new HashMap<String, WordDetails>();
        int x = 0;
        for (String element : debugList) {
            if (x++ % 3 == 0) {
                List<Word> wordFamilyList = container.getWordFamilyFor(element);

                results.put(element, new WordDetails(debugList.get(x + 1), wordFamilyList));
            }
        }
        return results;
    }

    public List<String> debugWordsToTranslate(List<String> wordsToTranslate, int minFrequency, int maxFrequency, boolean includeNoP) {
        List<String> debugList = new LinkedList<String>();
        for (String word : wordsToTranslate) {
            Integer frequency = container.getFrequnecyFor(word.toLowerCase());
            WordType wordType = container.getWordTypeFor(word.toLowerCase());
            if (minFrequency < frequency && frequency < maxFrequency && includeNoPForParameters(includeNoP, wordType)) {
                debugList.add(word);
                debugList.add(String.valueOf(wordType));
                debugList.add(String.valueOf(frequency));
            }
        }
        return debugList;
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

    private WordTypeFrequencyContainer createContainer() throws Exception {
        WordTypeFrequencyContainer container = new WordTypeFrequencyContainer();

        List<Word> allWords = commonDao.getAll(Word.class);
        for (Word word : allWords) {
            Integer frequency = container.getFrequnecyFor(word.getValue());
            if (word.getFrequency() > frequency) {
                container.put(word.getValue(), word);
            }
        }

        List<WordFamily> allWordFamily = commonDao.getAll(WordFamily.class);
        for (WordFamily wordFamily : allWordFamily) {
            container.put(wordFamily.getRoot(), wordFamily);
        }

        return container;
    }

    public static void main(String[] args) throws Exception {
        WordTypeFrequencyContainer container = new WordExtractor().createContainer();
        System.out.println(container);
    }

    public Map<String, WordDetails> addPhrasalVerbs(Map<String, WordDetails> wordsToTranslate, String text) {
        List<List<HasWord>> sentences = stanfordNLP.getSentences(text);
        Map<String, String> phrasalVerbs = stanfordNLP.getPhrasalVerbs(sentences);
        for (String verb : phrasalVerbs.keySet()) {
            wordsToTranslate.remove(verb);
            wordsToTranslate.put(verb + " " + phrasalVerbs.get(verb), new WordDetails("PV", null));
        }
        return wordsToTranslate;
    }

    public Word getWord(String wordValue) {
        return container.getWord(wordValue);
    }

    public WordFamily getWordFamily(String wordValue) {    //TODO refactor.
        Word word = container.getWord(wordValue);
        if(word == null){
            return WordFamily.EMPTY;
        }

        WordFamily wordFamily = container.getWordFamilyFor(word);
        if (wordFamily == null) {
            Map<Word, WordFamily> wordFamilies = container.getWordFamilies();
            for(WordFamily currentWordFamily : wordFamilies.values()){
                if(currentWordFamily.getFamily().contains(word)){
                    return currentWordFamily;
                }
            }
            wordFamily = new WordFamily();
            wordFamily.setRoot(word);
            commonDao.saveOrUpdate(wordFamily);
            container.put(word, wordFamily);
        }
        return wordFamily;
    }
    
    public WordFamily getWordFamilyForPhrasalVerb(String wordValue){
        WordFamily wordFamily = getWordFamily(wordValue);
        WordFamily transportWordFamily = new WordFamily();
        transportWordFamily.setRoot(wordFamily.getRoot());
        for (Word word : wordFamily.getFamily()){
            if(WordType.Verb.equals(word.getWordType())){
                transportWordFamily.getFamily().add(word);
            }
      }
      return transportWordFamily;
    }

   public PhrasalVerb getPhrasalVerb(String phrasalVerbValue){

       String[] pvParts = phrasalVerbValue.split(" ");
       Object[] paramValues = {
               getWordFamily(pvParts[0]),
               getWord(pvParts[1]),
               pvParts.length > 2 ? getWord(pvParts[2]):null
       };
       String[] paramNames = {
               "verb",
               "suffix1",
               "suffix2"
       };
       String query = pvParts.length >2 ? PHRASAL_VERB_QUERY : PHRASAL_VERB_QUERY_SUFFIX2_IS_NULL;
       List phrasalVerbs = commonDao.getByHQL(query, paramNames, paramValues );
        if(phrasalVerbs != null && phrasalVerbs.size() != 0){
            return (PhrasalVerb)phrasalVerbs.get(0);
        }
        return convertValueToPhrasalVerb(phrasalVerbValue);
    }

   private PhrasalVerb convertValueToPhrasalVerb(String phrasalVerbValue){
        String[] wordValues = phrasalVerbValue.split(" ");
        PhrasalVerb phrasalVerb = new PhrasalVerb();
        phrasalVerb.setVerb(getWordFamily(wordValues[0]));
        phrasalVerb.setSuffix1(getWord(wordValues[1]));
        if(wordValues.length == 3){
             phrasalVerb.setSuffix2(getWord(wordValues[2]));
        }
       return phrasalVerb;
    }
}
