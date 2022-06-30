import com.sun.net.httpserver.HttpServer;

public class Main {

  private static AppHttpServer server = new AppHttpServer();
  public static final Spottify spottifyServices = new Spottify();

  public static void main(String[] args) {

    try {
      spottifyServices.authorize();
      server.configureServer();
      System.out.println("Authcode : " + AppHttpServer.authCode);
      spottifyServices.getAccessToken();
    } catch (Exception e) {
      System.out.println("Exception " + e.toString());
    }

  }



}
