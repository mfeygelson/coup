package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.javax.server.config.JavaxWebSocketServletContainerInitializer;

public class SocketServer {
  public static void main(String[] args) throws Exception {
    var server = new SocketServer();
    server.setPort(8080);
    server.start();
    server.join();
  }

  private final Server server;
  private final ServerConnector connector;

  public SocketServer() {
    server = new Server();
    connector = new ServerConnector(server);
    server.addConnector(connector);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    JavaxWebSocketServletContainerInitializer.configure(context, ((servletContext, serverContainer) -> {
      serverContainer.setDefaultMaxTextMessageBufferSize(65535);
      serverContainer.addEndpoint(EventSocket.class);
    }));
  }

  public void setPort(int port) {
    connector.setPort(port);
  }

  public void start() throws Exception {
    server.start();
  }

  public void join() throws InterruptedException
  {
    System.out.println("Use Ctrl+C to stop server");
    server.join();
  }
}
