import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Помыть посуду", "Составить всю посоуду в посудомойку.");
        Task task2 = new Task("Постирать одежду", "Сложить одежду в стиральную машину.");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Переезд", "Заняться переездом.");
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid());
        Subtask subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи и распаковать их.", epic1.getUid());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");
        taskManager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Купить продукты", "Купить все необходимые продукты в магазине.",
                epic2.getUid());
        taskManager.createSubtask(subtask3);

        task1.setStatus("DONE");
        task2.setStatus("IN_PROGRESS");
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        subtask1.setStatus("DONE");
        subtask3.setStatus("DONE");
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask3);

        taskManager.deleteEpicById(2);
        taskManager.deleteTaskById(1);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getTaskById(0));
        System.out.println(taskManager.getTaskById(0));
        System.out.println(taskManager.getEpicById(5));
        System.out.println(taskManager.getHistory());
    }
}
