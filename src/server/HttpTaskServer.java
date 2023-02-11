package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final Gson gson = new Gson();
    TaskManager tasksManager;

    HttpServer server;

    public HttpTaskServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            tasksManager = Managers.getDefaultWithSaves();
            createEndPoints();
            server.start();
        } catch (IOException e) {
            System.out.println("Произошла ошибка при запуске сервера!");
        }
    }

    private static Endpoint getEndpoint(String method, String query, String path) {
        switch (method) {
            case "GET":
                if (path.contains("/tasks/subtask/epic")) {
                    return Endpoint.GET_SUBTASKS_OF_EPIC;
                } else if (query != null) {
                    return Endpoint.GET_TASK;
                } else {
                    return Endpoint.GET_ALL_TASKS;
                }
            case "POST":
                return Endpoint.POST_TASK;
            case "DELETE":
                if (query != null) {
                    return Endpoint.DELETE_TASK_BY_ID;
                } else {
                    return Endpoint.DELETE_ALL_TASKS;
                }
            default:
                return Endpoint.WRONG_ENDPOINT;
        }
    }

    private void createEndPoints() {
        server.createContext("/tasks/task", new TaskHandler());
        server.createContext("/tasks/subtask", new SubtaskHandler());
        server.createContext("/tasks/subtask/epic", new EpicRequestOfSubtasksHandler());
        server.createContext("/tasks/epic", new EpicHandler());
        server.createContext("/tasks/history", new HistoryHandler());
        server.createContext("/tasks", new PriorTasksHandler());
    }

    public void stop() {
        server.stop(0);
        System.out.println("Сервер успешно остановлен.");
    }

    private class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();
            String response;
            int id;

            switch (getEndpoint(method, query, path)) {
                case GET_ALL_TASKS:
                    response = gson.toJson(tasksManager.getTasks());

                    exchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

                    break;
                case GET_TASK:
                    id = Integer.parseInt(query.split("=")[1]);
                    response = gson.toJson(tasksManager.getTaskById(id));

                    exchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

                    break;
                case POST_TASK:
                    Task task = gson.fromJson(new String(exchange.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8), Task.class);
                    tasksManager.createTask(task);

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case DELETE_TASK_BY_ID:
                    id = Integer.parseInt(query.split("=")[1]);

                    tasksManager.deleteTaskById(id);

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case DELETE_ALL_TASKS:
                    tasksManager.deleteAllTasks();

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case WRONG_ENDPOINT:
                    exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    private class SubtaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getQuery();
            String response;
            int id;

            switch (getEndpoint(method, path, path)) {
                case GET_ALL_TASKS:
                    response = gson.toJson(tasksManager.getSubtasks());

                    exchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

                    break;
                case GET_TASK:
                    id = Integer.parseInt(path.split("=")[1]);
                    response = gson.toJson(tasksManager.getSubtaskById(id));

                    exchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

                    break;
                case POST_TASK:
                    Subtask task = gson.fromJson(new String(exchange.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8), Subtask.class);
                    tasksManager.createSubtask(task);

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case DELETE_TASK_BY_ID:
                    id = Integer.parseInt(path.split("=")[1]);

                    tasksManager.deleteSubtaskById(id);

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case DELETE_ALL_TASKS:
                    tasksManager.deleteAllSubtasks();

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case WRONG_ENDPOINT:
                    exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    private class EpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getQuery();
            String response;
            int id;

            switch (getEndpoint(method, path, path)) {
                case GET_ALL_TASKS:
                    response = gson.toJson(tasksManager.getEpics());

                    exchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

                    break;
                case GET_TASK:
                    id = Integer.parseInt(path.split("=")[1]);
                    response = gson.toJson(tasksManager.getEpicById(id));

                    exchange.sendResponseHeaders(200, 0);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

                    break;
                case POST_TASK:
                    Epic task = gson.fromJson(new String(exchange.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8), Epic.class);
                    tasksManager.createEpic(task);

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case DELETE_TASK_BY_ID:
                    id = Integer.parseInt(path.split("=")[1]);

                    tasksManager.deleteEpicById(id);

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case DELETE_ALL_TASKS:
                    tasksManager.deleteAllEpics();

                    exchange.sendResponseHeaders(200, -1);
                    break;
                case WRONG_ENDPOINT:
                    exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    private class EpicRequestOfSubtasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getQuery();

            int id = Integer.parseInt(path.split("=")[1]);
            String response = gson.toJson(tasksManager.getAllSubtasksOfEpic(id));

            exchange.sendResponseHeaders(200, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = gson.toJson(tasksManager.getHistory());

            exchange.sendResponseHeaders(200, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private class PriorTasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = gson.toJson(tasksManager.getSortedSet());

            exchange.sendResponseHeaders(200, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
