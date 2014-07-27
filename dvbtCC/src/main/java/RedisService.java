import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisService {

    private JedisPool pool;

    public RedisService(){
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
    }

    private void put (String key, String value){
        Jedis jedis = pool.getResource();
        try {
            jedis.set(key, value);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    private String get(String key){
        Jedis jedis = pool.getResource();
        try {
            return jedis.get(key);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public void putGermanWordEnglishTranslation( String key, String value){
        put(String.format("DE:%s", key), String.format("EN:%s", value));
    }

    public String getEnglishTranslationForGermanWord( String key){
        return StringUtils.removeStart(get(String.format("DE:%s", key)), "EN:");
    }
}