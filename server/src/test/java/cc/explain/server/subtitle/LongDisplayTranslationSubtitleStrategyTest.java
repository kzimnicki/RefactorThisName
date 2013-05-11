package cc.explain.server.subtitle;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import antlr.RecognitionException;
import org.joda.time.LocalTime;
import org.junit.Test;

public class LongDisplayTranslationSubtitleStrategyTest extends AbstractTest{

	@Test
	public void shouldCreateSubtitleWithLongDisplayText() throws IOException, RecognitionException {
		Subtitle subtitle = createSubtitleFromFile("/subtitle.srt");
		
		Subtitle longDisplaySubtitle = new LongDisplayTranslationSubtitleStrategy().process(subtitle, null, null);

		assertEquals(new LocalTime(00, 00, 12, 584), longDisplaySubtitle.getSubtitleElements().get(0).getEnd());
		assertEquals(new LocalTime(00, 00, 16, 131), longDisplaySubtitle.getSubtitleElements().get(1).getEnd());
		assertEquals(new LocalTime(00, 00, 19, 408), longDisplaySubtitle.getSubtitleElements().get(2).getEnd());
	}
}
