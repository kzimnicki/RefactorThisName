package cc.explain.server.api;

import cc.explain.server.api.HashData;
import cc.explain.server.api.OpenSubtitlesService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * User: kzimnick
 * Date: 15.11.12
 * Time: 13:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:test-spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
})
public class OpenSubtitlesServiceTest {

    @Autowired
    OpenSubtitlesService openSubtitlesService;

    @Test
    public void shouldReturnCorrectHash() throws IOException {
        HashData data = new HashData();
        List<String> lines = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("HashData.txt"));
        data.setSize(lines.get(0).split("=")[1]);
        data.setHead(lines.get(1));
        data.setTail(lines.get(2));

        String hash = openSubtitlesService.calculateHash(data);

        assertEquals("8e245d9679d31e12", hash);
    }

    @Test
    public void shouldFindCorrectMovie() {
        String hash = "80964db9ce3c9e6e"; //Madagascar 3
        String size = "742848512";

        openSubtitlesService.logInAnnonymously();
        String idSubtitleFile = openSubtitlesService.searchMovieByHash(hash, size);

        assertEquals("1953225164", idSubtitleFile);
    }

    @Test
    public void shouldFindCorrectMovieByFilename() {
        String fileName = "Madagascar.3.Europes.Most.Wanted.2012.DVDRip.XViD-PLAYNOW.avi"; //Madagascar 3

        openSubtitlesService.logInAnnonymously();
        String idSubtitleFile = openSubtitlesService.searchMovieByFilename(fileName);

        assertEquals("1953225164", idSubtitleFile);
    }

    @Test
    public void shouldNotFindCorrectMovieByFilename() {
        String fileName = "badMovieFilename"; //Madagascar 3

        openSubtitlesService.logInAnnonymously();
        String idSubtitleFile = openSubtitlesService.searchMovieByFilename(fileName);

        assertEquals("", idSubtitleFile);
    }

    @Test
    public void shouldLoginUserInApiService() {
        String login = "";
        String password = "";
        String userAgent = OpenSubtitlesService.OS_TEST_USER_AGENT;

        boolean result = openSubtitlesService.logIn(login, password, userAgent);

        assertEquals(true, result);
    }

    @Test
    public void shouldDownloadCorrectSubtitles() {
        String subtitleFileId = "1953225164";

        openSubtitlesService.logInAnnonymously();
        String base64Subtitle = openSubtitlesService.downloadSubtitlesBase64GZIP(subtitleFileId);

        assertEquals("H4sIAAAAAAAAA429zZLj", base64Subtitle.substring(0, 20));
    }

    @Test
    public void shouldDecodeBase64GZIP() {
        String base64GZIP = "H4sIAAAAAAAAAyvOz00tycjMSwcA+zHaCQkAAAA=";

        String result = openSubtitlesService.decodeBase64GZIP(base64GZIP);

        assertEquals("something", result);
    }


}
