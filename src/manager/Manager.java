package manager;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Subtask> subtasks;
    HashMap<Integer, Epic> epics;
    Integer uid;

    public Manager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        uid = 0;
    }

    public Integer createTask(Task task) {
        task.setUid(uid);
        tasks.put(uid, task);
        return uid++;
    }   // Создаем таск

    public Integer createSubtask(Subtask subtask) {
        subtask.setUid(uid);    // Добавляем юид сабтаска
        Integer uidOfEpic = subtask.getUidOfEpic(); // Получаем юид эпика
        if (!epics.containsKey(uidOfEpic)) return null;
        subtasks.put(uid, subtask); // добавляем сабтаск в список
        epics.get(uidOfEpic).addSubtaskId(subtask);  // Привязываем сабтаск к эпику
        epics.get(uidOfEpic).setStatus(subtasks); // Обновляем статус эпика
        return uid++; //Увеличиваем юид
    }   // Создаем сабтаск

    public Integer createEpic(Epic epic) {
        epic.setUid(uid);
        epics.put(uid, epic);
        return uid++;
    }   // Создаем эпик

    public void deleteAllTasks() {
        tasks.clear();
    }   // Удаляем все таски, сабтаски и эпики

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic :
                epics.values()) {
            epic.clearSubtasks();
        }
    }   // Удаляем все сабтаски

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();   // Вместе с эпиками, соответственно удаляются и сабтаски.
    }   // Удаляем эпики

    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }   // Удаляем таск по айди

    public void deleteSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        int uidOfEpic = subtask.getUidOfEpic();
        int uid = subtask.getUid();
        epics.get(uidOfEpic).deleteSubtask(uid);
        epics.get(subtask.getUidOfEpic()).setStatus(subtasks);  // Обновляем статус эпика
        subtasks.remove(id);
    }   // Удаляем сабтаск по айди

    public void deleteEpicById(Integer id) {
        epics.get(id).clearSubtasks();
        epics.remove(id);
    }   // Удаляем эпик по айди

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }   // Получаем список тасков

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }   // Получаем список сабтасков

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }   // Получаем список эпиков

    public Task getTaskById(Integer id) {
        return tasks.get(id);
    }   // Получаем таск по его айди

    public Subtask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }   // Получаем сабтаск по айди

    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }   // Получаем эпик по айди

    public void updateTask(Task task) {
        if (!tasks.containsValue(task)) return;
        tasks.put(task.getUid(), task);
    }   // Обновляем таск

    public void updateSubtask(Subtask subtask) {
        int uid = subtask.getUid();
        int uidOfEpic = subtask.getUidOfEpic();
        if (!subtasks.containsKey(uid) && !epics.containsKey(uidOfEpic)) return;
        subtasks.put(uid, subtask);
        epics.get(uidOfEpic).setStatus(subtasks);    // Обновляем статус эпика
    }   // Обновляем сабтаск

    public void updateEpic(Epic epic) {
        epics.put(epic.getUid(), epic);
    }   // Обновляем эпик

    public ArrayList<Integer> getAllSubtasksOfEpic(int epicId) {
        if (!isEpicExists(epicId)) return null;
        return epics.get(epicId).getSubtasks();
    }   // Получаем список всех сабтасков эпика

    private boolean isEpicExists(int uid) {
        return epics.containsKey(uid);
    }   // Проверяем существование эпика
}
