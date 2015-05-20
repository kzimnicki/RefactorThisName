package cc.explain.netflix.redis;

import cc.explain.netflix.CacheService;
import org.redisson.Config;
import org.redisson.Redisson;

import java.util.Map;
import java.util.Optional;

public class RedissonCacheServiceImpl implements CacheService {

    private final Redisson redisson;

    public RedissonCacheServiceImpl(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("127.0.0.1:6379");
        redisson = Redisson.create(config);
    }


    @Override
    public void put(Language from, Language to, String key, String value) {
        getMap(from, to).put(key, value);
    }

    @Override
    public Optional<String> get(Language from, Language to, String key) {
        return Optional.ofNullable(getMap(from, to).get(key));
    }

    @Override
    public void clear() {
        redisson.flushdb();
    }

    private Map<String,String> getMap(Language from , Language to){
        return redisson.getMap(String.format("%s-%s", from, to));
    }

    public static void main(String[] args){
        RedissonCacheServiceImpl service = new RedissonCacheServiceImpl();
        service.put(Language.de, Language.en, "Auto", "car");
        service.put(Language.de, Language.en, "Spaß", "żart");
        service.put(Language.en, Language.de, "dog", "Hund");

        System.out.println(service.get(Language.de, Language.en, "Auto"));
        System.out.println(service.get(Language.de, Language.en, "Spaß"));
        System.out.println(service.get(Language.en, Language.de, "dog"));
    }
}
