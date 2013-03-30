package cc.explain.server.subtitle;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class InTextTranslationSubtitleStrategy implements SubtitleStrategy {

	public Subtitle process(Subtitle subtitle, String pattern) {
		List<SubtitleElement> subtitleElements = subtitle.getSubtitleElements();
		for (SubtitleElement element : subtitleElements) {
			Map<String, String> translations = element.getTranslations();
			for (Entry<String, String> entry : translations.entrySet()) {
				element.setText(SubtitleUtils.replaceText(element.getText(), entry.getKey(), entry.getValue(), pattern));
			}
		}
		return subtitle;
	}

}
