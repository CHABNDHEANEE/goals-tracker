package manager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected TaskManager taskManager;
    protected Epic epic1;
    protected Epic epic2;
    protected Subtask subtask1;
    protected Subtask subtask2;
    protected Subtask subtask3;
    protected Task task1;
    protected Task task2;
    protected Task task3;
    protected Task task4;
    protected Task task5;

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
    void deletingEpicById() {
        createEpic(epic1);
        taskManager.deleteEpicById(0);
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void deletingSubtaskById() {
        createEpic(epic1);
        createSubtask(subtask1);
        taskManager.deleteSubtaskById(1);
        assertTrue(taskManager.getSubtasks().isEmpty());
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

    @Test
    void checkSortingByDate() {
        createTask(task2);
        createTask(task1);
        assertArrayEquals(new Task[]{task1, task2}, taskManager.getSortedSet().toArray());
    }

    @Test
    void checkSortingByDateWithNulls() {
        createTask(task3);
        createTask(task2);
        createTask(task4);
        createTask(task1);
        assertArrayEquals(new Task[]{task1, task2, task3, task4}, taskManager.getSortedSet().toArray());
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
}