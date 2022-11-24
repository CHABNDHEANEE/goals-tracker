package manager;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private static ArrayList<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }


    public static void add(Task task) {
        history.add(0, task);
        if (history.size() > 10) {
            history.remove(10);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}
