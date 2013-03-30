package cc.explain.server.subtitle.parser.srt;
import java.util.LinkedList;
import java.util.List;


public class Srt {
	private String filename;
	private List<SrtElement> subtitleElements = new LinkedList<SrtElement>();
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<SrtElement> getSubtitleElements() {
		return subtitleElements;
	}
	public void setSubtitleElements(List<SrtElement> subtitleElements) {
		this.subtitleElements = subtitleElements;
	}
	public void add(SrtElement element) {
		subtitleElements.add(element);
	}

}
