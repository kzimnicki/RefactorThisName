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

public class DvbtServlet extends WebSocketServlet{

    private static final long serialVersionUID = -7289719281366784056L;

    private final Set<TailorSocket> _members = new CopyOnWriteArraySet<TailorSocket>();
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

//    private final LuceneService luceneService = new LuceneService();
//    private final RedisService  redisService = new RedisService();
//    private Set<String> excludedWords;

    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

//    public RedisService getRedisService(){
//        return redisService;
//    }


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
                for(TailorSocket member : _members){
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
