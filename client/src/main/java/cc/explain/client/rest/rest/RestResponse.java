package cc.explain.client.rest.rest;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RestResponse {

    private static final String EMPTY_STRING = "";
    private static final String SEMICOLON = ";";
    private BasicHttpResponse response;
    private RestRequest request;
    private ClientConnectionManager conManager;

    protected RestResponse(BasicHttpResponse response, RestRequest request, ClientConnectionManager connectionManager) {
        this.response = response;
        this.request = request;
        this.conManager = connectionManager;
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


    public InputStream getContent() {
        try {
            return response.getEntity().getContent();
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public String getZipContent() {
        try {
            InputStream content = response.getEntity().getContent();
            ZipInputStream zis = new ZipInputStream(content);

            ZipEntry entry;
            StringBuilder builder = new StringBuilder();
            while ((entry = zis.getNextEntry()) != null) {
                int size;
                byte[] buffer = new byte[1024];
                while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                    builder.append(new String(buffer, "UTF-8"));
                }
            }
            zis.close();
            content.close();
            EntityUtils.consume(response.getEntity());
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
