package cc.explain.server.subtitle;

import java.util.List;

import org.joda.time.Period;

public class LongDisplayTranslationSubtitleStrategy implements SubtitleStrategy {
	
	private void changeTime(SubtitleElement currentElement, SubtitleElement nextElement) {
		Period period = new Period(currentElement.getEnd(), nextElement.getStart());
		long difference = period.toStandardDuration().getMillis();
		if (difference > 0) {
			if (difference < 2000) {
				currentElement.setEnd(nextElement.getStart());
			} else {
				currentElement.setEnd(currentElement.getEnd().plusMillis(2000));
			}
		}
	}

	public Subtitle process(Subtitle subtitle, String pattern, String phrasalVerbPattern) {
		List<SubtitleElement> subtitleElements = subtitle.getSubtitleElements();
		int size = subtitleElements.size() - 1;
		for (int i = 0; i < size; i++) {			
			changeTime(subtitleElements.get(i), subtitleElements.get(i + 1));
		}
		return subtitle;
	}


}
