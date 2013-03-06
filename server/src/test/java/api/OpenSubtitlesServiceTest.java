package api;

import cc.explain.server.api.HashData;
import cc.explain.server.core.OpenSubtitlesHasher;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * User: kzimnick
 * Date: 15.11.12
 * Time: 13:16
 */
public class OpenSubtitlesServiceTest {

    @Test
    public void shouldReturnCorrectHash() throws IOException {

        HashData data = new HashData();
        List<String> lines = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("HashData.txt"));
        data.setSize(lines.get(0).split("=")[1]);
        data.setHead(lines.get(1));
        data.setTail(lines.get(2));

        String hash = OpenSubtitlesHasher.computeHash(data);

        assertEquals("f131627b23315325", hash);

    }
}
