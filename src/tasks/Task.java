package Tasks;

public class Task {
    String name;
    String bio;
    int uid;
    String status;

    public Task(String name, String bio) {
        this.name = name;
        this.bio = bio;
        this.uid = 0;
        this.status = "NEW";
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


    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                '}';
    }
}