package cc.explain.netflix;

import cc.explain.netflix.redis.RedissonCacheServiceImpl;
import com.mashape.unirest.http.Unirest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetflixServletTest {

    private Bootstrap bootstrap = new Bootstrap()   ;

    @Before
    public void setup() throws Exception {
        bootstrap.start();
    }

    @After
    public void tearDown() throws Exception {
        bootstrap.stop();
    }

    @Test
    public void testDoGet() throws Exception {
        new RedissonCacheServiceImpl().clear();
        String body = Unirest.get("http://localhost:8888/netflix/?text=car&from=en&to=pl").asString().getBody();
        System.out.println(body);
    }
}