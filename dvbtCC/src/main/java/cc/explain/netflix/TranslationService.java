package cc.explain.netflix;

import cc.explain.client.rest.rest.HttpMethod;
import cc.explain.client.rest.rest.RestClient;
import cc.explain.client.rest.rest.RestRequest;
import cc.explain.client.rest.rest.RestResponse;
import cc.explain.netflix.redis.Language;
import cc.explain.netflix.redis.RedissonCacheServiceImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * Created by kz on 4/27/15.
 */
public class TranslationService {

    private RestClient client = new RestClient();
    private static final String URL_PATTERN = "https://api.datamarket.azure.com/Bing/MicrosoftTranslator/v1/Translate?Text=%27{2}%27&To=%27{1}%27&From=%27{0}%27&$format=json";

    private CacheService cacheService = new RedissonCacheServiceImpl();

    public String translate(Language from, Language to, String word) throws IOException {
        Optional<String> cacheTranslation = cacheService.get(from, to, word);
        if(cacheTranslation.isPresent()){
            return cacheTranslation.get();
        }
        String translation = executeUrl(MessageFormat.format(URL_PATTERN, from, to, word));
        cacheService.put(from, to, word, translation);
        return translation;
    }

    public String executeUrl(String url) throws IOException {
        String translation = StringUtils.EMPTY;
//        RestRequest restRequest = new RestRequest(HttpMethod.GET).setUrl(url);
//        restRequest.addHeader();
//        RestResponse response = client.execute(restRequest);

        try {
            System.out.println("translate");
            System.out.println(url);
            System.out.println(Unirest.get(url).header("Authorization", "Basic ZXhwbGFpbmNjQG91dGxvb2suY29tOktqaUUwM0tUUmJOeUhHcG5JdFVKbXNuWFhCWWVpUGh3N2hKUnN6RVBIc3M=").asJson().getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }


//        JsonObject json = new JsonParser().parse(IOUtils.toString(response.getContent())).getAsJsonObject();
//        translation = json.get("d").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("Text").getAsString();
        return translation;
    }
}
