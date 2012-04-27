package server.core;

import org.apache.commons.io.IOUtils;
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
import server.model.User;
import server.model.UserExcludeWord;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

public class WordExtractor {

    @Autowired
    CommonDao commonDao;

    @Autowired
    LoginService loginService;

    private static final String FREQUENCY_FILE = "freq.txt";
    public static final Version LUCENE_VER = Version.LUCENE_30;
    public static final String LANGUAGE = "English";
    public static final String EXLUDE_FILE_PATH = "C:\\Users\\kzimnick\\IdeaProjects\\RefactorThisname\\src\\main\\resources\\stoplists\\excludeFile.txt";
    private RAMDirectory idx;
    private HashSet<String> stopWordsSet;
    private WordTypeFrequencyContainer container;


    public WordExtractor() {
        init();
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

    public Set<String> getUserExcludeSet() {
        Set<String> userExcludeSet = new HashSet<String>();
        try {
            User user = loginService.getLoggedUser();
            List<UserExcludeWord> userExcludeWords = commonDao.getByHQL("From UserExcludeWord uw WHERE uw.user = :user", "user", user);
            for (UserExcludeWord userExcludeWord : userExcludeWords) {
                userExcludeSet.add(userExcludeWord.getWord().getValue());
            }
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
//        StandardAnalyzerWithoutLowerCase standardAnalyzer = new StandardAnalyzerWithoutLowerCase(LUCENE_VER, userWordsSet);
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
//            } else {
//                System.out.println(termText);
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

    //TODO refactor
    public Map<String, WordDetails> filterWordsToTranslateWithFrequency(List<String> list, int minFrequency, int maxFrequency) throws Exception {
        List<String> debugList = debugWordsToTranslate(list, minFrequency, maxFrequency, false);
        Map<String, WordDetails> results = new HashMap<String, WordDetails>();
        int x = 0;
        for (String element : debugList) {
            if (x++ % 3 == 0) {
                results.put(element, new WordDetails(debugList.get(x + 1), container.getWordFamilyFor(element)));
            }
        }
        return results;
    }

    public List<String> debugWordsToTranslate(List<String> wordsToTranslate, int minFrequency, int maxFrequency, boolean includeNoP) {
        List<String> debugList = new LinkedList<String>();
        for (String word : wordsToTranslate) {
            Integer frequency = container.getFrequnecyFor(word);
            Integer frequencyForLowerCase = container.getFrequnecyFor(word.toLowerCase());
            WordType wordType = container.getWordTypeFor(word);
            //szukamy rowniez dla malej litery, czasami w naglowkach pisza z duzej wszystkie litery
            if (frequency < frequencyForLowerCase && includeNoPForParameters(includeNoP, wordType)) {
                frequency = frequencyForLowerCase;
                wordType = container.getWordTypeFor(word.toLowerCase());
            }
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
        BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(FREQUENCY_FILE)));
        String line = "";
        int x = 0;
        WordTypeFrequencyContainer container = new WordTypeFrequencyContainer();
        WordType buffor = null;
        String previousWord = "";
        String previousLine = "";
        while ((line = br.readLine()) != null) {
            String[] elements = line.split("[\t@]+");
            String word = elements[1];
            Integer frequency = extractFrequency(line);
            WordType wordType;
            if (line.startsWith("\t@")) {
                Set<String> wordsFamily;
                if (previousLine.startsWith("\t@")) {
                    wordsFamily = container.getWordFamilyFor(previousWord);
                } else {
                    wordsFamily = container.getWordFamilyFor(previousWord);
                    if(wordsFamily == null){
                          wordsFamily = new HashSet<String>();
                    }
                }
                wordsFamily.add(word);
                container.put(word, wordsFamily);
                wordType = buffor;
            } else {
                wordType = WordType.valueOf(elements[2]);
                buffor = wordType;
            }
            Integer oldFrequency = container.getFrequnecyFor(word);
            if (oldFrequency == null) {
                container.put(word, frequency);
                container.put(word, wordType);
            } else if (oldFrequency < frequency) {
                container.put(word, frequency);
                container.put(word, wordType);
            }

            previousWord = word;
            previousLine = line;
        }
        return container;
    }

    private void createWordFamily(){

    }

    public static void main(String[] args) throws Exception {
        new WordExtractor().createContainer();
    }

    private static Integer extractFrequency(String line) {
        return Integer.valueOf(line.split("\t")[6].split("\\.")[1]);
    }
}
