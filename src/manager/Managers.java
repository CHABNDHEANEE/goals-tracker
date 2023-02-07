package manager;

import manager.fileBackedTaskManager.FileBackedTasksManager;
import manager.historyManager.HistoryManager;
import manager.historyManager.InMemoryHistoryManager;
import manager.inMemoryManager.InMemoryTaskManager;
import manager.serverTaskManager.HttpTaskManager;

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
}
