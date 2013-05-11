package cc.explain.server.core;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Word;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * User: kzimnick
 * Date: 13.05.12
 * Time: 15:43
 */
public class StanfordNLPTest {

    @Test
    public void testGetSentencesForEmptyText() {
        String text = "";

        List<List<HasWord>> sentences = new StanfordNLP().getSentences(text);

        assertEquals(0, sentences.size());
    }

    @Test
    public void testGetSentencesFor2SentencesText() {
        String text = "I go to school. My brother name is Thomas.";

        List<List<HasWord>> sentences = new StanfordNLP().getSentences(text);

        assertEquals(2, sentences.size());
    }

    @Test
    public void testGetSentencesForSubtitles() {
        String text =
                "330 00:11:03,579 --> 00:11:05,566 NICK: God, I'm like, I'm back.  " +
                        "331 00:11:05,567 --> 00:11:06,596 Thank you for doing this, actually.  ";

        List<List<HasWord>> sentences = new StanfordNLP().getSentences(text);

        assertEquals(2, sentences.size());
        assertEquals("NICK", sentences.get(0).get(1).word());
        assertEquals("Thank", sentences.get(1).get(1).word());
    }

        @Test
        public void testGetPhrasalVerbs() throws IOException {
            String subtitle = IOUtils.toString(this.getClass().getResourceAsStream("/linux.vtt"));

            StanfordNLP stanfordNLP = new StanfordNLP();
            List<List<HasWord>> sentences = stanfordNLP.getSentences(subtitle);
            List<String> phrasalVerbs = stanfordNLP.getPhrasalVerbs(sentences);

            assertEquals(127, sentences.size());
            assertEquals(7, phrasalVerbs.size());
    }

    @Test
    public void testGetPhrasalVerbsForSentences() {
        String text = "My presentation started well but I dried up quickly.";
        List<List<HasWord>> sentences = new StanfordNLP().getSentences(text);

        List<String> phrasalVerbs = new StanfordNLP().getPhrasalVerbs(sentences);

        assertEquals(1, phrasalVerbs.size());
        assertEquals("dried up", phrasalVerbs.get(0));
    }


    @Test
    public void testGetPhrasalVerbsForEmptyList() {
        List<List<HasWord>> sentences = Lists.newArrayList();

        List<String> phrasalVerbs = new StanfordNLP().getPhrasalVerbs(sentences);

        assertEquals(0, phrasalVerbs.size());
    }

    @Test
    public void testFilterSentece() {
        HasWord word1 = new Word("abc");
        HasWord word2 = new Word("00:11:05,567");
        HasWord word3 = new Word("--");
        List<HasWord> sentence = Lists.newArrayList(word1, word2, word3);

        List<HasWord> words = new StanfordNLP().filterSentence(sentence);

        assertEquals(1,words.size());
        assertEquals(new Word("abc"),words.get(0));
    }



}
