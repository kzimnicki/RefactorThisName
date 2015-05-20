package cc.explain.netflix;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class Bootstrap {

    private Server server;

    public void start() throws Exception {
        server = new Server(8888);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(NetflixServlet.class, "/netflix/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"src/main/resources/index.html"});
        resourceHandler.setResourceBase(".");

        HandlerCollection handlerList = new HandlerCollection();
        handlerList.setHandlers(new Handler[]{resourceHandler,handler});
        server.setHandler(handlerList);

        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public static void main(String[] args) throws Exception {
        new Bootstrap().start();
    }

}
