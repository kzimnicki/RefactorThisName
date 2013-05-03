package cc.explain.server.subtitle;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import antlr.RecognitionException;
import cc.explain.server.subtitle.parser.srt.SrtParser;
import edu.stanford.nlp.util.Maps;
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
		return createSubtitleFromFile(filename, translations, Collections.<String, String>emptyMap());
	}

    protected Subtitle createSubtitleFromFile(String filename, Map<String, String> translations, Map<String, String> phrasalVerbs) throws IOException, RecognitionException {
		Subtitle subtitle = createSubtitleFromFile(filename);
        PreTranslateSubtitleStrategy preTranslateSubtitleStrategy = new PreTranslateSubtitleStrategy();
        subtitle = preTranslateSubtitleStrategy.addTranslation(subtitle, translations, phrasalVerbs);
		return subtitle;
	}

}
