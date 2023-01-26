package task;

import manager.CSVTaskFormat;
import manager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int uid;
    protected Status status;
    protected TaskType taskType;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(int uid, TaskType taskType, String name, Status status, String description) {
        this.uid = uid;
        this.taskType = taskType;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public Task(int uid, TaskType taskType, String name, Status status, Duration duration, LocalDateTime startTime,
                String description) {
        this.uid = uid;
        this.taskType = taskType;
        this.name = name;
        this.status = status;
        this.description = description;
        this.duration = duration;
    } // Конструктор для восстановления из файла сохранения

    public Task(String name, String description, TaskType taskType) {
        this.name = name;
        this.description = description;
        this.uid = 0;
        this.status = Status.NEW;
        this.taskType = taskType;
    }

    public Task(String name, String description, int duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.uid = 0;
        this.status = Status.NEW;
        this.taskType = TaskType.TASK;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = startTime;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
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

    public boolean isEpic() {
        return false;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusSeconds(duration.getSeconds());
    }

    public LocalDateTime getStartTime() {
        if (startTime == null) return LocalDateTime.MIN;
        return startTime;
    }

    public Duration getDuration() {
        if (duration == null) return Duration.ofSeconds(0);
        return duration;
    }

    @Override
    public String toString() {
        return getUid() + "," + taskType + "," + name + "," + status + "," + getDuration() + "," +
                getStartTime().format(CSVTaskFormat.DATE_FORMAT) + "," +
                "," + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return uid == task.uid && Objects.equals(name, task.name) && Objects.equals(description, task.description) &&
                Objects.equals(status, task.status);
    }
}