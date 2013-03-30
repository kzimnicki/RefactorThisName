package cc.explain.server.subtitle.parser.srt;

import cc.explain.server.subtitle.Subtitle;
import cc.explain.server.subtitle.SubtitleElement;
import cc.explain.server.subtitle.parser.SubtitleParser;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import srt.Antrl3SrtLexer;
import srt.Antrl3SrtParser;

import java.util.List;

public class SrtParser implements SubtitleParser {

	public Subtitle parse(String input) {
		final Antrl3SrtLexer lexer = new Antrl3SrtLexer(new ANTLRStringStream(
				input));
		final CommonTokenStream stream = new CommonTokenStream(lexer);
		final Antrl3SrtParser parser = new Antrl3SrtParser(stream);
		Subtitle subtitle = new Subtitle();
		try {
			parser.parse();
			List<SrtElement> srtElements = parser.srtElements;

			for (SrtElement srtElement : srtElements) {
				SubtitleElement subtitleElement = convertSrtElementToSubtitleElement(srtElement);
				subtitle.add(subtitleElement);
			}
		} catch (RecognitionException e) {
			e.printStackTrace(); // TODO change to technical exception.
		}
		return subtitle;
	}

	SubtitleElement convertSrtElementToSubtitleElement(
			SrtElement srtElement) {
		SubtitleElement subtitleElement = new SubtitleElement();
		subtitleElement.setId(srtElement.getId());
		subtitleElement.setStart(srtElement.getStart());
		subtitleElement.setEnd(srtElement.getEnd());
		subtitleElement.setText(srtElement.getText());
		return subtitleElement;
	}

}
