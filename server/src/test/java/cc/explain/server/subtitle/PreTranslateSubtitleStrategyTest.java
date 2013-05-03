package cc.explain.server.subtitle;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import antlr.RecognitionException;
import org.junit.Test;

public class PreTranslateSubtitleStrategyTest extends AbstractTest{

	@Test
	public void shouldAddTranslationForSubtitle() throws IOException, RecognitionException {
		Subtitle subtitle = createSubtitleFromFile("/subtitle.srt");
		Map<String, String> translation = new HashMap<String, String>();
		translation.put("night", "noc");
		translation.put("started", "wystartowa�a");
		translation.put("house", "dom");

		Subtitle processedSubtitle = new PreTranslateSubtitleStrategy().addTranslation(subtitle, translation, Collections.<String, String>emptyMap());
		
		assertEquals(2,processedSubtitle.getSubtitleElements().get(0).getTranslations().size());
		assertEquals(2,processedSubtitle.getSubtitleElements().get(0).getTranslations().size());
		assertEquals("noc",processedSubtitle.getSubtitleElements().get(0).getTranslations().get("night"));
		assertEquals("wystartowa�a",processedSubtitle.getSubtitleElements().get(0).getTranslations().get("started"));
		assertEquals(0,processedSubtitle.getSubtitleElements().get(1).getTranslations().size());
		assertEquals(1,processedSubtitle.getSubtitleElements().get(2).getTranslations().size());
		assertEquals("dom",processedSubtitle.getSubtitleElements().get(2).getTranslations().get("house"));
	}
	
	
	@Test
	public void shouldAddTranslationForSubtitleElement() {
		SubtitleElement subtitleElement = new SubtitleElement();
		subtitleElement.setText("I'm the king of the world!!");
		Map<String, String> translations = new HashMap<String, String>();
		translations.put("king", "kr�l");
		translations.put("world", "�wiat");
		translations.put("bazinga", "bazinga");

		SubtitleElement processedElement = new PreTranslateSubtitleStrategy().addTranslations(subtitleElement, translations);
		
		assertEquals(2,processedElement.getTranslations().size());
		assertEquals("kr�l", processedElement.getTranslations().get("king"));
		assertEquals("�wiat", processedElement.getTranslations().get("world"));
	}
	
}
