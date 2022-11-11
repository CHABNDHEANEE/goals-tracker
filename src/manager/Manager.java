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
        setStatus(uidOfEpic); // Обновляем статус эпика
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
        setStatus(uidOfEpic);  // Обновляем статус эпика
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
        int id = task.getUid();
        if (!isTaskExists(id)) return;
        tasks.put(id, task);
    }   // Обновляем таск

    public void updateSubtask(Subtask subtask) {
        int uid = subtask.getUid();
        int uidOfEpic = subtask.getUidOfEpic();
        if (!isSubtaskExists(uid) && !isEpicExists(uidOfEpic)) return;
        subtasks.put(uid, subtask);
        setStatus(uidOfEpic);    // Обновляем статус эпика
    }   // Обновляем сабтаск

    public void updateEpic(Epic epic) {
        if (!isEpicExists(epic.getUid())) return;
        epics.put(epic.getUid(), epic);
    }   // Обновляем эпик

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

    private void setStatus(int uidOfEpic) {
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
}
