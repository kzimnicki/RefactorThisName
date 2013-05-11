package cc.explain.server.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static junit.framework.Assert.assertEquals;

/**
 * User: kzimnick
 * Date: 27.04.13
 * Time: 18:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:test-spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
})
public class TranslateServiceTest {

   @Autowired
   TranslateService translateService;

    @Test
    public void shouldTranslateEnglishWordToPolish(){
        String english = "get up";

        String[] translated = new TranslateService().translate(new String[]{english});

        assertEquals("wstać", translated[0]);
    }

        @Test
    public void shouldTranslateBultEnglishWordsToPolish(){
        String word1 = "get up";
        String word2 = "get down";

        String[] translated = new TranslateService().translate(new String[]{word1, word2});

        assertEquals("wstać", translated[0]);
        assertEquals("schodzić", translated[1]);
    }

    @Test
    public void shouldReturnTranslatedWord() throws Exception {
           String word = "doghouse";

           String translatedWord = translateService.getTranslatedWord(word);

           assertEquals("psia buda", translatedWord);
    }

        @Test
        public void shouldReturnTranslatedWordWithCorrectEncoding() throws Exception {
           String word = "thickness";

           String databaseTranslated = translateService.getTranslatedWord(word);
            String[] googleTranslated = translateService.translate(new String[]{word});

            print(databaseTranslated.getBytes());
            print(googleTranslated[0].getBytes());
            System.out.println(System.getProperty("file.encoding"));


            assertEquals(databaseTranslated, googleTranslated[0]);
    }

    private void print(byte[] bytes){
        for (byte b : bytes){
            System.out.print(b);
        }
        System.out.println();

    }

}
