package cc.explain.server.api;

import cc.explain.server.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    public String downloadSubtitles(HashData data) {
        String subtitle = openSubtitlesService.downloadSubtitles(data);
        LOG.debug("Downloaded From opensubtitles: %s", subtitle);
        if (!StringUtils.hasText(subtitle)) {
            subtitle = sublightService.downloadSubtitles(data);
            LOG.debug("Downloaded From sublight: %s", subtitle);
        }
        return subtitle;
    }
}
