package cc.explain.german

import cc.explain.server.api.TranslateService
import redis.clients.jedis.Jedis

class RedisService {
    private Jedis jedis

    def init(){
        jedis = new Jedis("localhost");
        jedis.connect();
    }

    def put (String key, String value){
        jedis.set(key, value)
    }

    def get(String key){
        return jedis.get(key)
    }

    def release(){
        jedis.disconnect()
    }

    public static void main(String[] args){
        println new cc.explain.server.api.TranslateService().googleTranslate(new String()["car","dog"])
        new RedisService().init();
    }
}
