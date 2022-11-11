package tasks;

import java.util.Objects;

public class Task {
    String name;
    String description;
    int uid;
    String status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.uid = 0;
        this.status = "NEW";
    }
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", bio='" + description + '\'' +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                '}';
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public boolean isEpic() {
        return false;
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