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

    public void putGermanWordEnglishTranslation(String key, String value) {
        put(String.format("DE:%s", key), value);
    }

    public String getEnglishTranslationForGermanWord(String key) {
        return get(String.format("DE:%s", key));
    }

    public void putGermanWordPolishTranslation(String key, String value) {
        put(String.format("DEPL:%s", key), value);
    }

    public String getPolishTranslationForGermanWord(String key) {
        return get(String.format("DEPL:%s", key));
    }
}