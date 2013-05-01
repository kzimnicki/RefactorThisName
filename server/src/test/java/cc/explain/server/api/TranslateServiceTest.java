package cc.explain.server.api;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * User: kzimnick
 * Date: 27.04.13
 * Time: 18:26
 */
public class TranslateServiceTest {

    @Test
    public void shouldTranslateEnglishWordToPolish(){
        String english = "get up";

        String translated = new TranslateService().translate(english);

        assertEquals("wstać", translated);
    }

        @Test
    public void shouldTranslateBultEnglishWordsToPolish(){
        String word1 = "get up";
        String word2 = "get down";

        String[] translated = new TranslateService().translate(new String[]{word1, word2});

        assertEquals("wstać", translated[0]);
        assertEquals("schodzić", translated[1]);
    }

}
