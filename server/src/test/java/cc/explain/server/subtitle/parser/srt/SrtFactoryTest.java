package cc.explain.server.subtitle.parser.srt;
import static org.junit.Assert.assertEquals;

import cc.explain.server.subtitle.AbstractTest;
import org.joda.time.LocalTime;
import org.junit.Test;

public class SrtFactoryTest extends AbstractTest {

	@Test
	public void shouldCreateSubtitleElement() {
		String id = "1";
		String start = "00:00:01,111";
		String end = "00:00:02,222";
		String text = "I'm Bond, James Bond";

		SrtElement element = new SrtFactory().createSrtElement(
				id, start, end, text);

		assertEquals(new Integer(1), element.getId());
		assertEquals(new LocalTime(0, 0, 1, 111), element.getStart());
		assertEquals(new LocalTime(0, 0, 2, 222), element.getEnd());
		assertEquals(text, element.getText());
	}
	
	@Test
	public void shouldConvertStringToTime() {
		String value = "11:22:33,444";

		LocalTime time = new SrtFactory().convertStringToLocalTime(value);

		assertEquals(new LocalTime(11, 22, 33, 444), time);
	}
	

}
