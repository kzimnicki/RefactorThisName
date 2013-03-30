package cc.explain.server.subtitle.composer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import antlr.RecognitionException;
import cc.explain.server.subtitle.AbstractTest;
import cc.explain.server.subtitle.Subtitle;
import org.joda.time.LocalTime;
import org.junit.Test;


public class SrtComposerTest extends AbstractTest {

	@Test
	public void shouldConvertTimeToString() {
		LocalTime localTime = new LocalTime(11, 22, 33,444);

		String time = new SrtComposer().convertLocalTimeToString(localTime);

		assertEquals("11:22:33,444", time);
	}
	
	@Test
	public void shouldCreateSubtitleText() throws IOException, RecognitionException {
		String filename = "/subtitle.srt";
		Subtitle subtitle = createSubtitleFromFile(filename);

		String textSubtitle = new SrtComposer().compose(subtitle);
		
		assertEquals(read(filename), textSubtitle);
	}

}
