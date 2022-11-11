package tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{


    ArrayList<Integer> subtasksId;

    public Epic(String name, String description, int uid, String status) {
        super(name, description);
        subtasksId = new ArrayList<>();
    }

    public Epic(String name, String description) {
        super(name, description);
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

    public void setStatus(HashMap<Integer, Subtask> subtasks) {
        if (subtasksId.isEmpty()) {
            this.status = "NEW";
            return;
        }

        boolean isNew = true;
        boolean isDone = true;

        for (Integer id : subtasksId) {
            Subtask subtask = subtasks.get(id);
            if (subtask.getStatus().equals("NEW")) {
                isNew = false;
            } else if (subtask.getStatus().equals("DONE")) {
                isDone = false;
            }
            if (!isNew && !isDone) break;
        }

        if (isDone) {
            this.status = "DONE";
        } else if (isNew) {
            this.status = "NEW";
        } else {
            this.status = "IN_PROGRESS";
        }
    }

    public void deleteSubtask(int uid) {
        subtasksId.remove(uid);
    }

    public void clearSubtasks() {
        subtasksId.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasksId +
                ", name='" + name + '\'' +
                ", bio='" + description + '\'' +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                '}';
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