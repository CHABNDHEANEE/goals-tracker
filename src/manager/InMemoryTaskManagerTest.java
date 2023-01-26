package manager;

import org.junit.jupiter.api.*;
import task.*;


import java.time.LocalDateTime;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    void beforeEach() {
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


}