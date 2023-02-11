package manager.serverTaskManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.TaskManager;
import manager.fileBackedTaskManager.FileBackedTasksManager;
import server.KVTaskClient;
import task.Task;

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;

public class HttpTaskManager extends FileBackedTasksManager implements TaskManager {
    private final static Gson gson = new GsonBuilder()
            .registerTypeAdapter(HttpTaskManager.class, new HttpTaskManagerJsonSerializer())
            .create();

    public static KVTaskClient client;

    public HttpTaskManager(String url) {
        URI serverURL = URI.create(url);
        client = new KVTaskClient(serverURL);
    }

    public HttpTaskManager() {}

    @Override
    public void save() {
        try {
            client.put("1", gson.toJson(this));
        } catch (Exception e) {
            System.out.println("Ошибочка! Трейс:\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    public TaskManager load() {
        try {
            return gson.fromJson(client.load("1"), HttpTaskManager.class);
        }  catch (Exception e) {
            System.out.println("Ошибочка! Трейс:\n" + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
