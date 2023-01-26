package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Status;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    Task task1;
    Task task2;
    Task task3;
    HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = Managers.getDefaultHistory();
        task1 = new Task(0, TaskType.TASK, "Task1", Status.NEW, "creating task test 1");
        task2 = new Task(1, TaskType.TASK, "Task2", Status.NEW, "creating task test 2");
        task3 = new Task(2, TaskType.TASK, "Task3", Status.NEW, "creating task test 3");
    }

    @Test
    void addTask() {
        putTasksIntoHistory();
        assertArrayEquals(new Task[]{task3, task2, task1}, historyManager.getHistory().toArray());
    }

    @Test
    void removeFromStart() {
        putTasksIntoHistory();
        historyManager.remove(task1.getUid());
        assertArrayEquals(new Task[]{task3, task2}, historyManager.getHistory().toArray());
    }

    @Test
    void removeFromMiddle() {
        putTasksIntoHistory();
        historyManager.remove(task2.getUid());
        assertArrayEquals(new Task[]{task3, task1}, historyManager.getHistory().toArray());
    }

    @Test
    void removeFromEnd() {
        putTasksIntoHistory();
        historyManager.remove(task3.getUid());
        assertArrayEquals(new Task[]{task2, task1}, historyManager.getHistory().toArray());
    }

    @Test
    void getHistory() {
        historyManager.addTask(task1);
        assertArrayEquals(new Task[]{task1}, historyManager.getHistory().toArray());
    }

    @Test
    void getHistoryWhenEmpty() {
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void getHistoryWithDuplicates() {
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task1);
        assertArrayEquals(new Task[]{task1, task2}, historyManager.getHistory().toArray());
    }

    private void putTasksIntoHistory() {
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
    }
}