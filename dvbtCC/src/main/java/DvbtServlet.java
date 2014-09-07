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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class DvbtServlet extends WebSocketServlet {

    private RedisService redisService = new RedisService();
    private LuceneService luceneService = new LuceneService();
    private RestClient client = new RestClient();

    private static int redisHit = 0;
    private static int translatorHit = 0;


    private static Set<String> excludedWords = new HashSet<>();

    private static final String EN_URL1 = "https://api.datamarket.azure.com/Bing/MicrosoftTranslator/v1/Translate?Text=%27";
    private static final String EN_URL2 = "%27&To=%27en%27&From=%27de%27&$format=json";

    private static final String PL_URL1 = "https://api.datamarket.azure.com/Bing/MicrosoftTranslator/v1/Translate?Text=%27";
    private static final String PL_URL2 = "%27&To=%27pl%27&From=%27de%27&$format=json";


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
                String value = "";
                try {
                    value = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (TailorSocket member : _members) {
                    try {
                        process(value, member);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            String line1 = req.getParameter("l1");
            String line2 = req.getParameter("l2");
            String timestamp = req.getParameter("t");
            System.out.println(line1);
            System.out.println(line2);
            System.out.println(timestamp);

            HashMap<String, String> translations = new HashMap<>();
            HashMap<String, String> plTranslations = new HashMap<>();

            if (!"KEINE UT".equals(line1) && !"KEINE UT".equals(line2)) {


                Set<String> wordsToTranslate = new HashSet<>();
                if (line1 != null) {
                    wordsToTranslate.addAll(luceneService.getGermanWords(excludedWords, line1));
                }

                if (line2 != null) {
                    wordsToTranslate.addAll(luceneService.getGermanWords(excludedWords, line2));
                }

                for (String word : wordsToTranslate) {

                    redisHit++;
                    String translation = redisService.getEnglishTranslationForGermanWord(word);

                    System.out.println(String.format("Redis translation: '%s', for: '%s' ", translation, word));

                    if(translation == null){
                        translatorHit++;
                        translation = translate(word, EN_URL1, EN_URL2);
                        redisService.putGermanWordEnglishTranslation(word, translation);
                    }

                    String plTranslation =  redisService.getPolishTranslationForGermanWord(word);

                    if(plTranslation == null){
                        translatorHit++;
                        plTranslation = translate(word, PL_URL1, PL_URL2);
                        redisService.putGermanWordPolishTranslation(word, plTranslation);
                    }

                    int distance = LevenshteinDistance.distance(StringUtils.lowerCase(word), StringUtils.lowerCase(translation));
                    if ((word.length() <5 && distance > 1) || (word.length() >= 5 && distance > 2)) {
                        translations.put(escape(word), escape(translation));
                    } else {
                        System.out.println(String.format(" > > > EN LevenshteinDistance: %s - %s", word, translation));
                    }

                    distance = LevenshteinDistance.distance(StringUtils.lowerCase(word), StringUtils.lowerCase(plTranslation));
                    if ((word.length() <5 && distance > 1) || (word.length() >= 5 && distance > 2)) {
                        plTranslations.put(escape(word), escape(plTranslation));
                    } else {
                        System.out.println(String.format(" > > > PL LevenshteinDistance: %s - %s", word, plTranslation));
                    }
                }

            }
            System.out.println(String.format("Redis hits: %s, Translator hit: %s", redisHit, translatorHit));

            String json = createJsonString(line1, line2, translations, plTranslations);

            System.out.println(json);

            System.out.println(String.format("Number of users: %s", _members.size()));

            queue.put(json);
            System.out.println(new SimpleDateFormat("yyyyMMddhhmmssS").format(new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String translate(String word, String enUrl1, String enUrl2) throws IOException {
        System.out.println(enUrl2);
        String translation;RestRequest restRequest = new RestRequest(HttpMethod.GET).setUrl(enUrl1 + word + enUrl2);
        restRequest.addHeader("Authorization", "Basic ZXhwbGFpbmNjQG91dGxvb2suY29tOktqaUUwM0tUUmJOeUhHcG5JdFVKbXNuWFhCWWVpUGh3N2hKUnN6RVBIc3M=");
        RestResponse response = client.execute(restRequest);

        JsonObject json = new JsonParser().parse(IOUtils.toString(response.getContent())).getAsJsonObject();
        translation = json.get("d").getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("Text").getAsString();
        return translation;
    }

    private String createJsonString(String line1, String line2, Map<String, String> translations, HashMap<String, String> plTranslations) {
        System.out.println("test");
        Type typeOfMap = new TypeToken<Map<String, String>>() {}.getType();
        String enTranslationsString = new Gson().toJson(translations, typeOfMap);
        String plTranslationsString = new Gson().toJson(plTranslations, typeOfMap);
        return String.format("{\"l1\": \"%s\", \"l2\":\"%s\", \"EN\":%s, \"PL\": %s}", escape(line1), escape(line2), enTranslationsString, plTranslationsString);
    }

    private String escape(String line) {
        if (line == null) {
            return StringUtils.EMPTY;
        } else {
            String trim = line.trim();
            if (trim.length() > 40) {
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
                connection.sendMessage(createJsonString("Loading....", "", new HashMap<String, String>(), new HashMap<String, String>()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
