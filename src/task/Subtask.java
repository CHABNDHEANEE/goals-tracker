package task;

public class Subtask extends Task {
    int uidOfEpic;
    public Subtask(String name, String description, int uidOfEpic, String status) {
        super(name, description);
        this.uidOfEpic = uidOfEpic;
    }

    public Subtask(String name, String description, int uidOfEpic) {
        super(name, description);
        this.uidOfEpic = uidOfEpic;
    }

    public int getUidOfEpic() {
        return uidOfEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "uidOfEpic=" + uidOfEpic +
                ", name='" + name + '\'' +
                ", bio='" + description + '\'' +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                '}';
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
