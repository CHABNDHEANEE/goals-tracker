import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Epic epic1 = new Epic("Переезд", "Заняться переездом.");
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid());
        Subtask subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи.", epic1.getUid());
        Subtask subtask3 = new Subtask("Распаковать вещи", "Распаковать их.", epic1.getUid());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        Epic epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");
        taskManager.createEpic(epic2);


        System.out.println(taskManager.getEpicById(0));
        System.out.println(taskManager.getEpicById(4));
        System.out.println(taskManager.getSubtaskById(2));
        System.out.println(taskManager.getEpicById(0));

        System.out.println(taskManager.getHistory());
        taskManager.deleteEpicById(0);
        System.out.println(taskManager.getHistory());
    }
}
