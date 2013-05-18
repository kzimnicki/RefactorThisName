package cc.explain.server.subtitle;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import cc.explain.server.subtitle.SubtitleUtils;
import org.junit.Test;

public class SubtitleUtilsTest {

	public static final String PATTERN = String.format("(%s)",SubtitleUtils.PATTERN_CORE); // '(@@TRANSLATED_WORD)'

	@Test
	public void shouldReplaceTextForLowerCaseWord() {
		String text = "I'm hungry. Let's get a taco.";
		String english = "hungry";
		String translated = "głodny";
		
		String replacedText = SubtitleUtils.replaceText(text, english, translated, PATTERN);
		
		assertEquals(replacedText, "I'm hungry (głodny). Let's get a taco.");
	}

    @Test
	public void shouldReplaceTextForPhrasalVerb() {
		String pattern = String.format("<font color='red'>%s</font>",SubtitleUtils.PATTERN_CORE);
        String english = "get up";
		String translated = "wstać";

		String replacedText = SubtitleUtils.replacePhrasalVerb(pattern,english, translated);

		assertEquals(replacedText, "<font color='red'>get up = wstać</font>");
	}
	
	@Test
	public void shouldFindTextForLowerCaseWord() {
		String text = "I'm hungry. Let's get a taco.";
		String english = "hungry";
		
		boolean result = SubtitleUtils.findText(text, english);
		
		assertEquals(true, result);
	}
	
	@Test
	public void shouldNotFindTextForNotExisingWord() {
		String text = "I'm hungry. Let's get a taco.";
		String english = "someOtherWord";
		
		boolean result = SubtitleUtils.findText(text, english);
		
		assertEquals(false, result);
	}
	
	@Test
	public void shouldReplaceTextForMarkAtTheEnd() {
		String text = "I take your fucking bullets!";
		String english = "bullets";
		String translated = "naboje";
		
		String replacedText = SubtitleUtils.replaceText(text, english, translated, PATTERN);
		
		assertEquals(replacedText, "I take your fucking bullets (naboje)!");
	}
	
	@Test
	public void shouldFindTextForMarkAtTheEnd() {
		String text = "I take your fucking bullets!";
		String english = "bullets";
		
		boolean result = SubtitleUtils.findText(text, english);
		
		assertEquals(true, result);
	}
	
	@Test
	public void shouldReplaceTextForMarkAtTheBegginig() {
		String text = "I'm kind of a -big deal";
		String english = "big";
		String translated = "duzy";
		
		String replacedText = SubtitleUtils.replaceText(text, english, translated, PATTERN);
		
		assertEquals(replacedText, "I'm kind of a -big (duzy) deal");
	}
	
	@Test
	public void shouldFindTextForMarkAtTheBegginig() {
		String text = "I'm kind of a -big deal";
		String english = "big";
		
		boolean result = SubtitleUtils.findText(text, english);
		
		assertEquals(true, result);
	}
	
	@Test
	public void shouldReplaceTextForUpperCaseWord() {
		String text = "Son, you got a panty on your head.";
		String english = "son";
		String translated = "syn";
		
		String replacedText = SubtitleUtils.replaceText(text, english, translated, PATTERN);
		
		assertEquals(replacedText, "Son (syn), you got a panty on your head.");
	}
	
	@Test
	public void shouldFindTextForUpperCaseWord() {
		String text = "Son, you got a panty on your head.";
		String english = "son";
		
		boolean result = SubtitleUtils.findText(text, english);
		
		assertEquals(true, result);
	}
	
	@Test
	public void shouldNotReplaceWordContainsTranslatedWord() {
		String text = "I aim to misbehave.";
		String english = "behave";
		String translated = "zachować się";
		
		String replacedText = SubtitleUtils.replaceText(text, english, translated, PATTERN);
		
		assertEquals(replacedText, "I aim to misbehave.");
	}
	
	@Test
	public void shouldNotFindWordContainsTranslatedWord() {
		String text = "I aim to misbehave.";
		String english = "behave";
		
		boolean result = SubtitleUtils.findText(text, english);
		
		assertEquals(false, result);
	}
	
	@Test
	public void shouldReplaceTextForMultiplyWords() {
		String text = "I'm the king of the world!!";
		Map<String, String> translations = new HashMap<String, String>();
		translations.put("king", "król");
		translations.put("world", "świat");

		String replacedText = SubtitleUtils.replaceText(text, translations, PATTERN);

		assertEquals(replacedText, "I'm the king (król) of the world (świat)!!");
	}

    @Test
	public void shouldRFindPhrasalVerb() {
		String text = "Sawyer, you're going out a youngster, but you've got to come back a star!";
		String phrasalVerb = "going out";

		boolean result = SubtitleUtils.findPhrasalVerb(text,phrasalVerb);

		assertEquals(result, true);
	}

    @Test
	public void shouldRFindSeparetedPhrasalVerb() {
		String text = "Lenard! Switch it on!";
		String phrasalVerb = "switch on";

		boolean result = SubtitleUtils.findPhrasalVerb(text,phrasalVerb);

		assertEquals(result, true);
	}

}
