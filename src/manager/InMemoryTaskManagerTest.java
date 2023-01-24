package manager;

import org.junit.jupiter.api.*;
import task.*;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefault();
        epic1 = new Epic("Переезд", "Заняться переездом.");

        subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid());
        subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи.", epic1.getUid());
        subtask3 = new Subtask("Распаковать вещи", "Распаковать их.", epic1.getUid());

        epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");

        task1 = new Task("Task1", "creating task test 1");
        task2 = new Task("Task2", "creating task test 2");
    }


}