package cc.explain.server.core;

import cc.explain.server.dto.WordDetailDTO;
import cc.explain.server.model.RootWord;
import cc.explain.server.model.Word;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cc.explain.server.api.TextService;

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
public class TextServiceTest {

    @Inject
    TextService textService;

    @Test
    public void shouldReturnWordFamiliesForTwoRootWords() throws Exception {
         Map<String,String> rootWordValues = Maps.newHashMap();
         rootWordValues.put("cart", new Date().toString());
         rootWordValues.put("truck", new Date().toString());

        List<WordDetailDTO> wordDetailDTOs = textService.getStringWordFamilyForIds(rootWordValues);

        assertEquals(2, wordDetailDTOs.size());

        assertTrue(wordDetailDTOs.get(0).getWordFamily().contains("carts"));
        assertTrue(wordDetailDTOs.get(0).getWordFamily().contains("carting"));
        assertTrue(wordDetailDTOs.get(1).getWordFamily().contains("trucks"));
    }

     @Test
    public void shouldReturnRootWordForWord() throws Exception {
         String word = "falling";

         RootWord rootWord = textService.getRootWord(word);

         assertEquals("fall", rootWord.getRootWord().getValue());
    }

      @Test
    public void shouldReturnEmptyWordFamiliesForEmptyList() throws Exception {
          Map<String,String> rootWordValues = Maps.newHashMap();

          List<WordDetailDTO> wordDetailDTOs = textService.getStringWordFamilyForIds(rootWordValues);

          assertEquals(0, wordDetailDTOs.size());
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
