package cc.explain.server.rest;

import cc.explain.server.exception.TechnicalException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class RestRequest extends HttpEntityEnclosingRequestBase {

    private HttpMethod httpMethod;
    private URIBuilder builder;

    public RestRequest(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getMethod() {
        return httpMethod.getName();
    }


    public RestRequest setUrl(String path) {
        try {
            builder = new URIBuilder(path);
        } catch (URISyntaxException e) {
            throw new TechnicalException(e.getCause());
        }
        setURI(build());
        return this;
    }

    private URI build() {
        try {
            return builder.build();
        } catch (URISyntaxException e) {
            throw new TechnicalException(e.getCause());  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public RestRequest addParam(String key, String value) {
        builder.addParameter(key, value);
        setURI(build());
        return this;
    }
}
