package cc.explain.server.core;

import cc.explain.server.api.PhrasalVerbTask;
import cc.explain.server.api.TextService;
import cc.explain.server.model.RootWord;
import cc.explain.server.model.Word;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: kzimnick
 * Date: 09.09.12
 * Time: 11:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:test-spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
})
public class TextServiceMultithreadTest {

    @Inject
    TextService textService;

    //1 Thread:
    //45sek 44sek
    //43sek
    //4 thread:
    //33s
    @Test
    public void shouldReturnPhrasalVerbsAndUseThreads() throws Exception {
        final String input = IOUtils.toString(this.getClass().getResourceAsStream("/linux.vtt"));

         List<String> phrasalVerbs = new PhrasalVerbTask(input).getPhrasalVerbs(input);

         assertEquals(7, phrasalVerbs.size());
    }

}
