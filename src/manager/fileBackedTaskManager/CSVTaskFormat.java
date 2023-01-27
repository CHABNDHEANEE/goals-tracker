package manager.fileBackedTaskManager;

import manager.historyManager.HistoryManager;
import manager.inMemoryManager.InMemoryTaskManager;
import task.TaskType;
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

    static final public DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd; HH:mm:ss");

    static String getAllTasks(InMemoryTaskManager inMemoryTaskManager) {
        StringBuilder result = new StringBuilder();

        for (Task task : inMemoryTaskManager.getTasks()) {
            result.append(task.toString()).append("\n");
        }

        for (Task task : inMemoryTaskManager.getSubtasks()) {
            result.append(task.toString()).append("\n");
        }

        for (Task task : inMemoryTaskManager.getEpics()) {
            result.append(task.toString()).append("\n");
        }

        return result.toString();
    }

    static String historyToString(HistoryManager historyManager) {
        StringBuilder result = new StringBuilder();

        for (Task task : historyManager.getHistory()) {
            result.append(task.getUid()).append(",");
        }

        if (result.length() > 0) result = new StringBuilder(result.substring(0, result.length() - 1));

        return result.toString();
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
