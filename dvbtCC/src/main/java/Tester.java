import cc.explain.client.rest.rest.HttpMethod;
import cc.explain.client.rest.rest.RestClient;
import cc.explain.client.rest.rest.RestRequest;
import cc.explain.client.rest.rest.RestResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by kz on 7/15/14.
 */
public class Tester {

    public static void main(String[] args) throws IOException {
        RestClient client = new RestClient();

        final String URL1 =  "https://api.datamarket.azure.com/Bing/MicrosoftTranslator/v1/Translate?Text=%27";
        final String URL2 = "%27&To=%27en%27&From=%27de%27&$format=json";


        RestRequest restRequest = new RestRequest(HttpMethod.GET).setUrl(URL1+ "versuchen" +URL2);
        restRequest.addHeader("Authorization", "Basic ZXhwbGFpbmNjQG91dGxvb2suY29tOktqaUUwM0tUUmJOeUhHcG5JdFVKbXNuWFhCWWVpUGh3N2hKUnN6RVBIc3M=");

        RestResponse response = client.execute(restRequest);

//        JsonObject json = new Gson().fromJson(IOUtils.toString(response.getContent()), JsonObject.class);

        JsonObject json = new JsonParser().parse(IOUtils.toString(response.getContent())).getAsJsonObject();
        System.out.println(json.get("d").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("Text"));
    }
}
