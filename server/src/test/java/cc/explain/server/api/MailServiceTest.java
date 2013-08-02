package cc.explain.server.api;

import cc.explain.server.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import org.apache.commons.io.IOUtils;

/**
 * User: kzimnick
 * Date: 22.06.13
 * Time: 11:04
 */
public class MailServiceTest {

    @Before
    public void setup() throws NamingException, IOException {
        TestUtils.initTestJNDIFromPropertiesFile();
    }

    @Test
    public void test() throws Exception {
        String testEmail = (String) InitialContext.doLookup("java:comp/env/testEmail");
        MailService mailService = new MailService();
        mailService.send(testEmail, "Hello", "World");
    }
}
