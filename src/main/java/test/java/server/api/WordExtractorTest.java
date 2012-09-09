package test.java.server.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.api.WordExtractor;
import server.model.newModel.Word;

import javax.inject.Inject;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: kzimnick
 * Date: 09.09.12
 * Time: 11:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:test-spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
})
public class WordExtractorTest {

    @Inject
    WordExtractor wordExtractor;

     @Test
    public void shouldReturnWordFamiliesForTwoRootWords() throws Exception {
         List<String> rootWordValues = new ArrayList<String>(2);
         rootWordValues.add("cart");
         rootWordValues.add("truck");

         Map<String, Set<String>> wordFmilies = wordExtractor.getStringWordFamilyForIds(rootWordValues);

         assertTrue(wordFmilies.get("truck").contains("trucks"));
         assertTrue(wordFmilies.get("cart").contains("carts"));
         assertTrue(wordFmilies.get("cart").contains("carting"));
    }

      @Test
    public void shouldReturnEmptyWordFamiliesForEmptyList() throws Exception {
         List<String> rootWordValues = new ArrayList<String>();

         Map<String, Set<String>> wordFmilies = wordExtractor.getStringWordFamilyForIds(rootWordValues);

         assertEquals(0, wordFmilies.size());
    }

    @Test
    public void shouldReturnEmptyListOfWordsForEmptyStringList() throws Exception {
         List<String> wordValues = new ArrayList<String>();

           List<Word> words = wordExtractor.getWords(wordValues);

           assertEquals(0, words.size());
    }


    @Test
    public void shouldReturn2WordsFor2String() throws Exception {
        List<String> wordValues = new ArrayList<String>();
        wordValues.add("car");
        wordValues.add("dog");

           List<Word> words = wordExtractor.getWords(wordValues);

        assertEquals(2, words.size());
        assertEquals("car", words.get(0).getValue());
        assertEquals("dog", words.get(1).getValue());
    }

}
