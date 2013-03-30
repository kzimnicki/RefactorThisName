package cc.explain.server.subtitle.parser.srt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import antlr.RecognitionException;
import cc.explain.server.subtitle.AbstractTest;
import cc.explain.server.subtitle.Subtitle;
import cc.explain.server.subtitle.SubtitleElement;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalTime;
import org.junit.Test;

public class SrtParserTest extends AbstractTest {
	
	@Test
	public void shouldCreateSubtitleFromString() throws IOException, RecognitionException {
		String filename = "/subtitle.srt";
		final String input = IOUtils.toString(SrtFactoryTest.class.getResourceAsStream(filename));

		Subtitle subtitle = new SrtParser().parse(input);
		
		assertEquals(3, subtitle.getSubtitleElements().size());
	
	}
	
	@Test
	public void shouldConvertSrtElementToSubtitleElement() throws IOException, RecognitionException {
		SrtElement srtElement = new SrtElement();
		srtElement.setId(1);
		srtElement.setStart(new LocalTime(0,0,12,584));
		srtElement.setEnd(new LocalTime(0,0,14,131));
		srtElement.setText("Text");
		
		SubtitleElement element = new SrtParser().convertSrtElementToSubtitleElement(srtElement);

		assertEquals(1, element.getId().intValue());
		assertEquals(new LocalTime(0,0,12,584), element.getStart());
		assertEquals(new LocalTime(0,0,14,131), element.getEnd());
		assertEquals("Text", element.getText());
	}
}
