package task;

import manager.TaskType;

import java.util.ArrayList;

public class Epic extends Task{


    ArrayList<Integer> subtasksId;

    public Epic(int uid, TaskType taskType, String name, Status status, String description) {
        super(uid, taskType, name, status, description);
    }

    public Epic(String name, String description, int uid, String status) {
        super(name, description, TaskType.EPIC);
        subtasksId = new ArrayList<>();
    }

    public Epic(String name, String description) {
        super(name, description, TaskType.EPIC);
        this.name = name;
        this.description = description;
        subtasksId = new ArrayList<>();
    }

    public void addSubtaskId(Subtask subtask) {
        this.subtasksId.add(subtask.uid);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasksId;
    }

    public void deleteSubtask(int uid) {
        subtasksId.remove(subtasksId.indexOf(uid));
    }

    public void clearSubtasks() {
        subtasksId.clear();
    }

    @Override
    public void setStatus(String newStatus) {}

    @Override
    public boolean isEpic() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return subtasksId.equals(epic.subtasksId);
    }
}