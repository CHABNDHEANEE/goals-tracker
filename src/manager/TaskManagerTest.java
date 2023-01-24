package manager;

import manager.exception.DeletingWrongElementException;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    TaskManager taskManager;
    Epic epic1;
    Epic epic2;
    Subtask subtask1;
    Subtask subtask2;
    Subtask subtask3;
    Task task1;
    Task task2;

    @Test
    void creatingTask() {
        createTask(task1);
        assertEquals(taskManager.getTaskById(0), task1);
    }

    @Test
    void creatingEpic() {
        createEpic(epic1);
        assertEquals(epic1, taskManager.getEpicById(0));
    }

    @Test
    void creatingSubtask() {
        createEpic(epic1);
        createSubtask(subtask1);
        assertEquals(subtask1, taskManager.getSubtaskById(1));
    }

    @Test
    void deletingAllTasks() {
        creatingTask();
        taskManager.deleteAllTasks();
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void deletingAllEpics() {
        createEpic(epic1);
        taskManager.deleteAllEpics();
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void deletingAllSubtasks() {
        createEpic(epic1);
        createSubtask(subtask1);
        taskManager.deleteAllSubtasks();
        assertTrue(taskManager.getSubtasks().isEmpty());
    }

    @Test
    void deletingTaskById() {
        creatingTask();
        taskManager.deleteTaskById(0);
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void deletingTaskByIdFromEmpty() {
        checkMsgForDeletingWrongElement(assertThrows(DeletingWrongElementException.class,
                () -> taskManager.deleteTaskById(0)));
    }

    @Test
    void deletingEpicById() {
        createEpic(epic1);
        taskManager.deleteEpicById(0);
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void deletingEpicByIdFromEmpty() {
        checkMsgForDeletingWrongElement(assertThrows(DeletingWrongElementException.class,
                () -> taskManager.deleteEpicById(0)));
    }

    @Test
    void deletingSubtaskById() {
        createEpic(epic1);
        createSubtask(subtask1);
        taskManager.deleteSubtaskById(1);
        assertTrue(taskManager.getSubtasks().isEmpty());
    }

    @Test
    void deletingSubtaskByIdFromEmpty() {
        checkMsgForDeletingWrongElement(assertThrows(DeletingWrongElementException.class,
                () -> taskManager.deleteSubtaskById(0)));
    }

    @Test
    void gettingAllTasks() {
        createTask(task1);
        createTask(task2);
        assertArrayEquals(new Task[]{task1, task2}, taskManager.getTasks().toArray());
    }

    @Test
    void gettingAllTasksFromEmpty() {
        assertArrayEquals(new Task[]{}, taskManager.getTasks().toArray());
    }

    @Test
    void gettingAllEpics() {
        createEpic(epic1);
        createEpic(epic2);
        assertArrayEquals(new Task[]{epic1, epic2}, taskManager.getEpics().toArray());
    }

    @Test
    void gettingAllEpicsFromEmpty() {
        assertArrayEquals(new Task[]{}, taskManager.getEpics().toArray());
    }

    @Test
    void gettingAllSubtasks() {
        createEpic(epic1);
        createSubtask(subtask1);
        createSubtask(subtask2);
        assertArrayEquals(new Task[]{subtask1, subtask2}, taskManager.getSubtasks().toArray());
    }

    @Test
    void gettingAllSubtasksFromEmpty() {
        assertArrayEquals(new Task[]{}, taskManager.getSubtasks().toArray());
    }

    @Test
    void updatingTask() {
        createTask(task1);
        task1.setName("changedName");
        taskManager.updateTask(task1);
        assertEquals(task1, taskManager.getTaskById(0));
    }

    @Test
    void updatingUnexistingTask() {
        taskManager.updateTask(task1);
        assertArrayEquals(new Task[]{}, taskManager.getTasks().toArray());
    }

    @Test
    void updatingEpic() {
        createEpic(epic1);
        epic1.setName("changedName");
        taskManager.updateEpic(epic1);
        assertEquals(epic1, taskManager.getEpicById(0));
    }

    @Test
    void updatingUnexistingEpic() {
        taskManager.updateEpic(epic1);
        assertArrayEquals(new Task[]{}, taskManager.getEpics().toArray());
    }

    @Test
    void updatingSubtask() {
        createEpic(epic1);
        createSubtask(subtask1);
        createSubtask(subtask2);
        subtask1.setName("changedName");
        taskManager.updateSubtask(subtask1);
        assertEquals(subtask1, taskManager.getSubtaskById(1));
    }

    @Test
    void updatingUnexistingSubtask() {
        createEpic(epic1);
        taskManager.updateSubtask(subtask1);
        assertArrayEquals(new Task[]{}, taskManager.getSubtasks().toArray());
    }

    @Test
    void gettingAllSubtasksOfEpic() {
        createEpic(epic1);
        createSubtask(subtask1);
        createSubtask(subtask2);
        assertArrayEquals(new Task[]{subtask1, subtask2}, taskManager.getAllSubtasksOfEpic(epic1.getUid()).toArray());
    }

    @Test
    void gettingAllSubtasksOfUnexistingEpic() {
        assertNull(taskManager.getAllSubtasksOfEpic(epic1.getUid()));
    }

    @Test
    void getHistory() {
        createTask(task1);
        createTask(task2);
        taskManager.getTaskById(0);
        assertArrayEquals(new Task[]{task1}, taskManager.getHistory().toArray());
    }

    @Test
    void checkThatSubtaskHasEpic() {
        createEpic(epic1);
        createSubtask(subtask1);
        assertEquals(0, subtask1.getUidOfEpic());
    }

    private void createTask(Task task) {
        taskManager.createTask(task);
    }

    private void createEpic(Epic task) {
        taskManager.createEpic(task);
    }

    private void createSubtask(Subtask task) {
        taskManager.createSubtask(task);
    }

    private void checkMsgForDeletingWrongElement(DeletingWrongElementException exception) {
        assertEquals("Элемент не существует!", exception.getMessage());
    }
}