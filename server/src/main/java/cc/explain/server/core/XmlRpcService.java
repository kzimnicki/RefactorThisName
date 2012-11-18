package cc.explain.server.core;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

/**
 * User: kzimnick
 * Date: 15.11.12
 * Time: 21:34
 */
public class XmlRpcService {

    private static Logger LOG = LoggerFactory.getLogger(XmlRpcService.class);

    public String doTest(String movieHash, String length) throws XmlRpcException, IOException {

        XmlRpcClient xmlrpc = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://api.opensubtitles.org/xml-rpc"));
        xmlrpc.setConfig(config);
        Object[] params = new Object[]{"", "", "", "OS Test User Agent"};
        HashMap<String, String> result = (HashMap<String, String>) xmlrpc.execute("LogIn", params);
        String token = result.get("token");
        System.out.println(token);

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("moviebytesize", length);
        map1.put("moviehash", movieHash);
        map1.put("sublanguageid", "eng");
        params = new Object[]{token, new Object[]{map1}};
        Map<String, Object> result2 = (Map<String, Object>) xmlrpc.execute("SearchSubtitles", params);
        Map<String, Object> data = (Map<String, Object>) ((Object[]) result2.get("data"))[0];
        String idSubtitleFile = (String) data.get("IDSubtitleFile");


        params = new Object[]{token, new String[]{idSubtitleFile}};
        HashMap<String, Object> res = (HashMap<String, Object>) xmlrpc.execute("DownloadSubtitles", params);
        data = (Map<String, Object>) ((Object[]) res.get("data"))[0];
        Object data1 = data.get("data");
        byte[] bytes = Base64.decodeBase64((String) data1);

        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes));
        java.io.ByteArrayOutputStream byteout = new java.io.ByteArrayOutputStream();

        int r = 0;
        byte buf[] = new byte[1024];
        while (r >= 0) {
            r = gzipInputStream.read(buf, 0, buf.length);
            if (r > 0) {
                byteout.write(buf, 0, r);
            }
        }
        byte[] uncompressed = byteout.toByteArray();
        String subtitles = new String(uncompressed);
        return subtitles;
    }

}
