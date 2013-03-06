package api;

import cc.explain.server.core.XmlRpcService;
import org.apache.xmlrpc.XmlRpcException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * User: kzimnick
 * Date: 16.11.12
 * Time: 10:47
 */


public class XmlRpcClientTest {

    @Test
    public void test() throws IOException, XmlRpcException {
        XmlRpcService xmlRpcService = new XmlRpcService();
        String s = xmlRpcService.doTest("Anger Management [1x07] Charlie's Patient Gets Out of Jail.avi", "", "");

        assertTrue(StringUtils.hasText(s));
    }


}
