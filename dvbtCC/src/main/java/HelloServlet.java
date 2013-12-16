import cc.explain.german.RedisService;
import cc.explain.server.api.LuceneService;
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
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloServlet extends WebSocketServlet{

    private static final long serialVersionUID = -7289719281366784056L;
    public static String newLine = System.getProperty("line.separator");

    private final Set<TailorSocket> _members = new CopyOnWriteArraySet<TailorSocket>();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final LuceneService luceneService = new LuceneService();
    private final RedisService  redisService = new RedisService();
    private Set<String> excludedWords;

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
                BufferedReader br = VLC.get();
                for(TailorSocket member : _members){
                    System.out.println("Trying to send to Member!");
                    if(member.isOpen()){
                        try {
                            String line;
                            while((line = br.readLine()) != null){
                                System.out.println(line);
                                String subtitles = "";
                                if(line.length()> 74){
                                    subtitles = line.substring(74);
                                }

                                System.out.println(subtitles.length());
                                System.out.println(subtitles);
                                if(line.contains("##CC##") && subtitles.length() > 2) {
                                    System.out.println("Lucene Processing start");
                                    List<String> words = luceneService.getGermanWords(excludedWords, subtitles);
                                    System.out.println("Lucene Processing finish");
                                    StringBuilder builder = new StringBuilder();

                                    for(String word : words){
                                        System.out.println(word);
                                        String translation = redisService.getWithEngPrefix(word);
                                        if(translation == null){
                                            translation = redisService.getWithEngPrefix(word.toLowerCase());
                                        }
                                        if(translation != null && !org.apache.commons.lang3.StringUtils.equals(translation,word)){
                                            builder.append(word + " - " + translation + "<br />");
                                        }
                                    }
                                    if(builder.length() > 0 ){
                                        member.sendMessage(builder.toString());
                                    }
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 2, 200, TimeUnit.MILLISECONDS);



    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getNamedDispatcher("default").forward(request,
                response);
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
