package cc.explain.netflix;

import cc.explain.netflix.redis.Language;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Set;

/**
 * Created by kz on 4/27/15.
 */
public class TextService {

    private final Version LUCENE_VER = Version.LUCENE_30;

    public Set<String> getWords(String text, Language from){
        try {
            return getWordsUsingLucene(text, from);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getWordsUsingLucene(String text, Language from) throws IOException {
        Set<String> stopWords = Sets.newHashSet();
        stopWords.addAll(IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream(String.format("excluded/%s.txt", from))));

        RAMDirectory index = new RAMDirectory();
        IndexWriter writer = new IndexWriter(index, new StandardAnalyzer(LUCENE_VER, stopWords), IndexWriter.MaxFieldLength.UNLIMITED);
        writer.addDocument(createDocument(text));
        writer.optimize();
        writer.close();

        IndexReader reader = IndexReader.open(index);
        TermEnum terms = reader.terms();

        Set<String> words = Sets.newHashSet();

        while(terms.next()) {
            Term term = terms.term();
            String termText = term.text();
            if(termText.length() > 2) {
                words.add(termText);
            }
        }
        return words;
    }

    private Document createDocument(String content) {
        Document doc = new Document();
        doc.add(new Field("content", new StringReader(content)));
        return doc;
    }


    public boolean areSimilar(final String word, final String translation) {
        int distance = LevenshteinDistance.distance(StringUtils.lowerCase(word), StringUtils.lowerCase(translation));
        return (word.length() <5 && distance > 1) || (word.length() >= 5 && distance > 2);
    }
}
