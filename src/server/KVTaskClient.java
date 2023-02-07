package server;

import com.google.gson.Gson;
import manager.serverTaskManager.HttpTaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private URI serverURL;
    private String token;
    Gson gson = new Gson();


    public KVTaskClient(URI serverURL) {
        this.serverURL = serverURL;
        try {
            token = getToken();
        } catch (Exception e) {
            System.out.println("Ошибка!");
        }
    }

    public HttpTaskManager getTaskManager() {
        String load = null;
        try {
            load = load("1");
        } catch (Exception e) {}
        if (load == null) return new HttpTaskManager(serverURL.toString());
        return gson.fromJson(load, HttpTaskManager.class);
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI requestURL = getServerURL("save/" + key + "?API_TOKEN=" + token);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(requestURL)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        if (response.statusCode() != 200) System.out.println("Произошла ошибка!");
    }

    public String load(String key) throws IOException, InterruptedException {
        URI requestURL = getServerURL("load/" + key + "?API_TOKEN=" + token);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(requestURL)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }

    private String getToken() throws IOException, InterruptedException {
        URI requestURL = getServerURL("register");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(requestURL).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }

    private URI getServerURL(String request) {
        return URI.create(serverURL.toString() + request);
    }
}
