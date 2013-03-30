package cc.explain.server.subtitle;


public interface SubtitleStrategy {
	
	Subtitle process(Subtitle subtitle, String pattern);

}
