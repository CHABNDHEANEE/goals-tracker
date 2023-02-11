package manager.serverTaskManager;

import manager.Managers;
import manager.TaskManager;
import manager.TaskManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest extends TaskManagerTest {
    final KVServer server = Managers.getDefaultKVServer();

    HttpTaskManagerTest() throws IOException {
    }

    @BeforeEach
    void beforeEach() {
        server.start();
        taskManager = Managers.getDefault();
        epic1 = new Epic("Переезд", "Заняться переездом.");

        subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid(),
                60, LocalDateTime.now());
        subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи.", epic1.getUid(), 60,
                LocalDateTime.now().plusHours(1));
        subtask3 = new Subtask("Распаковать вещи", "Распаковать их.", epic1.getUid(), 50,
                LocalDateTime.now().plusHours(2));

        epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");

        task1 = new Task("Task1", "creating task test 1", 60, LocalDateTime.now().plusHours(10));
        task2 = new Task("Task2", "creating task test 2", 100, LocalDateTime.now().plusHours(11));
        task3 = new Task("Task3", "creating task test 3", 60, null);
        task4 = new Task("Task4", "creating task test 4", 100, null);
        task5 = new Task("Task5", "creating task test 5", 100, LocalDateTime.now().plusHours(10));
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    void testSavingWithElementsListAndWithoutHistory() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.save();

        TaskManager loaded = taskManager.load();
        assertEquals(taskManager, loaded);
    }

    @Test
    void testSavingWithElementsAndHistory() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(0);

        TaskManager loaded = taskManager.load();
        assertEquals(taskManager, loaded);
    }

    @Test
    void testSavingWithEpicWithoutSubtasks() {
        taskManager.createEpic(epic1);
        taskManager.save();

        TaskManager loaded = taskManager.load();
        assertEquals(taskManager, loaded);
    }

    @Test
    void testSavingEmptyList() {
        taskManager.save();
        TaskManager loaded = taskManager.load();
        assertEquals(taskManager, loaded);
    }
}
