package cc.explain.server.api;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: kzimnick
 * Date: 24.11.12
 * Time: 18:25
 */
public class SubtitleService {

    @Autowired
    OpenSubtitlesService openSubtitlesService;

    public String computeHash(HashData data) {
        String hash = openSubtitlesService.calculateHash(data);
        return hash;
    }
}
