package manager;

import task.Task;

import java.time.ZoneOffset;
import java.util.Comparator;

class customDateComparator  implements Comparator<Task> {
    public int compare(Task task1, Task task2) {
        if (task1.getStartTime() == null && task2.getStartTime() == null) return 0;
        else if (task2.getStartTime() == null) return -1;
        else if (task1.getStartTime() == null) return 1;
        long result = task1.getStartTime().toInstant(ZoneOffset.UTC).getEpochSecond() -
                task2.getStartTime().toInstant(ZoneOffset.UTC).getEpochSecond();
        return (int) result;
    }
}
