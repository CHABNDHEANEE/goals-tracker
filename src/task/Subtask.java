package task;

import manager.TaskType;

public class Subtask extends Task {
    int uidOfEpic;

    public Subtask(int uid, TaskType taskType, String name, Status status, String description, int uidOfEpic) {
        super(uid, taskType, name, status, description);
        this.uidOfEpic = uidOfEpic;
    }

    public Subtask(String name, String description, int uidOfEpic, String status) {
        super(name, description, TaskType.SUBTASK);
        this.uidOfEpic = uidOfEpic;
    }

    public Subtask(String name, String description, int uidOfEpic) {
        super(name, description, TaskType.SUBTASK);
        this.uidOfEpic = uidOfEpic;
    }

    public int getUidOfEpic() {
        return uidOfEpic;
    }

    @Override
    public String toString() {
        return getUid() + "," + taskType + "," + name + "," + status + "," + description + "," + uidOfEpic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return uidOfEpic == subtask.uidOfEpic;
    }
}
