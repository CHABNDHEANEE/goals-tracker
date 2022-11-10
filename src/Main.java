import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Помыть посуду", "Составить всю посоуду в посудомойку.");
        Task task2 = new Task("Постирать одежду", "Сложить одежду в стиральную машину.");
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Переезд", "Заняться переездом.");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid());
        Subtask subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи и распаковать их.", epic1.getUid());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        Epic epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Купить продукты", "Купить все необходимые продукты в магазине.",
                epic2.getUid());
        manager.createSubtask(subtask3);

        task1.setStatus("DONE");
        task2.setStatus("IN_PROGRESS");
        manager.updateTask(task1);
        manager.updateTask(task2);

        subtask1.setStatus("DONE");
        subtask3.setStatus("DONE");
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask3);

        manager.deleteEpicById(2);
        manager.deleteTaskById(1);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubtasks());
        System.out.println(manager.getAllEpics());
    }
}
