package cc.explain.server.subtitle;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class PreTranslateSubtitleStrategy {
	
	public Subtitle addTranslation(Subtitle subtitle, Map<String,String> translations, Map<String, String> phrasalVerbs){
		List<SubtitleElement> subtitleElements = subtitle.getSubtitleElements();
		for (SubtitleElement element : subtitleElements) {
			addTranslations(element, translations);
            addPhrasalVerbs(element, phrasalVerbs);
		}
		return subtitle;
	}
	
	SubtitleElement addTranslations(SubtitleElement subtitleElement,  Map<String,String> translations){
		for (Entry<String, String> entry : translations.entrySet()) {
			boolean containsEnglisWord = SubtitleUtils.findText(subtitleElement.getText(), entry.getKey());
			if(containsEnglisWord){
				subtitleElement.getTranslations().put(entry.getKey(), entry.getValue());
			}
		}
		return subtitleElement;
	}

    SubtitleElement addPhrasalVerbs(SubtitleElement subtitleElement, Map<String, String> phrasalVerbs){
        for (Entry<String, String> entry : phrasalVerbs.entrySet()) {
			boolean containsPhrasalVerb = SubtitleUtils.findPhrasalVerb(subtitleElement.getText(), entry.getKey());
			if(containsPhrasalVerb){
				subtitleElement.getPhrasalVerbs().put(entry.getKey(), entry.getValue());
			}
		}
		return subtitleElement;
    }
	
}
