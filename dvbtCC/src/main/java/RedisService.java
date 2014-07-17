import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RedisService {

    private Jedis jedis;

    public void init(){
        jedis = new Jedis("localhost");
        jedis.connect();
    }

    void put (String key, String value){
        jedis.set(key, value);
    }

    public String get(String key){
        return jedis.get(key);
    }

    public String getWithEngPrefix(String key){
        return jedis.get(String.format("EN:%s",key));
    }

    public void release(){
        jedis.disconnect();
    }
}