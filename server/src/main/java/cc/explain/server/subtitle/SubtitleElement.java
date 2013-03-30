package cc.explain.server.subtitle;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalTime;

public class SubtitleElement {

	private Integer id;
	private LocalTime start;
	private LocalTime end;
	private String text;
	private Map<String,String> translations = new HashMap<String,String>();
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: ").append(getId()).append(", ");
		builder.append("Time: ").append(getStart()).append(" - ").append(getEnd()).append(", ");
		builder.append("Text: ").append(getText());
		return builder.toString();
	}

	public Map<String,String> getTranslations() {
		return translations;
	}

	public void setTranslations(Map<String,String> translations) {
		this.translations = translations;
	}

}
