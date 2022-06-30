import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;

public class AppHttpServer {

  public static String authCode = "";

  public void configureServer() throws Exception {
    HttpServer server = HttpServer.create();
    server.bind(new InetSocketAddress(8083), 0);
    server.start();
    server.createContext("/",
        new HttpHandler() {
          public void handle(HttpExchange exchange) throws IOException {
            System.out.println(exchange.getRequestURI().getRawPath());
            String query = exchange.getRequestURI().getQuery();
            String response;
            if (!Objects.isNull(query) && query.contains("code")) {
              authCode = query.substring(5);
              response = "Got the code. Return back to your program.";
            } else {
              response = "Not found authorization code. Try again.";
            }
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
          }
        }
    );

    while (authCode.equals("")) {
      Thread.sleep(10);
    }
    server.stop(10);
  }

}
