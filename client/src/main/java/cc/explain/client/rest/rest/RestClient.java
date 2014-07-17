package cc.explain.client.rest.rest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;

public class RestClient {

    private DefaultHttpClient defaultHttpClient;

    public RestClient() {
        PoolingClientConnectionManager conman = new PoolingClientConnectionManager();
        conman.setDefaultMaxPerRoute(15);
        conman.setMaxTotal(30);
        defaultHttpClient = new DefaultHttpClient(conman);
    }

    public RestResponse execute(RestRequest request) {
        RestResponse response = null;
        try {
            BasicHttpResponse apacheResponse = (BasicHttpResponse) defaultHttpClient.execute(request);
            response = new RestResponse(apacheResponse, request, defaultHttpClient.getConnectionManager());
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getCause());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        return response;
    }
}
