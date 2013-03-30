package cc.explain.server.subtitle;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import antlr.RecognitionException;
import org.junit.Test;

public class InTextTranslationSubtitleStrategyTest extends AbstractTest{

	@Test
	public void shouldProcessSubtitleForTestTranslations() throws IOException, RecognitionException {
		Map<String, String> translations = new HashMap<String, String>();
		translations.put("night", "noc");
		translations.put("started", "wystartowała");
		translations.put("house", "dom");
		Subtitle subtitle = createSubtitleFromFileWithTranslations("/subtitle.srt", translations);
		
		Subtitle processedSubtitle = new InTextTranslationSubtitleStrategy().process(subtitle, SubtitleUtilsTest.PATTERN);
		
		assertEquals("The night (noc) started (wystartowała) like any other.", processedSubtitle.getSubtitleElements().get(0).getText());
		assertEquals("We were downstairs at the Bar.", processedSubtitle.getSubtitleElements().get(1).getText());
		assertEquals("On the house (dom).", processedSubtitle.getSubtitleElements().get(2).getText());
	}

}