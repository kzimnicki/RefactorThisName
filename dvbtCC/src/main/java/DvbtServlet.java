import cc.explain.german.RedisService;
import cc.explain.server.api.LuceneService;
import cc.explain.server.subtitle.SubtitleUtils;
import cc.explain.server.utils.StringUtils;
import com.google.common.io.Files;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

public class DvbtServlet extends WebSocketServlet{

    private static final long serialVersionUID = -7289719281366784056L;
    public static String newLine = System.getProperty("line.separator");

    private final Set<TailorSocket> _members = new CopyOnWriteArraySet<TailorSocket>();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final LuceneService luceneService = new LuceneService();
    private final RedisService  redisService = new RedisService();
    private Set<String> excludedWords;

    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    public RedisService getRedisService(){
        return redisService;
    }


    public void process(String subtitles, TailorSocket member) throws IOException {
            List<String> words = luceneService.getGermanWords(excludedWords, subtitles);
            String translateSubtitle = subtitles;
            for(int i=0; i<words.size(); i++){
                String word = words.get(i);
                if(translateSubtitle.trim().startsWith(word)){
                    System.err.println(word);
                }
//                                        String translation = redisService.getWithEngPrefix(word);
                String translation = getRedisService().get(word);
                if(translation == null){
                    translation = getRedisService().get(word.toLowerCase());
                }
                if(translation != null && !org.apache.commons.lang3.StringUtils.equals(translation,word)){
                    translateSubtitle = SubtitleUtils.replaceText(translateSubtitle, word, translation, "<font color='yellow'>(@@TRANSLATED_TEXT@@)</font>");
                }
            }
            member.sendMessage(translateSubtitle);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            excludedWords = new HashSet<String>(Files.readLines(new File("/home/kz/dev/RefactorThisName/dvbtCC/src/main/resources/excludeGermanWords1000.txt"), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        redisService.init();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String value = "";
                try {
                     value = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(TailorSocket member : _members){
                        try {
                            process(value, member);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }
        }, 2, 2, TimeUnit.MILLISECONDS);



    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getNamedDispatcher("default").forward(request,
                response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String value = req.getParameter("v");
            System.out.println(value);
            queue.put(value);

        } catch (InterruptedException e) {
            e.printStackTrace();
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
            _connection.sendMessage(data);
        }

        @Override
        public void onMessage(String data) {
            System.out.println("Received: "+data);
        }

        public boolean isOpen() {
            return _connection.isOpen();
        }

        @Override
        public void onOpen(Connection connection) {
            _members.add(this);
            _connection = connection;
            try {
                connection.sendMessage("Server received Web Socket upgrade and added it to Receiver List.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
