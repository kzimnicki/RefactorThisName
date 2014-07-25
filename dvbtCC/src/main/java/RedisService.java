import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

public class RedisService {

    private Jedis jedis;

    public RedisService(){
        init();
    }

    public void init(){
        jedis = new Jedis("localhost");
        reconnect();
    }

    private void put (String key, String value){
        reconnect();
        jedis.set(key, value);
    }

    public void putGermanWordEnglishTranslation( String key, String value){
        put(String.format("DE:%s", key), String.format("EN:%s", value));
    }

    public String getEnglishTranslationForGermanWord( String key){
        return StringUtils.removeStart(get(String.format("DE:%s", key)), "EN:");
    }

    private String get(String key){
        reconnect();
        return jedis.get(key);
    }

    private void reconnect() {
        if(!jedis.isConnected()){
            jedis.connect();
        }
    }

    public void release(){
        jedis.disconnect();
    }
}