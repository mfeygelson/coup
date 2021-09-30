package server;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/events/")
public class EventSocket {
  @OnOpen
  public void onOpen(Session session) throws IOException, InterruptedException {
    while (true) {
      session.getBasicRemote().sendText("haldo");
      Thread.sleep(1000);
    }
  }
}
