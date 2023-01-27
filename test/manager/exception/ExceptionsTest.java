package manager.exception;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import task.Task;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ExceptionsTest {
    protected TaskManager taskManager;
    protected Task task1;
    protected Task task2;
    protected Task task3;
    protected Task task4;
    protected Task task5;

    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefault();

        task1 = new Task("Task1", "creating task test 1", 60, LocalDateTime.now().plusHours(10));
        task2 = new Task("Task2", "creating task test 2", 100, LocalDateTime.now().plusHours(11));
        task3 = new Task("Task3", "creating task test 3", 60, null);
        task4 = new Task("Task4", "creating task test 4", 100, null);
        task5 = new Task("Task5", "creating task test 5", 100, LocalDateTime.now().plusHours(10));
    }

    void checkMsgForDeletingWrongElement(DeletingWrongElementException exception) {
        assertEquals("Элемент не существует!", exception.getMessage());
    }
}
