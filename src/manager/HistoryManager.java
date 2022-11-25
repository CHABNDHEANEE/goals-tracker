package manager;

import tasks.Task;

import java.util.LinkedList;

interface HistoryManager {

    void addTask(Task task);
    LinkedList<Task> getHistory();
}
