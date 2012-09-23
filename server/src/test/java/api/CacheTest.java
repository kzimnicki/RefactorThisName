package api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import server.api.WordExtractor;
import server.core.CommonDao;
import server.api.EnglishTranslator;
import server.model.newModel.Word;

import java.util.Set;

import static junit.framework.Assert.assertEquals;

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
      EnglishTranslator englishTranslator;

    @Autowired
    WordExtractor wordExtractor;

    @Autowired
    CommonDao commonDao;

    @Test
    public void testExtractWordsWithFrequency() throws Exception {
                   StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i=0; i< 200; i++){
              StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
            Set<Word> big = wordExtractor.getWordFamily("big");
            System.out.println(big);
            Set<Word> make = wordExtractor.getWordFamily("make");
            System.out.println(make);
                 stopWatch2.stop();
        System.err.println(stopWatch2.getTotalTimeMillis());
        }
        stopWatch.stop();
        System.err.println(stopWatch.getTotalTimeMillis());


    }

}
