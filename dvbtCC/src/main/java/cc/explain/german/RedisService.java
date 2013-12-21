package cc.explain.german;

import cc.explain.server.api.TranslateService;
import com.google.common.base.Predicate;
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

    public String getWithEngPrefix(String key){
        return jedis.get(String.format("EN:%s",key));
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

        final RedisService redisService = new RedisService();
        redisService.init();

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
        List<List<String>> partition = Lists.partition(germanWords, 160);

        for(int i = partition.size()-1; i>= 0; i--){
            System.err.println("Partition: " + i);
            List<String> partToTranslate = partition.get(i);
            List<String> filtered =Lists.newArrayList(Iterables.filter(partToTranslate, new Predicate<String>() {
                @Override
                public boolean apply(String key) {
                    String value = redisService.getWithEngPrefix(key);
                    return value.equals(key);
                }
            }));
            System.err.println("filtered: " + filtered.size());
            String[] filteredArray =  filtered.toArray(new String[filtered.size()]);
            String[] translated = service.googleTranslateFromGermanToEnglish(filteredArray);
            for(int j=0; j<translated.length; j++ ){
                System.out.println(filteredArray[j] +" - "+ translated[j]);
                if(!filteredArray[j].equals(translated[j])){
                    System.exit(0);
                    redisService.put("EN"+":"+filteredArray[j], translated[j]);
                }
//
            }
        }

        redisService.release();

    }
}
