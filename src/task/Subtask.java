package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    final int uidOfEpic;

    public Subtask(
            int uid, TaskType taskType, String name, Status status, LocalDateTime startTime,
                   String description, int uidOfEpic
                   ) {
        super(uid, taskType, name, status, description);
        this.uidOfEpic = uidOfEpic;
        this.startTime = startTime;
    }

    public Subtask(
            int uid, TaskType taskType, String name, Status status, int duration, LocalDateTime startTime,
            String description, int uidOfEpic
    ) {
        super(uid, taskType, name, status, duration, startTime, description);
        this.uidOfEpic = uidOfEpic;
    } //Конструктор для загрузки сохранки

    public Subtask(String name, String description, int uidOfEpic, String status, int duration, LocalDateTime startTime) {
        super(name, description, TaskType.SUBTASK);
        this.uidOfEpic = uidOfEpic;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Subtask(String name, String description, int uidOfEpic, int duration, LocalDateTime startTime) {
        super(name, description, TaskType.SUBTASK);
        this.uidOfEpic = uidOfEpic;
        this.duration = duration;
        this.startTime = startTime;
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
