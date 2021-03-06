package cc.explain.server.api;

import cc.explain.server.model.Configuration;
import cc.explain.server.subtitle.InTextTranslationSubtitleStrategy;
import cc.explain.server.subtitle.PreTranslateSubtitleStrategy;
import cc.explain.server.subtitle.Subtitle;
import cc.explain.server.subtitle.SubtitleStrategy;
import cc.explain.server.subtitle.composer.SrtComposer;
import cc.explain.server.subtitle.parser.srt.SrtParser;
import cc.explain.server.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;

/**
 * User: kzimnick
 * Date: 24.11.12
 * Time: 18:25
 */
public class SubtitleService {

    private static Logger LOG = LoggerFactory.getLogger(SubtitleService.class);

    @Autowired
    OpenSubtitlesService openSubtitlesService;

    @Autowired
    SublightService sublightService;
    private SrtParser srtParser;
    private SrtComposer srtComposer;
    private PreTranslateSubtitleStrategy preTranslateSubtitleStrategy;

    public SubtitleService(){
        this.srtParser = new SrtParser();
        this.srtComposer = new SrtComposer();
        this.preTranslateSubtitleStrategy = new PreTranslateSubtitleStrategy();
    }

    public String downloadSubtitles(HashData data) {
        String subtitle = openSubtitlesService.downloadSubtitles(data);
        LOG.debug("Downloaded From opensubtitles: %s", subtitle);
        if (!StringUtils.hasText(subtitle)) {
            subtitle = sublightService.downloadSubtitles(data);
            LOG.debug("Downloaded From sublight: %s", subtitle);
        }
        return subtitle;
    }

    public String addTranslation(String srt, Map<String, String> translations, Map<String, String> phrasalVerbs, Configuration config) {
        Subtitle subtitle = srtParser.parse(srt);
        subtitle = preTranslateSubtitleStrategy.addTranslation(subtitle, translations, phrasalVerbs);
        SubtitleStrategy subtitleStrategy = config.getSubtitleProcessor().getStrategy();
        Subtitle processed = subtitleStrategy.process(subtitle, config.getSubtitleTemplate(), config.getPhrasalVerbTemplate());
        return srtComposer.compose(processed);
    }
}
