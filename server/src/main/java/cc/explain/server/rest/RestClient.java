package cc.explain.server.rest;

import java.io.IOException;

import cc.explain.server.exception.TechnicalException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;
import org.springframework.http.HttpStatus;

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
            if(response.getStatusCode() != HttpStatus.OK.value()){
                throw new TechnicalException("Subtitles not found.");
            }
        } catch (ClientProtocolException e) {
            throw new TechnicalException(e.getCause());
        } catch (IOException e) {
            throw new TechnicalException(e.getCause());
        }
        return response;
    }
}
