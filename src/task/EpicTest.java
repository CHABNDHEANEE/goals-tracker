package task;

import manager.Managers;
import manager.TaskManager;
import manager.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager taskManager;
    Epic epic1;
    Epic epic2;
    Subtask subtask1;
    Subtask subtask2;
    Subtask subtask3;

    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefault();
        epic1 = new Epic("Переезд", "Заняться переездом.");
        taskManager.createEpic(epic1);

        subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid(),
                60, LocalDateTime.now());
        subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи.", epic1.getUid(), 60,
                LocalDateTime.now().plusHours(1));
        subtask3 = new Subtask("Распаковать вещи", "Распаковать их.", epic1.getUid(), 50,
                LocalDateTime.now().plusHours(2));
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");
        taskManager.createEpic(epic2);
    }

    @Test
    void epicStatusWithNoSubtasks() {
        taskManager.setStatus(4);
        assertEquals(Status.NEW, getStatusOfEpic(4));
    }

    @Test
    void epicStatusWithAllNewSubtasks() {
        taskManager.setStatus(0);
        assertEquals(Status.NEW, getStatusOfEpic(0));
    }

    @Test
    void epicStatusWithAllDoneSubtasks() {
        changeStatusOfAllSubtasks(0, "DONE");
        taskManager.setStatus(0);
        assertEquals(Status.DONE, getStatusOfEpic(0));
    }

    @Test
    void epicStatusWithNewAndDoneSubtasks() {
        subtask1.setStatus("DONE");
        taskManager.setStatus(0);
        assertEquals(Status.IN_PROGRESS, getStatusOfEpic(0));
    }

    @Test
    void epicStatusWithAllInProgressSubtasks() {
        changeStatusOfAllSubtasks(0, "IN_PROGRESS");
        taskManager.setStatus(0);
        assertEquals(Status.IN_PROGRESS, getStatusOfEpic(0));
    }

    private Status getStatusOfEpic(int epicUid) {
        return taskManager.getEpicById(epicUid).getStatus();
    }

    private void changeStatusOfAllSubtasks(int epicUid, String status) {
        for (Task subtask :
                taskManager.getAllSubtasksOfEpic(epicUid)) {
            subtask.setStatus(status);
        }
    }
}