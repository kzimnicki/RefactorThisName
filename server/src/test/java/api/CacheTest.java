package api;

import cc.explain.server.model.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import cc.explain.server.api.ExplainCCApi;
import cc.explain.server.api.TextService;
import cc.explain.server.core.CommonDao;

import java.util.Set;

/**
 * User: kzimnick
 * Date: 04.09.12
 * Time: 21:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:test-spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
})
public class CacheTest {

    @Autowired
    ExplainCCApi api;

    @Autowired
    TextService textService;

    @Autowired
    CommonDao commonDao;

    @Test
    public void testExtractWordsWithFrequency() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 200; i++) {
            StopWatch stopWatch2 = new StopWatch();
            stopWatch2.start();
            Set<Word> big = textService.getWordFamily("big");
            System.out.println(big);
            Set<Word> make = textService.getWordFamily("make");
            System.out.println(make);
            stopWatch2.stop();
            System.err.println(stopWatch2.getTotalTimeMillis());
        }
        stopWatch.stop();
        System.err.println(stopWatch.getTotalTimeMillis());
    }

}
