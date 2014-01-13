import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class ServerTest {


    public static void main(String[] args) throws Exception {
        Server server = new Server(8888);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(DvbtServlet.class, "/app/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
//        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(".");



        HandlerCollection handlerList = new HandlerCollection();
        handlerList.setHandlers(new Handler[]{resourceHandler,handler});
        server.setHandler(handlerList);

        server.start();
    }

}
