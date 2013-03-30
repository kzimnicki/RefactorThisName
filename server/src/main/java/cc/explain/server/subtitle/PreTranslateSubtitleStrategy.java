package cc.explain.server.subtitle;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class PreTranslateSubtitleStrategy {
	
	public Subtitle addTranslation(Subtitle subtitle, Map<String,String> translations){
		List<SubtitleElement> subtitleElements = subtitle.getSubtitleElements();
		for (SubtitleElement element : subtitleElements) {
			addTranslation(element, translations);
		}
		return subtitle;
	}
	
	SubtitleElement addTranslation(SubtitleElement subtitleElement,  Map<String,String> translations){
		for (Entry<String, String> entry : translations.entrySet()) {
			boolean containsEnglisWord = SubtitleUtils.findText(subtitleElement.getText(), entry.getKey());
			if(containsEnglisWord){
				subtitleElement.getTranslations().put(entry.getKey(), entry.getValue());
			}
		}
		return subtitleElement;
	}
	
}
