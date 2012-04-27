/**
 * User: kzimnick
 * Date: 12.02.12
 * Time: 12:24
 */
public class Server {

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        init();
    }

    private void init() {
//        Handler handler = new SimpleHandler();
//        org.mortbay.jetty.Server server = createServer(handler);
//        startServer(server);
    }

//    private org.mortbay.jetty.Server createServer(Handler handler) {
//        int port = Integer.valueOf(System.getProperty("port", "443"));
//        org.mortbay.jetty.Server server = new org.mortbay.jetty.Server(port);
//        System.out.println("PORT: " + port);
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[]{handler});
//        server.setHandler(handlers);
//        return server;
//
//    }
//
//    private void startServer(org.mortbay.jetty.Server server) {
//        try {
//            System.setProperty("org.mortbay.jetty.Request.maxFormContentSize", "1");
//            server.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }
}
