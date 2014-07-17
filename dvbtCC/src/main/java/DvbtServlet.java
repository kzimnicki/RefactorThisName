import cc.explain.client.rest.rest.HttpMethod;
import cc.explain.client.rest.rest.RestClient;
import cc.explain.client.rest.rest.RestRequest;
import cc.explain.lucene.LuceneService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class DvbtServlet extends WebSocketServlet {

    private RedisService redisService = new RedisService();
    private LuceneService luceneService = new LuceneService();

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
            String json = createJsonString(line1, line2);
            System.out.println(json);
            queue.put(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createJsonString(String line1, String line2) {
        return String.format("{\"l1\": \"%s\", \"l2\":\"%s\"}", escape(line1), escape(line2));
    }

    private String escape(String line) {
        if (line==null){
            return StringUtils.EMPTY;
        }else {
            String trim = line.trim();
            if(trim.length() > 40){
                trim = trim.substring(0,40);
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
                connection.sendMessage(createJsonString("Loading....", ""));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
