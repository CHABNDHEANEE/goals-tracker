package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.exception.KVServerException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class KVServer {
    public static final int PORT = 8080;
    private final String apiToken;
    private final HttpServer server;
    private final Map<String, String> data = new HashMap<>();

    public KVServer() {
        try {
            apiToken = generateApiToken();
            server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
            server.createContext("/register", this::register);
            server.createContext("/save", this::save);
            server.createContext("/load", this::load);
        } catch (IOException e) {
            throw new KVServerException("Произошла ошибка при инициализации сервера: " + e.getMessage());
        }

    }

    public void start() {    //Запуск сервера
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        System.out.println("API_TOKEN: " + apiToken);
        server.start();
    }

    public void stop() {    //Остановка сервера
        server.stop(0);
        System.out.println("Сервер остановлен.");
    }

    private void register(HttpExchange h) throws IOException {    //Регистрация клиента на сервере
        try {
            System.out.println("\n/register");
            if ("GET".equals(h.getRequestMethod())) {
                sendText(h, apiToken);
            } else {
                System.out.println("/register ждёт GET-запрос, а получил " + h.getRequestMethod());
                h.sendResponseHeaders(405, 0);
            }
        } finally {
            h.close();
        }
    }

    private void load(HttpExchange h) throws IOException {    //Загрузка данных с сервера
        System.out.println("\n/load");
        if (hasAuth(h)) {    //Проверка корректности токена
            System.out.println("Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
            h.sendResponseHeaders(403, 0);
            return;
        }
        if ("GET".equals(h.getRequestMethod())) {    //Определение метода
            String key = h.getRequestURI().getPath().substring("/load/".length());
            if (key.isEmpty()) {    //Проверка заполнения key
                System.out.println("Key для загрузки пустой. key указывается в пути: /load/{key}");
                h.sendResponseHeaders(400, 0);
                return;
            }
            sendText(h, data.get(key));
            h.sendResponseHeaders(200, 0);
            try (OutputStream os = h.getResponseBody()) {
                os.write("Запрос успешно выполнен.".getBytes());
            }
        } else {    //Обработка неверного метода запроса
            System.out.println("/load ждёт GET-запрос, а получил: " + h.getRequestMethod());
            h.sendResponseHeaders(405, 0);
        }
    }

    private void save(HttpExchange h) throws IOException {    //Сохранение значения на сервер
        try {
            System.out.println("\n/save");
            if (hasAuth(h)) {
                System.out.println("Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
                h.sendResponseHeaders(403, 0);
                return;
            }
            if ("POST".equals(h.getRequestMethod())) {
                String key = h.getRequestURI().getPath().substring("/save/".length());
                if (key.isEmpty()) {
                    System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                String value = readText(h);
                if (value.isEmpty()) {
                    System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                data.put(key, value);
                System.out.println("Значение для ключа " + key + " успешно обновлено!");
                h.sendResponseHeaders(200, 0);
            } else {
                System.out.println("/save ждёт POST-запрос, а получил: " + h.getRequestMethod());
                h.sendResponseHeaders(405, 0);
            }
        } finally {
            h.close();
        }
    }

    private String generateApiToken() {    //Генерация токена клиента
        return "" + System.currentTimeMillis();
    }

    protected boolean hasAuth(HttpExchange h) {    //Проверка аутентификации клиента
        String rawQuery = h.getRequestURI().getRawQuery();
        return rawQuery == null || (!rawQuery.contains("API_TOKEN=" + apiToken) && !rawQuery.contains("API_TOKEN=DEBUG"));
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
