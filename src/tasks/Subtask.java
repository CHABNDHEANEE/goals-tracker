package Tasks;

public class Subtask extends Task {
    int uidOfEpic;
    public Subtask(String name, String bio, Integer uidOfEpic) {
        super(name, bio);
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
                ", bio='" + bio + '\'' +
                ", uid=" + uid +
                ", status='" + status + '\'' +
                '}';
    }
}
