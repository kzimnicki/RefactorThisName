package cc.explain.server.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 * User: kzimnick
 * Date: 22.06.13
 * Time: 11:04
 */
public class MailServiceTest {

    @Before
    public void setup() throws NamingException, IOException {
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        Properties prop = new Properties();
        InputStream stream = this.getClass().getResourceAsStream("/jndi.properties");
        prop.load(stream);
        stream.close();

        for(Object key : prop.keySet()){
            builder.bind((String)key, prop.get(key));
        }
        builder.activate();
    }

    @Test
    public void test() throws Exception {
        String testEmail = (String) InitialContext.doLookup("java:comp/env/testEmail");
        MailService mailService = new MailService();
        mailService.init();
        mailService.send(testEmail, "Hello", "World");
    }
}
