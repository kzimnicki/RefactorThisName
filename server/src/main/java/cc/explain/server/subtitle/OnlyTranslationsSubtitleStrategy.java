package cc.explain.server.subtitle;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OnlyTranslationsSubtitleStrategy implements SubtitleStrategy {

	public Subtitle process(Subtitle subtitle, String pattern, String phrasalVerbPattern) {
		List<SubtitleElement> subtitleElements = subtitle.getSubtitleElements();
		for (SubtitleElement element : subtitleElements) {
			Map<String, String> translations = element.getTranslations();
			StringBuilder builder = new StringBuilder();
			for (Entry<String, String> trans : translations.entrySet()) {
				builder.append(trans.getKey()).append(" = ").append(trans.getValue()).append("\n");
			}
			element.setText(builder.toString());
		}
		return subtitle;
	}

}
