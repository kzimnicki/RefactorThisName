package cc.explain.netflix;

import cc.explain.netflix.redis.Language;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class NetflixServlet extends HttpServlet {

    private static final long serialVersionUID = -6154475799000019575L;

    private TextService textService = new TextService();

    private TranslationService translationService = new TranslationService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");
        Language from = Language.valueOf(request.getParameter("from"));
        Language to = Language.valueOf(request.getParameter("to"));

        if (StringUtils.isBlank(text) || StringUtils.length(text) > 1000) {
            LOG(String.format("Text not supported: [%s]", text));
            return;
        }

        Set<String> words = textService.getWords(text, from);
        Map<String, String> translationMapping = Maps.newHashMap();

        for (String word : words) {
            String translation = translationService.translate(from, to, word);

            if (textService.areNotSimilar(word, translation)) {
                translationMapping.put(word, translation);
            }
        }

        String json = createJsonString(translationMapping);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        LOG(String.valueOf(translationMapping));
        LOG(json);
        response.getWriter().println(json);
    }

    private static void LOG(String text) {
        System.out.println("LOG: " + text);
    }

    public String createJsonString(Map<String, String> translations) {
        Type typeOfMap = new TypeToken<Map<String, String>>() {
        }.getType();
        String json = new Gson().toJson(translations, typeOfMap);
        return String.format("{\"translation\": \"%s\"}", json);
    }

}
