package cc.explain.server.subtitle;

import java.io.IOException;
import java.util.Map;

import antlr.RecognitionException;
import cc.explain.server.subtitle.parser.srt.SrtParser;
import org.apache.commons.io.IOUtils;

public abstract class AbstractTest {

	protected Subtitle createSubtitleFromFile(String filename) throws IOException, RecognitionException {
		final String input = IOUtils.toString(AbstractTest.class.getResourceAsStream(filename));
		Subtitle subtitle = new SrtParser().parse(input);
		return subtitle;
	}
	
	protected String read(String filename) throws IOException{
		return IOUtils.toString(AbstractTest.class.getResourceAsStream(filename));
	}
	
	protected Subtitle createTedCaptionsFromFile(String filename) throws IOException, RecognitionException {
		final String input = IOUtils.toString(AbstractTest.class.getResourceAsStream(filename));
		Subtitle subtitle = new SrtParser().parse(input);
		return subtitle;
	}
	
	protected Subtitle createSubtitleFromFileWithTranslations(String filename, Map<String, String> translations) throws IOException, RecognitionException {
		Subtitle subtitle = createSubtitleFromFile(filename);
		subtitle = new PreTranslateSubtitleStrategy().addTranslation(subtitle, translations);
		return subtitle;
	}

}
