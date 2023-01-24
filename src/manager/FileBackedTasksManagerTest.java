package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefaultWithSaves();
        epic1 = new Epic("Переезд", "Заняться переездом.");

        subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid());
        subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи.", epic1.getUid());
        subtask3 = new Subtask("Распаковать вещи", "Распаковать их.", epic1.getUid());

        epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");

        task1 = new Task("Task1", "creating task test 1");
        task2 = new Task("Task2", "creating task test 2");
    }

    @Test
    void testSavingWithElementsListAndWithoutHistory() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.save();

        TaskManager loaded = loadManagerFromFile();
        assertEquals(taskManager, loaded);
    }

    @Test
    void testSavingWithElementsAndHistory() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getTaskById(0);
        taskManager.getTaskById(1);
        taskManager.getTaskById(0);
        taskManager.save();

        TaskManager loaded = loadManagerFromFile();
        assertEquals(taskManager, loaded);
    }

    @Test
    void testSavingWithEpicWithoutSubtasks() {
        taskManager.createEpic(epic1);
        taskManager.save();

        TaskManager loaded = loadManagerFromFile();
        assertEquals(taskManager, loaded);
    }

    @Test
    void testSavingEmptyList() {
        taskManager.save();
        TaskManager loaded = loadManagerFromFile();
        assertEquals(taskManager, loaded);
    }

    private FileBackedTasksManager loadManagerFromFile() {
        return FileBackedTasksManager.loadFromFile(new File("TaskManager.csv"));
    }
}