package cc.explain.server.subtitle.parser.tedcaptions;

import cc.explain.server.subtitle.Subtitle;
import cc.explain.server.subtitle.SubtitleElement;
import cc.explain.server.subtitle.parser.SubtitleParser;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import com.google.gson.Gson;


public class TedCaptionsParser implements SubtitleParser {

	private int idGenerator = 1;

	public Subtitle parse(String input) {
		TedCaption tedCaption = new Gson().fromJson(input, TedCaption.class);
		Subtitle subtitle = new Subtitle();
		for (TedCaptionElement caption : tedCaption.getCaptions()) {
			SubtitleElement element = convertTedCaptionElementToSubtitleElement( caption);
			subtitle.add(element);
		}
		return subtitle;
	}

	SubtitleElement convertTedCaptionElementToSubtitleElement(TedCaptionElement caption) {
		SubtitleElement element = new SubtitleElement();
		element.setId(calculateId());
		element.setStart(calculateStart(caption.getStartTime()));
		element.setEnd(calculateEnd(caption.getStartTime(), caption.getDuration()));
		element.setText(caption.getContent());
		return element;
	}
	
	LocalTime calculateEnd(long startTime, long duration) {
		return new LocalTime(startTime+duration, DateTimeZone.UTC);
	}

	LocalTime calculateStart(long startTime) {
		return new LocalTime(startTime, DateTimeZone.UTC);
	}

	private Integer calculateId() {
		return idGenerator++;
	}
	
	
}
