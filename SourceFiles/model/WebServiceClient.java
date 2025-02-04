package model;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class WebServiceClient {

    private HttpClient client;
    public WebServiceClient() {
        this.client = HttpClient.newHttpClient();
    }

    public List<String> getStrategies() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://omok.atwebpages.com/info/"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
       
        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray strategiesJsonArray = jsonObject.getJSONArray("strategies");

        List<String> strategies = new ArrayList<>();
        for (int i = 0; i < strategiesJsonArray.length(); i++) {
            strategies.add(strategiesJsonArray.getString(i));
        }

        return strategies;
    }
  
    public String createNewGame(String strategy) throws IOException, InterruptedException {
        String requestURL = "http://omok.atwebpages.com/new/?strategy=" + strategy;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
       
        JSONObject jsonObject = new JSONObject(response.body());
        boolean success = jsonObject.getBoolean("response");
        if (success) {
            return jsonObject.getString("pid");  // Return the game ID
        } else {
            throw new IOException("Failed to create new game: " + jsonObject.getString("reason"));
        }
    }
    public JSONObject makeMove(String gameId, int x, int y) throws IOException, InterruptedException {
        String requestURL = String.format("http://omok.atwebpages.com/play/?pid=%s&x=%d&y=%d", gameId, x, y);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body());
    }
}
    

