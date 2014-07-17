import cc.explain.client.rest.rest.HttpMethod;
import cc.explain.client.rest.rest.RestClient;
import cc.explain.client.rest.rest.RestRequest;
import cc.explain.client.rest.rest.RestResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by kz on 7/15/14.
 */
public class Tester {

    public static void main(String[] args) throws IOException {
        RestClient client = new RestClient();
        String url = "https://api.datamarket.azure.com/Bing/MicrosoftTranslator/v1/Translate?Text=%27probieren%27&To=%27en%27&From=%27de%27&$format=json";
        RestRequest restRequest1 = new RestRequest(HttpMethod.GET).setUrl(url);
        restRequest1.addHeader("Authorization", "Basic ZXhwbGFpbmNjQG91dGxvb2suY29tOktqaUUwM0tUUmJOeUhHcG5JdFVKbXNuWFhCWWVpUGh3N2hKUnN6RVBIc3M=");


        RestResponse response = client.execute(restRequest1);

        System.out.println(IOUtils.readLines(response.getContent()));
    }
}
