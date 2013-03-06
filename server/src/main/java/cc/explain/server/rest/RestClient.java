package cc.explain.server.rest;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;

public class RestClient {

    private DefaultHttpClient defaultHttpClient;

    public RestClient() {
        defaultHttpClient = new DefaultHttpClient();
    }

    public RestResponse execute(RestRequest request) {
        RestResponse response = null;
        try {
            BasicHttpResponse apacheResponse = (BasicHttpResponse) defaultHttpClient.execute(request);
            response = new RestResponse(apacheResponse);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
