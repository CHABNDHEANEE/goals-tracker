package server;

import manager.exception.KVTaskClientException;
import manager.exception.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final URI serverURL;
    private final String token;


    public KVTaskClient(URI serverURL) {
        this.serverURL = serverURL;
        token = getToken();
    }

    public void put(String key, String json) {
        try {
            URI requestURL = getServerURL("save/" + key + "?API_TOKEN=" + token);
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(requestURL)
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Не удалось сохранить данные. Статус: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Не удалось сохранить данные. \n" + e);
        }
    }

    public String load(String key) {
        try {
            URI requestURL = getServerURL("load/" + key + "?API_TOKEN=" + token);
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(requestURL)
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Не удалось загрузить данные. \n" + e);
        }
    }

    private String getToken() {
        try {
            URI requestURL = getServerURL("register");
            HttpRequest request = HttpRequest.newBuilder().GET().uri(requestURL).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            return response.body();
        } catch (Exception e) {
            throw new KVTaskClientException(
                    String.format(
                            "Получение токена по URL - %s \nПричина возникновения ошибки: %s",
                            serverURL,
                            e.getMessage()
                    )
            );
        }
    }

    private URI getServerURL(String request) {
        return URI.create(serverURL.toString() + request);
    }
}
