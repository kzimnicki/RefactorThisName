package cc.explain.server.subtitle.parser.tedcaptions;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class TedCaptionElement {

    private long duration;
	private String content; 
    private boolean startOfParagraph;
    private long startTime;
    
    public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isStartOfParagraph() {
		return startOfParagraph;
	}
	public void setStartOfParagraph(boolean startOfParagraph) {
		this.startOfParagraph = startOfParagraph;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
