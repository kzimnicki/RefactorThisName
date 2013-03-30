package cc.explain.server.subtitle;
import java.util.LinkedList;
import java.util.List;


public class Subtitle {
	private String filename;
	private List<SubtitleElement> subtitleElements = new LinkedList<SubtitleElement>();
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<SubtitleElement> getSubtitleElements() {
		return subtitleElements;
	}
	public void setSubtitleElements(List<SubtitleElement> subtitleElements) {
		this.subtitleElements = subtitleElements;
	}
	public void add(SubtitleElement element) {
		subtitleElements.add(element);
	}

}
