package manager;

import manager.exception.DeletingWrongElementException;
import manager.exception.OccupiedTimeIntervalException;
import task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Subtask> subtasks;
    protected final HashMap<Integer, Epic> epics;
    private Integer uid;
    protected final HistoryManager historyManager;

    protected TreeSet<Task> sortedSet;


    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
        uid = 0;
    }

    private void checkIfTimeIntervalFree(Task taskWithTime) {
        LocalDateTime timeOfStart = taskWithTime.getStartTime();
        Duration duration = taskWithTime.getDuration();

        if (timeOfStart == null) return;

        for (Task task :
                getSortedSet()) {
            if (task.getStartTime() == null || task.getUid() == taskWithTime.getUid()) {
            }
            else if ((task.getStartTime().isBefore(timeOfStart) &&
                    task.getStartTime().plus(task.getDuration()).isAfter(timeOfStart)) ||
                    (task.getStartTime().isAfter(timeOfStart) &&
                            task.getStartTime().isBefore(timeOfStart.plus(duration)))) {
                throw new OccupiedTimeIntervalException("Данный временной интервал занят!");
            }
        }
    }

    private void makeSortedSet() {
        sortedSet = new TreeSet<>(new customDateComparator());
        sortedSet.addAll(tasks.values());
        sortedSet.addAll(subtasks.values());
    }

    private ArrayList<Task> getTasksWithoutTime() {
        ArrayList<Task> result = new ArrayList<>();

        for (Task task :
                tasks.values()) {
            if (task.getStartTime() == null) result.add(task);
        }
        if (!result.isEmpty()) result.remove(0);
        return result;
    }

    @Override
    public ArrayList<Task> getSortedSet() {
        makeSortedSet();
        ArrayList<Task> result = new ArrayList<>(sortedSet);
        result.addAll(getTasksWithoutTime());
        return result;
    }

    @Override
    public Integer createTask(Task task) {
        checkIfTimeIntervalFree(task);
        task.setUid(uid);
        tasks.put(uid, task);
        return uid++;
    }   // Создаем таск

    @Override
    public Integer createSubtask(Subtask subtask) {
        checkIfTimeIntervalFree(subtask);
        subtask.setUid(uid);    // Добавляем юид сабтаска
        int uidOfEpic = subtask.getUidOfEpic(); // Получаем юид эпика
        if (!epics.containsKey(uidOfEpic)) return null;
        subtasks.put(uid, subtask); // добавляем сабтаск в список
        Epic epic = epics.get(uidOfEpic);
        epic.addSubtaskId(subtask);  // Привязываем сабтаск к эпику
        setStatus(uidOfEpic); // Обновляем статус эпика
        epic.updateTime(getAllSubtasksOfEpic(uidOfEpic));
        return uid++; // Увеличиваем юид
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
        if (!tasks.containsKey(id)) throw new DeletingWrongElementException("Элемент не существует!");
        tasks.remove(id);
        if (historyManager.getHistory().contains(tasks.get(id))) historyManager.remove(id);
    }   // Удаляем таск по айди

    @Override
    public void deleteSubtaskById(Integer id) {
        if (!subtasks.containsKey(id)) throw new DeletingWrongElementException("Элемент не существует!");
        Subtask subtask = subtasks.get(id);
        int uidOfEpic = subtask.getUidOfEpic();
        int uid = subtask.getUid();
        epics.get(uidOfEpic).deleteSubtask(uid);
        epics.get(uidOfEpic).updateTime(getAllSubtasksOfEpic(uidOfEpic));
        setStatus(uidOfEpic);  // Обновляем статус эпика
        subtasks.remove(id);
        if (historyManager.getHistory().contains(subtask)) historyManager.remove(id);
    }   // Удаляем сабтаск по айди

    @Override
    public void deleteEpicById(Integer id) {
        if (!epics.containsKey(id)) throw new DeletingWrongElementException("Элемент не существует!");
        for (Task task : getAllSubtasksOfEpic(id)) {
            historyManager.remove(task.getUid());
            deleteSubtaskById(task.getUid());
        }
        epics.remove(id);
        if (historyManager.getHistory().contains(epics.get(id))) historyManager.remove(id);
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
        checkIfTimeIntervalFree(task);
        int id = task.getUid();
        if (!isTaskExists(id)) return;
        tasks.put(id, task);
    }   // Обновляем таск

    @Override
    public void updateSubtask(Subtask subtask) {
        checkIfTimeIntervalFree(subtask);
        int uid = subtask.getUid();
        int uidOfEpic = subtask.getUidOfEpic();
        if (!isSubtaskExists(uid) || isEpicExists(uidOfEpic)) return;
        subtasks.put(uid, subtask);
        setStatus(uidOfEpic);    // Обновляем статус эпика
    }   // Обновляем сабтаск

    @Override
    public void updateEpic(Epic epic) {
        if (isEpicExists(epic.getUid())) return;
        epics.put(epic.getUid(), epic);
    }   // Обновляем эпик

    @Override
    public ArrayList<Subtask> getAllSubtasksOfEpic(int epicId) {
        if (isEpicExists(epicId)) return null;
        ArrayList<Subtask> result = new ArrayList<>();
        for (int id :
                epics.get(epicId).getSubtasks()) {
            result.add(subtasks.get(id));
        }
        return result;
    }   // Получаем список всех сабтасков эпика

    private boolean isEpicExists(int uid) {
        return !epics.containsKey(uid);
    }   // Проверяем существование эпика

    private boolean isTaskExists(int uid) {
        return tasks.containsKey(uid);
    }

    private boolean isSubtaskExists(int uid) {
        return subtasks.containsKey(uid);
    }

    @Override
    public void setStatus(int uidOfEpic) {
        Epic epic = epics.get(uidOfEpic);
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus("NEW");
            return;
        }

        boolean isNew = true;
        boolean isDone = true;

        for (Subtask subtask : getAllSubtasksOfEpic(uidOfEpic)) {
            if (subtask.getStatus() != Status.NEW) {
                isNew = false;
            }
            if (subtask.getStatus() != Status.DONE) {
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
