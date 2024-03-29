package manager.fileBackedTaskManager;

import manager.TaskManager;
import manager.exception.ManagerSaveException;
import manager.inMemoryManager.InMemoryTaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    public FileBackedTasksManager() {
        super();
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager taskManager = new FileBackedTasksManager();
        String strFile;
        try {
            strFile = Files.readString(Path.of(file.toURI()));
        } catch (IOException e) {
            System.out.println("Ошибка!");
            strFile = "";
        }

        String[] lines = strFile.split("\n");

        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isBlank()) break;
            taskManager.loadTask(CSVTaskFormat.taskFromString(lines[i]));
        }

        try {
            for (Integer id : CSVTaskFormat.historyFromString(lines[lines.length - 1])) {
                if (taskManager.tasks.containsKey(id)) {
                    taskManager.historyManager.addTask(taskManager.tasks.get(id));
                } else if (taskManager.subtasks.containsKey(id)) {
                    taskManager.historyManager.addTask(taskManager.subtasks.get(id));
                } else if (taskManager.epics.containsKey(id)) {
                    taskManager.historyManager.addTask(taskManager.epics.get(id));
                }
            }
        } catch (Exception e) {
        } //Если нет истории, то не добавляем ее в новый файл.

        taskManager.createSortedSetAfterLoading();

        return taskManager;
    }

    private void createSortedSetAfterLoading() {
        sortedSet.addAll(tasks.values());
        sortedSet.addAll(subtasks.values());
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteTaskById(Integer id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void setStatus(int uidOfEpic) {
        super.setStatus(uidOfEpic);
        save();
    }

    public void save() {
        try (Writer fileWriter = new FileWriter("TaskManager.csv")) {
            fileWriter.write("id,type,name,status,startTime,description,epic \n");
            fileWriter.write(CSVTaskFormat.getAllTasks(this));
            fileWriter.write("\n\n");
            fileWriter.write(CSVTaskFormat.historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка!");
        }

    }

    public void loadTask(Task task) {
        TaskType type = task.getTaskType();
        switch (type) {
            case TASK:
                tasks.put(task.getUid(), task);
                break;
            case EPIC:
                epics.put(task.getUid(), (Epic) task);
                break;
            case SUBTASK:
                subtasks.put(task.getUid(), (Subtask) task);
                break;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaskManager item = (TaskManager) obj;

        return Objects.equals(getEpics(), item.getEpics())
                && Objects.equals(getSubtasks(), item.getSubtasks())
                && Objects.equals(getTasks(), item.getTasks());
    }

    @Override
    public TaskManager load() {
        return null;
    }
}
