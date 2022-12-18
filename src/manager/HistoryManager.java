package manager;

import tasks.Task;

import java.util.ArrayList;

interface HistoryManager {

    void addTask(Task task);
    void remove(int id);
    ArrayList<Task> getHistory();
}
