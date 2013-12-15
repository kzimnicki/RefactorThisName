package cc.explain.german;

import cc.explain.server.api.TranslateService;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kz on 12/14/13.
 */
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

    public void release(){
        jedis.disconnect();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        TranslateService service = new TranslateService();
        File file = new File("/home/kz/dev/RefactorThisName/dvbtCC/src/main/resources/converted_words.txt");
        List<String> lines = Files.readLines(file, Charset.defaultCharset());
        Splitter splitter = Splitter.onPattern("[(,)]");
        List germanWords = new LinkedList();

        RedisService redisService = new RedisService();
        redisService.init();


//        System.out.println(redisService.get("knuten"));
//
//        System.exit(0);

        for(String word : lines) {
            if(word.contains("(")){
                Iterable<String> split = splitter.split(word);
                Iterator<String> iterator = split.iterator();
                String first = iterator.next();
                germanWords.add(first);
                while(iterator.hasNext()){
                    String current = first + iterator.next();
                    if (!iterator.hasNext()){
                        continue;
                    }
                    germanWords.add(current);
                }
            }else if (word.contains(",")) {
                //skip
            }
            else{
                germanWords.add(word.split(" ")[0]);
            }
        }
        System.out.println(germanWords.size());
        System.out.println(lines.size());
        List<List<String>> partition = Lists.partition(germanWords, 20);

        for(int i = partition.size()-1; i>= 0; i--){
            System.err.println("Partition: " + i);
            String[] partToTranslate = partition.get(i).toArray(new String[]{});
            String[] translated = service.googleTranslateFromGerman(partToTranslate);
            for(int j=0; j<translated.length; j++ ){
                System.out.println(partToTranslate[j] +" - "+ translated[j]);
                redisService.put(partToTranslate[j], translated[j]);
            }
        }

        redisService.release();

    }
}
