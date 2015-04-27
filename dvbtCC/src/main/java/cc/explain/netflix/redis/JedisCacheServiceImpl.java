package cc.explain.netflix.redis;

import cc.explain.netflix.CacheService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisCacheServiceImpl implements CacheService {

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

    @Override
    public void put(Language from, Language to, String key, String value) {
        put(String.format("%s%s:%s",from, to,  key), value);
    }

    @Override
    public String get(Language from, Language to, String key) {
        return get(String.format("%s%s:%s", from, to, key));
    }
}
