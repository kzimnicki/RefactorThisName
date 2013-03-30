package cc.explain.server.subtitle.parser.tedcaptions;

import static org.junit.Assert.*;

import java.io.IOException;

import antlr.RecognitionException;
import cc.explain.server.subtitle.AbstractTest;
import cc.explain.server.subtitle.Subtitle;
import cc.explain.server.subtitle.SubtitleElement;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalTime;
import org.junit.Test;

public class TedCaptionsParserTest extends AbstractTest{

	@Test
	public void shouldParseFor4elementsTedCaptions() throws IOException, RecognitionException {
		final String input = IOUtils.toString(AbstractTest.class.getResourceAsStream("/tedTalksSubtitles.json"));
		
		Subtitle subtitle = new TedCaptionsParser().parse(input);
		
		assertEquals(4, subtitle.getSubtitleElements().size());
	}
	
	@Test
	public void shouldCalculateStartForLongStartTime() throws IOException, RecognitionException {
		long startTime =  5 * 59 * 59 * 999;
		
		LocalTime time = new TedCaptionsParser().calculateStart(startTime);
		
		assertEquals(new LocalTime(4,49,47,595), time); 
	}
	
	@Test
	public void shouldCalculateStartFor8seconds() throws IOException, RecognitionException {
		long startTime =  8000;
		
		LocalTime time = new TedCaptionsParser().calculateStart(startTime);
		
		assertEquals(new LocalTime(0,0,8,0), time); 
	}
	
	@Test
	public void shouldCalculateEndForLongStartTimeAndDuration() throws IOException, RecognitionException {
		long startTime =  5 * 59 * 59 * 999;
		long duration = 1003;
		
		
		LocalTime time = new TedCaptionsParser().calculateEnd(startTime, duration);
		
		assertEquals(new LocalTime(4,49,48,598), time); 
	}
	
	
	@Test
	public void shouldConvertTedCaptionElementToSubtitleElement() {
		TedCaptionElement tedCaptionElement = new TedCaptionElement();
		tedCaptionElement.setContent("text");
		tedCaptionElement.setDuration(1000);
		tedCaptionElement.setStartTime(8000);
		tedCaptionElement.setStartOfParagraph(false);
		
		SubtitleElement element = new TedCaptionsParser().convertTedCaptionElementToSubtitleElement(tedCaptionElement);
		
		assertEquals("text", element.getText());
		assertEquals(1, element.getId().intValue());
		assertEquals(new LocalTime(0, 0, 8, 0), element.getStart());
		assertEquals(new LocalTime(0, 0, 9, 0), element.getEnd());
	}
	

}
