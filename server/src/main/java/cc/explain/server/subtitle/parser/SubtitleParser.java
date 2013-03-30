package cc.explain.server.subtitle.parser;

import cc.explain.server.subtitle.Subtitle;

public interface SubtitleParser {
	
	Subtitle parse(String input);
}
