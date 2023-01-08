package manager;

import task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Subtask> subtasks;
    protected final HashMap<Integer, Epic> epics;
    private Integer uid;
    protected final HistoryManager historyManager;


    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
        uid = 0;
    }

    @Override
    public Integer createTask(Task task) {
        task.setUid(uid);
        tasks.put(uid, task);
        return uid++;
    }   // Создаем таск

    @Override
    public Integer createSubtask(Subtask subtask) {
        subtask.setUid(uid);    // Добавляем юид сабтаска
        Integer uidOfEpic = subtask.getUidOfEpic(); // Получаем юид эпика
        if (!epics.containsKey(uidOfEpic)) return null;
        subtasks.put(uid, subtask); // добавляем сабтаск в список
        epics.get(uidOfEpic).addSubtaskId(subtask);  // Привязываем сабтаск к эпику
        setStatus(uidOfEpic); // Обновляем статус эпика
        return uid++; //Увеличиваем юид
    }   // Создаем сабтаск

    @Override
    public Integer createEpic(Epic epic) {
        epic.setUid(uid);
        epics.put(uid, epic);
        return uid++;
    }   // Создаем эпик

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }   // Удаляем все таски, сабтаски и эпики

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic :
                epics.values()) {
            epic.clearSubtasks();
            setStatus(epic.getUid());
        }
    }   // Удаляем все сабтаски

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();   // Вместе с эпиками, соответственно удаляются и сабтаски.
    }   // Удаляем эпики

    @Override
    public void deleteTaskById(Integer id) {
        tasks.remove(id);
        historyManager.remove(id);
    }   // Удаляем таск по айди

    @Override
    public void deleteSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        int uidOfEpic = subtask.getUidOfEpic();
        int uid = subtask.getUid();
        epics.get(uidOfEpic).deleteSubtask(uid);
        setStatus(uidOfEpic);  // Обновляем статус эпика
        subtasks.remove(id);
        historyManager.remove(id);
    }   // Удаляем сабтаск по айди

    @Override
    public void deleteEpicById(Integer id) {
        for (Task task : getAllSubtasksOfEpic(id)) {
            historyManager.remove(task.getUid());
        }
        epics.get(id).clearSubtasks();
        epics.remove(id);
        historyManager.remove(id);
    }   // Удаляем эпик по айди

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }   // Получаем список тасков

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }   // Получаем список сабтасков

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }   // Получаем список эпиков

    @Override
    public Task getTaskById(Integer id) {
        Task task = tasks.get(id);
        historyManager.addTask(task);
        return task;
    }   // Получаем таск по его айди

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        historyManager.addTask(subtask);
        return subtask;
    }   // Получаем сабтаск по айди

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = epics.get(id);
        historyManager.addTask(epic);
        return epic;
    }   // Получаем эпик по айди

    @Override
    public void updateTask(Task task) {
        int id = task.getUid();
        if (!isTaskExists(id)) return;
        tasks.put(id, task);
    }   // Обновляем таск

    @Override
    public void updateSubtask(Subtask subtask) {
        int uid = subtask.getUid();
        int uidOfEpic = subtask.getUidOfEpic();
        if (!isSubtaskExists(uid) && !isEpicExists(uidOfEpic)) return;
        subtasks.put(uid, subtask);
        setStatus(uidOfEpic);    // Обновляем статус эпика
    }   // Обновляем сабтаск

    @Override
    public void updateEpic(Epic epic) {
        if (!isEpicExists(epic.getUid())) return;
        epics.put(epic.getUid(), epic);
    }   // Обновляем эпик

    @Override
    public ArrayList<Subtask> getAllSubtasksOfEpic(int epicId) {
        if (!isEpicExists(epicId)) return null;
        ArrayList<Subtask> result = new ArrayList<>();
        for (int id :
                epics.get(epicId).getSubtasks()) {
            result.add(subtasks.get(id));
        }
        return result;
    }   // Получаем список всех сабтасков эпика

    private boolean isEpicExists(int uid) {
        return epics.containsKey(uid);
    }   // Проверяем существование эпика

    private boolean isTaskExists(int uid) {
        return tasks.containsKey(uid);
    }

    private boolean isSubtaskExists(int uid) {
        return subtasks.containsKey(uid);
    }

    protected void setStatus(int uidOfEpic) {
        Epic epic = epics.get(uidOfEpic);
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus("NEW");
            return;
        }

        boolean isNew = true;
        boolean isDone = true;

        for (Subtask subtask : getAllSubtasksOfEpic(uidOfEpic)) {
            if (subtask.getStatus().equals("NEW")) {
                isNew = false;
            } else if (subtask.getStatus().equals("DONE")) {
                isDone = false;
            }
            if (!isNew && !isDone) break;
        }

        if (isDone) {
            epic.setStatus("DONE");
        } else if (isNew) {
            epic.setStatus("NEW");
        } else {
            epic.setStatus("IN_PROGRESS");
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public void save() {}
}
