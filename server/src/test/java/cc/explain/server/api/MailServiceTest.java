package cc.explain.server.api;

import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * User: kzimnick
 * Date: 22.06.13
 * Time: 11:04
 */
public class MailServiceTest {

    @Test
    public void test() throws Exception {
        MailService mailService = new MailService();
        mailService.init();
        mailService.send("krzys", "Hello", "World");
    }
}
