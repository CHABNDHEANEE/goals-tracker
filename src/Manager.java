import Tasks.*;

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

    public void createTask(Task task) {
        task.setUid(uid);
        tasks.put(uid, task);
        uid++;
    }   // Создаем таск

    public void createSubtask(Subtask subtask) {
        subtask.setUid(uid);    // Добавляем юид сабтаска
        Integer uidOfEpic = subtask.getUidOfEpic(); // Получаем юид эпика
        epics.get(uidOfEpic).setSubtask(subtask);  // Привязываем сабтаск к эпику
        subtasks.put(uid, subtask); // добавляем сабтаск в список
        epics.get(uidOfEpic).checkStatus(); // Обновляем статус эпика
        uid++; //Увеличиваем юид
    }   // Создаем сабтаск

    public void createEpic(Epic epic) {
        epic.setUid(uid);
        epics.put(uid, epic);
        uid++;
    }   // Создаем эпик

    public void deleteAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }   // Удаляем все таски, сабтаски и эпики

    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }   // Удаляем таск по айди

    public void deleteSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        epics.get(subtask.getUidOfEpic()).checkStatus();  // Обновляем статус эпика
        subtasks.remove(id);
    }   // Удаляем сабтаск по айди

    public void deleteEpicById(Integer id) {
        epics.remove(id);
    }   // Удаляем эпик по айди

    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }   // Получаем список тасков

    public HashMap<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }   // Получаем список сабтасков

    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
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
        tasks.put(task.getUid(), task);
    }   // Обновляем таск

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getUid(), subtask);
        epics.get(subtask.getUidOfEpic()).checkStatus();    // Обновляем статус эпика
    }   // Обновляем сабтаск

    public void updateEpic(Epic epic) {
        epics.put(epic.getUid(), epic);
    }   // Обновляем эпик

    public HashMap<Integer, Subtask> getAllSubtasksOfEpic(Epic epic) {
        return epic.getSubtasks();
    }   // Получаем список всех сабтасков эпика

}
