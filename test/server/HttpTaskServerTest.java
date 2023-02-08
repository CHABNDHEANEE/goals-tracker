package server;

import com.google.gson.Gson;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {
    Gson gson;
    HttpTaskServer server;
    TaskManager taskManager;
    Task task1;
    Task task2;
    protected Epic epic1;
    protected Subtask subtask1;
    protected Subtask subtask2;

    @BeforeEach
    void beforeAll() {
        gson = new Gson();
        task1 = new Task("Task1", "creating task test 1", 60, LocalDateTime.now().plusHours(10));
        task2 = new Task("Task2", "creating task test 2", 100, LocalDateTime.now().plusHours(11));
        epic1 = new Epic("Переезд", "Заняться переездом.");

        subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid(),
                60, LocalDateTime.now());
        subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи.", epic1.getUid(), 60,
                LocalDateTime.now().plusHours(1));
    }

    @BeforeEach
    void beforeEach() {
        server = new HttpTaskServer();
        taskManager = Managers.getDefaultWithSaves();
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void postTask() throws IOException, InterruptedException {
        HttpResponse<String> response = postTaskOnServer(task1);
        assertEquals(200, response.statusCode());
    }

    @Test
    void getAllTasks() throws IOException, InterruptedException {
        postTaskOnServer(task1);
        postTaskOnServer(task2);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        Task[] tasks = {taskManager.getTaskById(0), taskManager.getTaskById(1)};
        assertArrayEquals(tasks, gson.fromJson(getTaskFromServer(-1), Task[].class));
    }

    @Test
    void getTaskById() throws IOException, InterruptedException {
        postTaskOnServer(task1);
        assertEquals(task1, gson.fromJson(getTaskFromServer(0), Task.class));
    }

    @Test
    void deleteAllTasks() throws IOException, InterruptedException {
        postTaskOnServer(task1);
        deleteTaskFromServer(-1);
        assertArrayEquals(new Task[] {}, gson.fromJson(getTaskFromServer(-1), Task[].class));
    }

    @Test
    void deleteTaskById() throws IOException, InterruptedException {
        postTaskOnServer(task1);
        postTaskOnServer(task2);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.deleteTaskById(0);
        deleteTaskFromServer(0);
        assertArrayEquals(taskManager.getTasks().toArray(), gson.fromJson(getTaskFromServer(-1), Task[].class));
    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        postTaskOnServer(task1);
        postTaskOnServer(task2);
        getTaskFromServer(1);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTaskById(1);
        assertArrayEquals(taskManager.getHistory().toArray(), gson.fromJson(getHistoryFromServer(), Task[].class));
    }

    @Test
    void getPriorTasks() throws IOException, InterruptedException {
        postTaskOnServer(task1);
        postTaskOnServer(task2);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertArrayEquals(taskManager.getSortedSet().toArray(), gson.fromJson(getPriorTasksFromServer(), Task[].class));
    }

    @Test
    void getSubtasksOfEpic() throws IOException, InterruptedException {
        postEpicOnServer(epic1);
        postSubtaskOnServer(subtask1);
        postSubtaskOnServer(subtask2);
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        assertEquals(gson.toJson(taskManager.getAllSubtasksOfEpic(0)), getAllSubtasksOfEpicFromServer());
    }

    private String getAllSubtasksOfEpicFromServer() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/subtask/epic/?id=0");
        HttpRequest request =  HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private void postEpicOnServer(Epic task) throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
    }

    private void postSubtaskOnServer(Subtask task) throws  IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        client.send(request, handler);
    }

    private String getPriorTasksFromServer() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private String getHistoryFromServer() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/history");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private void deleteTaskFromServer(int id) throws IOException, InterruptedException {
        URI url;
        if (id == -1) {
            url = URI.create("http://localhost:8080/tasks/task");
        } else {
            url = URI.create("http://localhost:8080/tasks/task/?id=" + id);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> postTaskOnServer(Task task) throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        return client.send(request, handler);
    }

    private String getTaskFromServer(int id) throws IOException, InterruptedException {
        URI url;
        if (id == -1) {
            url = URI.create("http://localhost:8080/tasks/task");
        } else {
            url = URI.create("http://localhost:8080/tasks/task/?id=" + id);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }
}
