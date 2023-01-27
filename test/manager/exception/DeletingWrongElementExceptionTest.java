package manager.exception;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeletingWrongElementExceptionTest extends ExceptionsTest {
    @Test
    void deletingTaskByIdFromEmpty() {
        TaskManager taskManager = Managers.getDefault();
        checkMsgForDeletingWrongElement(assertThrows(DeletingWrongElementException.class,
                () -> taskManager.deleteTaskById(0)));

    }

    @Test
    void deletingEpicByIdFromEmpty() {
        checkMsgForDeletingWrongElement(assertThrows(DeletingWrongElementException.class,
                () -> taskManager.deleteEpicById(0)));
    }
}
