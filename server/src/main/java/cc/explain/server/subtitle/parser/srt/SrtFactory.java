package cc.explain.server.subtitle.parser.srt;


import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;



public class SrtFactory {

	private final DateTimeFormatter formatter = DateTimeFormat
			.forPattern("HH:mm:ss,SSS");

	public SrtElement createSrtElement(String id, String start, String end, String text) {

		SrtElement subtitleElement = new SrtElement();
		subtitleElement.setId(Integer.valueOf(id));
		subtitleElement.setStart(convertStringToLocalTime(start));
		subtitleElement.setEnd(convertStringToLocalTime(end));
		subtitleElement.setText(text);

		return subtitleElement;
	}

	LocalTime convertStringToLocalTime(String text) {
		return formatter.parseLocalTime(text);
	}
}
