import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Spottify {

  public static final String client_id = "e3f5bb82bf8d45c3a9d1503fd888bb64";
  public static final String client_secret = "480763dc5a4f4c34827f2b5a5ac23df3";
  public static final String redirect_url = "http://localhost:8083";
  HttpClient client = HttpClient.newBuilder().build();
  public static final String authorize_url = String.format("https://accounts.spotify.com/authorize?client_id=%s&redirect_uri=%s&response_type=code",
      client_id, redirect_url);


  public void authorize() {
    System.out.println("Use this link to authorize Spottify");
    System.out.println(authorize_url);
  }

  public void getAccessToken() {

    String plainCredentials = client_id + ":" + client_secret;
    String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
    String authorizationHeader = "Basic " + base64Credentials;

    String access_token_url = "https://accounts.spotify.com/api/token";
    String bodyData = String.format("code=%s&redirect_uri=%s&grant_type=authorization_code",
        AppHttpServer.authCode, redirect_url);
    System.out.println(access_token_url);
    HttpRequest request = HttpRequest.newBuilder()
        .header("Content-Type", "application/x-www-form-urlencoded")
        .header("Authorization", authorizationHeader)
        .uri(URI.create(access_token_url))
        .POST(HttpRequest.BodyPublishers.ofString(bodyData))
        .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
    } catch (Exception e) {
      System.out.println(e);
    }

  }

}
