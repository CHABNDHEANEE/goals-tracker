package Tasks;

import java.util.HashMap;

public class Epic extends Task{

    HashMap<Integer, Subtask> subtasks;

    public Epic(String name, String bio) {
        super(name, bio);
        subtasks = new HashMap<>();
    }

    public void setSubtask(Subtask subtask) {
        this.subtasks.put(subtask.uid, subtask);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void checkStatus() {
        if (subtasks.isEmpty()) {
            this.status = "NEW";
            return;
        }

        boolean New = true;

        for (Subtask subtask : subtasks.values()) {
            if (!subtask.status.equals("NEW")) {
                New = false;
                break;
            }
        }

        if (New) {
            this.status = "NEW";
            return;
        }

        boolean done = true;

        for (Subtask subtask : subtasks.values()) {
            if (!subtask.status.equals("DONE")) {
                done = false;
                break;
            }
        }

        if (done) {
            this.status = "DONE";
            return;
        }
        this.status = "IN_PROGRESS";
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public void setStatus(String newStatus) {}
}
