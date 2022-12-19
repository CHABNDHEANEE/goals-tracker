package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

public interface TaskManager {

    Integer createTask(Task task);

    Integer createSubtask(Subtask subtask);

    Integer createEpic(Epic epic);

    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    void deleteTaskById(Integer id);

    void deleteSubtaskById(Integer id);

    void deleteEpicById(Integer id);

    ArrayList<Task> getTasks();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Epic> getEpics();

    Task getTaskById(Integer id);

    Subtask getSubtaskById(Integer id);

    Epic getEpicById(Integer id);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    ArrayList<Subtask> getAllSubtasksOfEpic(int epicId);

    ArrayList<Task> getHistory();
}