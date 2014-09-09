import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisService {

    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 2000);

    private void put(String key, String value) {
        Jedis jedis = pool.getResource();
        jedis.set(key, value);
        pool.returnResource(jedis);
    }

    private String get(String key) {
        Jedis jedis = pool.getResource();
        String value = jedis.get(key);
        pool.returnResource(jedis);
        return value;
    }

    public void puTranslation(String languagePrefix, String key, String value) {
        put(String.format("%s:%s",languagePrefix,  key), value);
    }

    public String getTranslation(String languagePrefix, String key) {
        return get(String.format("%s:%s", languagePrefix, key));
    }
}