package manager;

import manager.fileBackedTaskManager.FileBackedTasksManager;
import manager.historyManager.InMemoryHistoryManager;
import manager.serverTaskManager.HttpTaskManager;
import server.KVServer;

public class Managers {

    public static TaskManager getDefault() {
        return new HttpTaskManager("http://localhost:8080/");
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultWithSaves() {
        return new FileBackedTasksManager();
    }

    public static KVServer getDefaultKVServer() {
        return new KVServer();
    }
}
