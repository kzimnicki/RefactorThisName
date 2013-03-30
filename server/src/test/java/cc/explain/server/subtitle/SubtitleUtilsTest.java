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
		String text = "I'm hungry. Let�s get a taco.";
		String english = "hungry";
		String translated = "g�odny";
		
		String replacedText = SubtitleUtils.replaceText(text, english, translated, PATTERN);
		
		assertEquals(replacedText, "I'm hungry (g�odny). Let�s get a taco.");
	}
	
	@Test
	public void shouldFindTextForLowerCaseWord() {
		String text = "I'm hungry. Let�s get a taco.";
		String english = "hungry";
		
		boolean result = SubtitleUtils.findText(text, english);
		
		assertEquals(true, result);
	}
	
	@Test
	public void shouldNotFindTextForNotExisingWord() {
		String text = "I'm hungry. Let�s get a taco.";
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
		String text = "I�m kind of a -big deal";
		String english = "big";
		String translated = "duzy";
		
		String replacedText = SubtitleUtils.replaceText(text, english, translated, PATTERN);
		
		assertEquals(replacedText, "I�m kind of a -big (duzy) deal");
	}
	
	@Test
	public void shouldFindTextForMarkAtTheBegginig() {
		String text = "I�m kind of a -big deal";
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
		String translated = "zachowa� si�";
		
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
		translations.put("king", "kr�l");
		translations.put("world", "�wiat");
		
		String replacedText = SubtitleUtils.replaceText(text, translations, PATTERN);
		
		assertEquals(replacedText, "I'm the king (kr�l) of the world (�wiat)!!");
	}

}
