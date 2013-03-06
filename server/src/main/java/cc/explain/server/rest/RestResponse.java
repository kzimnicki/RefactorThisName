package cc.explain.server.rest;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

public class RestResponse {
    
    private static final String EMPTY_STRING = "";
    private static final String SEMICOLON = ";";
    private BasicHttpResponse response;
    
    protected RestResponse(BasicHttpResponse response) {
       this.response = response;
    }

    public String getContentType() {
        String value = response.getEntity().getContentType().getValue();
        return value.split(SEMICOLON)[0];
    }
    
    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    public String getStatus() {
        return response.getStatusLine().getReasonPhrase();
    }

    public long getContentLength() {
        return response.getEntity().getContentLength();
    }

    public String getContent() {
        try {
            return EntityUtils.toString(response.getEntity()).trim();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY_STRING;
    }
}
