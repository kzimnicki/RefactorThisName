package cc.explain.server.api;

import cc.explain.server.core.lucene.StandardAnalyzerCaseSensitive;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * User: kzimnick
 * Date: 02.11.12
 * Time: 22:23
 */
public class LuceneService {


    private static final Version LUCENE_VER = Version.LUCENE_30;
    private Set<String> stopWordsSet = Collections.emptySet();

    private RAMDirectory idx;


    public void init() throws IOException {
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
    }

    public void analyseText(Set<String> userExcludeSet, String content) throws IOException {
        Set<String> userWordsSet = new HashSet<String>();
        userWordsSet.addAll(stopWordsSet);
        userWordsSet.addAll(userExcludeSet);
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer(LUCENE_VER, userWordsSet);
        idx = new RAMDirectory();
        IndexWriter writer = new IndexWriter(idx, standardAnalyzer, IndexWriter.MaxFieldLength.UNLIMITED);
        writer.addDocument(createDocument(content));
        writer.optimize();
        writer.close();
    }


    public List<String> getWords(Set<String> excludeSet, String text) throws IOException {
        analyseText(excludeSet, text);

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
    //TODO refactor
    public List<String> getGermanWords(Set<String> excludeSet, String text) throws IOException {
        Set<String> userWordsSet = new HashSet<String>();
        userWordsSet.addAll(stopWordsSet);
        userWordsSet.addAll(excludeSet);
        StandardAnalyzerCaseSensitive standardAnalyzer = new StandardAnalyzerCaseSensitive(LUCENE_VER, userWordsSet);
        idx = new RAMDirectory();
        IndexWriter writer = new IndexWriter(idx, standardAnalyzer, IndexWriter.MaxFieldLength.UNLIMITED);
        writer.addDocument(createDocument(text));
        writer.optimize();
        writer.close();

        IndexReader reader = IndexReader.open(idx);
        TermEnum terms = reader.terms();

        List<String> list = new LinkedList<String>();
        while (terms.next()) {
            Term term = terms.term();
            String termText = term.text();
            if (termText.matches("[A-Za-z-äöüß]+") && termText.length() > 2) {
                list.add(termText);
            }
        }
        return list;
    }

    private Document createDocument(String content) {
        Document doc = new Document();
        doc.add(new Field("content", new StringReader(content)));
        return doc;
    }

}
