import cc.explain.client.rest.rest.HttpMethod;
import cc.explain.client.rest.rest.RestClient;
import cc.explain.client.rest.rest.RestRequest;
import cc.explain.client.rest.rest.RestResponse;
import cc.explain.lucene.LuceneService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class NetflixServlet extends HttpServlet {

    private static final long serialVersionUID = -6154475799000019575L;

    private DvbtServlet servlet = new DvbtServlet();


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {


        String text = request.getParameter("t");

        HashMap<String, String> translations = new HashMap<>();
        HashMap<String, String> plTranslations = new HashMap<>();


        LinkedHashSet<String> wordsToTranslate = new LinkedHashSet<>();
        servlet.getWords(text, wordsToTranslate);

        for (String word : wordsToTranslate) {

            String translation = servlet.getTranslation(servlet.DE_EN, "de", "en", word);
            String plTranslation = servlet.getTranslation(servlet.DE_PL,"de", "pl", word);

            servlet.filterTranslations(translations, word, translation, false);
            servlet.filterTranslations(plTranslations, word, plTranslation, false);
        }


    String json = servlet.createJsonString(text, null, translations, plTranslations, false);


    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
        System.out.println(translations);
        response.getWriter().println(json);
}

}
