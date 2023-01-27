package manager.exception;

import manager.Managers;
import org.junit.jupiter.api.Test;
import task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OccupiedTimeIntervalExceptionTest extends ExceptionsTest {

    @Test
    protected void checkInterceptedTimeInterval() {
        createTask(task1);
        createTask(task2);
        createTask(task3);
        OccupiedTimeIntervalException exception =
                assertThrows(OccupiedTimeIntervalException.class, () -> createTask(task5));
        assertEquals("Данный временной интервал занят!", exception.getMessage());
    }

    private void createTask(Task task) {
        taskManager.createTask(task);
    }
}
