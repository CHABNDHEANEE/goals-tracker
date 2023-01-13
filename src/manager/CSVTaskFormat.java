package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

public class CSVTaskFormat {

    CSVTaskFormat() {
        super();
    }
    static String getAllTasks(InMemoryTaskManager inMemoryTaskManager) {
        String result = "";

        for (Task task : inMemoryTaskManager.getTasks()) {
            result += task.toString() + "\n";
        }

        for (Task task : inMemoryTaskManager.getSubtasks()) {
            result += task.toString() + "\n";
        }

        for (Task task : inMemoryTaskManager.getEpics()) {
            result += task.toString() + "\n";
        }

        return result;
    }

    static String historyToString(HistoryManager historyManager) {
        String result = "";

        for (Task task : historyManager.getHistory()) {
            result += task.getUid() + ",";
        }

        if (result.length() > 0) result = result.substring(0, result.length() - 1);

        return result;
    }

    static Task taskFromString(String taskString) {
        String[] taskStringArr = taskString.split(",");

        switch (TaskType.valueOf(taskStringArr[1])) {
            case TASK:
                return new Task(Integer.parseInt(taskStringArr[0]), TaskType.valueOf(taskStringArr[1]),
                        taskStringArr[2], Status.valueOf(taskStringArr[3]), taskStringArr[4]);
            case SUBTASK:
                return new Subtask(Integer.parseInt(taskStringArr[0]), TaskType.valueOf(taskStringArr[1]),
                        taskStringArr[2], Status.valueOf(taskStringArr[3]), taskStringArr[4],
                        Integer.parseInt(taskStringArr[5]));
            case EPIC:
                return new Epic(Integer.parseInt(taskStringArr[0]), TaskType.valueOf(taskStringArr[1]),
                        taskStringArr[2], Status.valueOf(taskStringArr[3]), taskStringArr[4]);
        }

        return null;
    }

    static ArrayList<Integer> historyFromString(String value) {
        String[] valueArr = value.split(",");
        ArrayList<Integer> result = new ArrayList<>();
        for (String str : valueArr) {
            result.add(Integer.parseInt(str));
        }

        return result;
    }
}
