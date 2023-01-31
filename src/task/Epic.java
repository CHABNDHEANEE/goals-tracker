package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task{


    final ArrayList<Integer> subtasksId;
    private LocalDateTime endTime;
    private Duration duration;

    public Epic(int uid, TaskType taskType, String name, Status status, LocalDateTime startTime,
                String description) {
        super(uid, taskType, name, status, description);
        subtasksId = new ArrayList<>();
    }

    public Epic(int uid, TaskType taskType, String name, Status status, long  duration, LocalDateTime startTime,
                String description) {
        super(uid, taskType, name, status, duration, startTime, description);
        subtasksId = new ArrayList<>();
    } //Конструктор для восстановления из сохранки

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
        subtasksId.remove((Integer) uid);
    }

    public void clearSubtasks() {
        subtasksId.clear();
        eraseDuration();
        eraseStartTime();
    }

    public void updateTime(ArrayList<Subtask> subtasks) {
        if (subtasks.isEmpty()) {
            startTime = null;
            duration = Duration.ofSeconds(0);
            return;
        }
        calcStartTime(subtasks);
        calcDuration(subtasks);
        calcEndTime(subtasks);
    }

    private void calcStartTime(ArrayList<Subtask> subtasks) {
        LocalDateTime newStartTime = subtasks.get(0).getStartTimeNullSafe();
        for (Subtask task :
                subtasks) {
            if (newStartTime.isAfter(task.getStartTimeNullSafe())) newStartTime = task.getStartTimeNullSafe();
        }

        startTime = newStartTime;
    }

    private void calcDuration(ArrayList<Subtask> subtasks) {
        Duration newDuration = Duration.ofSeconds(0);
        for (Subtask task :
                subtasks) {
            newDuration = newDuration.plus(Duration.ofMinutes(task.getDuration()));
        }
        duration = newDuration;
    }

    private void calcEndTime(ArrayList<Subtask> subtasks) {
        LocalDateTime newEndTime = subtasks.get(0).getEndTime();
        for (Subtask task :
                subtasks) {
            if (newEndTime.isBefore(task.getEndTime())) newEndTime = task.getEndTime();
        }
        endTime = newEndTime;
    }

    private void eraseStartTime() {
        this.startTime = null;
    }

    private void eraseDuration() {
        this.duration = Duration.ofSeconds(0);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

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