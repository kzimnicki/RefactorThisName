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
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class DvbtServlet extends WebSocketServlet {

    public static final String DE_EN = "DEEN";
    public static final String DE_PL = "DEPL";
    private static final String EMPTY_STRING = "";
    private static final String PARAM_LINE_1 = "l1";
    private static final String PARAM_LINE_2 = "l2";
    private static final String PARAM_TIMESTAMP = "t";
    private static final String NO_CC = "KEINE UT";
    private RedisService redisService = new RedisService();
    private LuceneService luceneService = new LuceneService();
    private RestClient client = new RestClient();

    private static int redisHit = 0;
    private static int translatorHit = 0;


    private static Set<String> excludedWords = new HashSet<>();

    private static final String URL_PATTERN = "https://api.datamarket.azure.com/Bing/MicrosoftTranslator/v1/Translate?Text=%27{2}%27&To=%27{1}%27&From=%27{0}%27&$format=json";

    {
        try {
            excludedWords.addAll(IOUtils.readLines(DvbtServlet.class.getResourceAsStream("excludeGermanWords1000.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final long serialVersionUID = -7289719281366784056L;

    private final Set<TailorSocket> _members = new CopyOnWriteArraySet<TailorSocket>();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public void process(String subtitles, TailorSocket member) throws IOException {
        member.sendMessage(subtitles);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    String value = queue.poll(3, TimeUnit.SECONDS);

                    if(value == null) {
                        System.out.println("Just heartbeat...");
                        value = "OK";
                    }

                    for (TailorSocket member : _members) {
                        try {
                            process(value, member);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




            }
        }, 2, 2, java.util.concurrent.TimeUnit.MILLISECONDS);


    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getNamedDispatcher("default").forward(request,
                response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String line1 = req.getParameter(PARAM_LINE_1);
            String line2 = req.getParameter(PARAM_LINE_2);
            String timestamp = req.getParameter(PARAM_TIMESTAMP);
            System.out.println(line1);
            System.out.println(line2);
            System.out.println(timestamp);

            HashMap<String, String> translations = new HashMap<>();
            HashMap<String, String> plTranslations = new HashMap<>();

            if (!NO_CC.equals(line1.trim()) && !NO_CC.equals(line2.trim())) {


                LinkedHashSet<String> wordsToTranslate = new LinkedHashSet<>();
                getWords(line1, wordsToTranslate);
                getWords(line2, wordsToTranslate);

                for (String word : wordsToTranslate) {

                    redisHit++;
                    String translation = getTranslation(DE_EN,"de", "en", word);
                    String plTranslation = getTranslation(DE_PL,"de", "pl", word);

                    filterTranslations(translations, word, translation, true);
                    filterTranslations(plTranslations, word, plTranslation, true);
                }

            }
            System.out.println(String.format("Redis hits: %s, Translator hit: %s", redisHit, translatorHit));

            String json = createJsonString(line1, line2, translations, plTranslations, true);

            System.out.println(json);

            System.out.println(String.format("Number of users: %s", _members.size()));

            queue.put(json);
            System.out.println(new SimpleDateFormat("yyyyMMddhhmmssS").format(new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getWords(final String line1, final LinkedHashSet<String> wordsToTranslate) throws IOException {
        if (line1 != null) {
            wordsToTranslate.addAll(luceneService.getGermanWords(excludedWords, line1));
        }
    }

    public String getTranslation(String languagegPrefix, String from, String to, String word) throws IOException {
        String translation = redisService.getTranslation(languagegPrefix, word);
        System.out.println(String.format("Redis translation: '%s', for: '%s' ", translation, word));
        if(translation == null){
            translatorHit++;
            translation = executeUrl(MessageFormat.format(URL_PATTERN, from, to, word));
            redisService.puTranslation(languagegPrefix, word, translation);
        }
        return translation;
    }

    public void filterTranslations(final HashMap<String, String> translations, final String word, final String translation, boolean trimLine) {
        int distance = LevenshteinDistance.distance(StringUtils.lowerCase(word), StringUtils.lowerCase(translation));
        if ((word.length() <5 && distance > 1) || (word.length() >= 5 && distance > 2)) {
            translations.put(escape(word, trimLine), escape(translation, trimLine));
        } else {
            System.out.println(String.format(" > > > LevenshteinDistance: %s - %s", word, translation));
        }
    }

    public String executeUrl(String url) throws IOException {
        String translation;
        RestRequest restRequest = new RestRequest(HttpMethod.GET).setUrl(url);
        restRequest.addHeader("Authorization", "Basic ZXhwbGFpbmNjQG91dGxvb2suY29tOktqaUUwM0tUUmJOeUhHcG5JdFVKbXNuWFhCWWVpUGh3N2hKUnN6RVBIc3M=");
        RestResponse response = client.execute(restRequest);

        JsonObject json = new JsonParser().parse(IOUtils.toString(response.getContent())).getAsJsonObject();
        translation = json.get("d").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("Text").getAsString();
        return translation;
    }

    public String createJsonString(String line1, String line2, Map<String, String> translations, HashMap<String, String> plTranslations, boolean trim) {
        Type typeOfMap = new TypeToken<Map<String, String>>() {}.getType();
        String enTranslationsString = new Gson().toJson(translations, typeOfMap);
        String plTranslationsString = new Gson().toJson(plTranslations, typeOfMap);
        return String.format("{\"l1\": \"%s\", \"l2\":\"%s\", \"EN\":%s, \"PL\": %s}", escape(line1, trim), escape(line2, trim), enTranslationsString, plTranslationsString);
    }

    private String escape(String line, boolean trimLine) {
        if (line == null) {
            return StringUtils.EMPTY;
        } else {
            String trim = line.trim();
            if (trimLine && trim.length() > 40) {
                trim = trim.substring(0, 40);
            }
            return StringEscapeUtils.escapeJson(trim);
        }
    }


    public WebSocket doWebSocketConnect(HttpServletRequest request,
                                        String protocol) {
        return new TailorSocket();
    }

    class TailorSocket implements WebSocket.OnTextMessage {
        private Connection _connection;

        @Override
        public void onClose(int closeCode, String message) {
            _members.remove(this);
        }

        public void sendMessage(String data) throws IOException {
            System.out.println(_members.size());
            _connection.sendMessage(data);
        }

        @Override
        public void onMessage(String data) {
            System.out.println("Received: " + data);
        }

        public boolean isOpen() {
            return _connection.isOpen();
        }

        @Override
        public void onOpen(Connection connection) {
            _members.add(this);
            _connection = connection;
            try {
                connection.sendMessage(createJsonString("Loading....", "", new HashMap<String, String>(), new HashMap<String, String>(), true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
