package cc.explain.server.rest;

import java.net.URI;
import java.net.URISyntaxException;

import cc.explain.server.exception.TechnicalException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class RestRequest extends HttpEntityEnclosingRequestBase {

    private HttpMethod httpMethod;

    public RestRequest(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getMethod() {
        return httpMethod.getName();
    }


    public RestRequest setUrl(String path) {
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException e) {
            throw new TechnicalException(e.getCause());
        }
        setURI(uri);
        return this;
    }
}
