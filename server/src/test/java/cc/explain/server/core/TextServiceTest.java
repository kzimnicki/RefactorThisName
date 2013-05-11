package cc.explain.server.core;

import cc.explain.server.model.RootWord;
import cc.explain.server.model.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cc.explain.server.api.TextService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class TextServiceTest {

    @Inject
    TextService textService;

    @Test
    public void shouldReturnWordFamiliesForTwoRootWords() throws Exception {
         List<String> rootWordValues = new ArrayList<String>(2);
         rootWordValues.add("cart");
         rootWordValues.add("truck");

         Map<String, Set<String>> wordFmilies = textService.getStringWordFamilyForIds(rootWordValues);

         assertTrue(wordFmilies.get("truck").contains("trucks"));
         assertTrue(wordFmilies.get("cart").contains("carts"));
         assertTrue(wordFmilies.get("cart").contains("carting"));
    }

     @Test
    public void shouldReturnRootWordForWord() throws Exception {
         String word = "falling";

         RootWord rootWord = textService.getRootWord(word);

         assertEquals("fall", rootWord.getRootWord().getValue());
    }

      @Test
    public void shouldReturnEmptyWordFamiliesForEmptyList() throws Exception {
         List<String> rootWordValues = new ArrayList<String>();

         Map<String, Set<String>> wordFmilies = textService.getStringWordFamilyForIds(rootWordValues);

         assertEquals(0, wordFmilies.size());
    }

    @Test
    public void shouldReturnEmptyListOfWordsForEmptyStringList() throws Exception {
         List<String> wordValues = new ArrayList<String>();

           List<Word> words = textService.getWords(wordValues);

           assertEquals(0, words.size());
    }



    @Test
    public void shouldReturn2WordsFor2String() throws Exception {
        List<String> wordValues = new ArrayList<String>();
        wordValues.add("car");
        wordValues.add("dog");

        List<Word> words = textService.getWords(wordValues);

        assertEquals(2, words.size());
        assertEquals("car", words.get(0).getValue());
        assertEquals("dog", words.get(1).getValue());
    }

}
