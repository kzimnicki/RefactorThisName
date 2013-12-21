package cc.explain.server.api;

import cc.explain.server.exception.TechnicalException;
import cc.explain.server.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * User: kzimnick
 * Date: 24.11.12
 * Time: 20:05
 */
public class OpenSubtitlesService {

    private static final int HASH_CHUNK_SIZE = 64 * 1024;
    public static final String API_LOG_IN = "LogIn";
    public static final String API_SEARCH_SUBTITLES = "SearchSubtitles";
    public static final String OS_TEST_USER_AGENT = "OS Test User Agent";

    private String login;
    private String password;
    private String userAgent;
    private String apiUrl;


    private XmlRpcClient xmlrpcClient;
    private String token;

    private long computeHashForChunk(ByteBuffer buffer) {
        LongBuffer longBuffer = buffer.order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();
        long hash = 0;
        int x = 0;
        while (longBuffer.hasRemaining()) {
            long value = longBuffer.get();
            hash += value;
        }
        return hash;
    }

    public String calculateHash(HashData data) {
        byte[] headBytes = Base64.decodeBase64(data.getHead().split(",")[1]);

        byte[] tailBytes = Base64.decodeBase64(data.getTail().split(",")[1]);
        long head = computeHashForChunk(ByteBuffer.wrap(headBytes, 0, HASH_CHUNK_SIZE));
        long tail = computeHashForChunk(ByteBuffer.wrap(tailBytes, 0, HASH_CHUNK_SIZE));

        return String.format("%016x", Long.valueOf(data.getSize()) + head + tail);
    }


    public String searchMovieByHash(String hash, String length) {
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("moviebytesize", length);
        map1.put("moviehash", hash);
        map1.put("sublanguageid", "eng");
        Object[] params = new Object[]{token, new Object[]{map1}};
        Map<String, Object> result = xmlRpcExecute(API_SEARCH_SUBTITLES, params);
        Object data = result.get("data");
        if (!(data instanceof Boolean)) {
            Map<String, Object> dataElement = (Map<String, Object>) ((Object[]) data)[0];
            return (String) dataElement.get("IDSubtitleFile");
        }
        return StringUtils.EMPTY;
    }

    private Map<String, Object> xmlRpcExecute(String methodName, Object[] params) {
        try {
            return (Map<String, Object>) xmlrpcClient.execute(methodName, params);
        } catch (XmlRpcException e) {
            throw new TechnicalException(e.getCause());
        }
    }

    private void init() {
        xmlrpcClient = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        try {
            config.setServerURL(new URL(apiUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        xmlrpcClient.setConfig(config);
    }

    public boolean logIn(String login, String password, String userAgent) {
        Object[] params = new Object[]{login, password, "", userAgent};
        Map<String, Object> result = xmlRpcExecute(API_LOG_IN, params);
        token = (String) result.get("token");
        return StringUtils.hasText(token);
    }

    public boolean logInAnnonymously() {
        return logIn("", "", OS_TEST_USER_AGENT);
    }

    public String downloadSubtitlesBase64GZIP(String subtitleFileId) {
        Object[] params = new Object[]{token, new String[]{subtitleFileId}};
        Map<String, Object> result = xmlRpcExecute("DownloadSubtitles", params);
        Map<String, Object> data = (Map<String, Object>) ((Object[]) result.get("data"))[0];
        return (String) data.get("data");
    }

    public String decodeBase64GZIP(String base64GZIP) {
        byte[] bytes = Base64.decodeBase64(base64GZIP);
        java.io.ByteArrayOutputStream byteout = new java.io.ByteArrayOutputStream();
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes));

            int r = 0;
            byte buf[] = new byte[1024];
            while (r >= 0) {
                r = gzipInputStream.read(buf, 0, buf.length);
                if (r > 0) {
                    byteout.write(buf, 0, r);
                }
            }
        } catch (IOException e) {
            throw new TechnicalException(e.getCause());
        }
        byte[] uncompressed = byteout.toByteArray();
        return new String(uncompressed);
    }

    public String downloadSubtitles(HashData data) {
        String subtitles = StringUtils.EMPTY;
        boolean isLogged = logIn(login, password, userAgent);
        if (isLogged) {
            String hash = calculateHash(data);
            String subtitleFileId = searchMovieByHash(hash, data.getSize());
            if (StringUtils.hasText(subtitleFileId)) {
                String base64GZIP = downloadSubtitlesBase64GZIP(subtitleFileId);
                subtitles = decodeBase64GZIP(base64GZIP);
            }
        }
        return subtitles;
    }

    public String searchMovieByFilename(String fileName) {
        Map map1 = new HashMap<String, Object>();
        map1.put("query", fileName);
        map1.put("sublanguageid", "eng");

        Object[] params = new Object[]{token, new Object[]{map1}};
        Map<String, Object> result = xmlRpcExecute(API_SEARCH_SUBTITLES, params);

        if (!(result.get("data") instanceof Boolean)) {
            Map<String, Object> data = (Map<String, Object>) ((Object[]) result.get("data"))[0];
            return (String) data.get("IDSubtitleFile");
        }
        return StringUtils.EMPTY;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
