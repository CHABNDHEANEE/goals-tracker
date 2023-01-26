package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CSVTaskFormat {

    static public DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd; HH:mm:ss");

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
                        taskStringArr[2], Status.valueOf(taskStringArr[3]), Duration.parse(taskStringArr[4]),
                        LocalDateTime.parse(taskStringArr[5], DATE_FORMAT), taskStringArr[7]);
            case SUBTASK:
                return new Subtask(Integer.parseInt(taskStringArr[0]), TaskType.valueOf(taskStringArr[1]),
                        taskStringArr[2], Status.valueOf(taskStringArr[3]), Duration.parse(taskStringArr[4]), LocalDateTime.parse(taskStringArr[5], DATE_FORMAT), taskStringArr[7], Integer.parseInt(taskStringArr[8]));
            case EPIC:
                return new Epic(Integer.parseInt(taskStringArr[0]), TaskType.valueOf(taskStringArr[1]),
                        taskStringArr[2], Status.valueOf(taskStringArr[3]), Duration.parse(taskStringArr[4]),
                        getStartDateForEpic(taskStringArr[5]), taskStringArr[7]);
        }

        return null;
    }

    static private LocalDateTime getStartDateForEpic(String date) {
        try {
            if (LocalDateTime.parse(date, DATE_FORMAT).equals(LocalDateTime.MIN)) return null;
        } catch (DateTimeParseException exception) { return null; }
        return LocalDateTime.parse(date, DATE_FORMAT);
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
