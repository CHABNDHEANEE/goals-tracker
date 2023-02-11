package task;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected final TaskType taskType;
    protected String name;
    protected String description;
    protected int uid;
    protected Status status;
    protected long duration;
    protected LocalDateTime startTime;

    public Task(int uid, TaskType taskType, String name, Status status, String description) {
        this.uid = uid;
        this.taskType = taskType;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public Task(int uid, TaskType taskType, String name, Status status, long duration, LocalDateTime startTime,
                String description) {
        this.uid = uid;
        this.taskType = taskType;
        this.name = name;
        this.status = status;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
    } // Конструктор для восстановления из файла сохранения

    public Task(String name, String description, TaskType taskType) {
        this.name = name;
        this.description = description;
        this.uid = 0;
        this.status = Status.NEW;
        this.taskType = taskType;
    }

    public Task(String name, String description, long duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.uid = 0;
        this.status = Status.NEW;
        this.taskType = TaskType.TASK;
        this.duration = duration;
        this.startTime = startTime;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public LocalDateTime getStartTimeNullSafe() {
        if (startTime == null) return LocalDateTime.MIN;
        return startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return getUid() + "," + taskType + "," + name + "," + status + "," + getDuration() + "," +
                getStartTime() + "," +
                "," + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return uid == task.uid && Objects.equals(name, task.name) && Objects.equals(description, task.description) &&
                Objects.equals(status, task.status) && Objects.equals(getStartTime(), task.getStartTime());
    }
}