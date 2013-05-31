package cc.explain.server.rest;

import cc.explain.server.exception.TechnicalException;
import cc.explain.server.utils.StringUtils;
import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.eclipse.core.filesystem.URIUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.commons.io.IOUtils;

/**
 * User: kzimnick
 * Date: 07.04.13
 * Time: 17:06
 */
public class Rest {
    private final HttpRequestBase requestBase;
    private final DefaultHttpClient client;
    private URIBuilder uriBuilder;
    private int parameterCount = 0;

    private Rest(HttpRequestBase httpMethod){
        this.requestBase = httpMethod;
        this.client =  new DefaultHttpClient(new PoolingClientConnectionManager());
    }

    public static Rest get(){
        Rest rest = new Rest(new HttpGet());
        return rest;
    }

    public static Rest post(){
        Rest rest = new Rest(new HttpPost());
        return rest;
    }

    public Rest url(String url){
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            throw new TechnicalException(e);
        }
        return this;
    }

    public Rest addParameter(String key, String value){
        parameterCount++;
        uriBuilder.addParameter(key, value);
        return this;
    }

    private boolean shouldReturnSingleElement(){
        return parameterCount == 1;
    }

    public String[] execute(){
        try {
            requestBase.setURI(uriBuilder.build());
            BasicHttpResponse response = (BasicHttpResponse)client.execute(requestBase);

            String content = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            client.getConnectionManager().shutdown();
            return parseResponse(content);
        } catch (IOException e) {
           throw new TechnicalException(e);
        } catch (URISyntaxException e) {
             throw new TechnicalException(e);
        }
    }

    private String[] parseResponse(String content) {
        String[] results;
        if(shouldReturnSingleElement()){
            results = new String[1];
            results[0]=new Gson().fromJson(content, String.class);
        }else{
             results = new Gson().fromJson(content, String[].class);
        }
        return  results;
    }
}
