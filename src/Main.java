import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        Task task1 = new Task("Помыть посуду", "Составить всю посоуду в посудомойку.");
        Task task2 = new Task("Постирать одежду", "Сложить одежду в стиральную машину.");
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);

        Epic epic1 = new Epic("Переезд", "Заняться переездом.");
        inMemoryTaskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Упаковать вещи", "Разложить вещи по коробкам.", epic1.getUid());
        Subtask subtask2 = new Subtask("Перевезти вещи", "Увезти все вещи и распаковать их.", epic1.getUid());
        inMemoryTaskManager.createSubtask(subtask1);
        inMemoryTaskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Пополнить запасы", "Сходить в магазин за продуктами.");
        inMemoryTaskManager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Купить продукты", "Купить все необходимые продукты в магазине.",
                epic2.getUid());
        inMemoryTaskManager.createSubtask(subtask3);

        task1.setStatus("DONE");
        task2.setStatus("IN_PROGRESS");
        inMemoryTaskManager.updateTask(task1);
        inMemoryTaskManager.updateTask(task2);

        subtask1.setStatus("DONE");
        subtask3.setStatus("DONE");
        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask3);

        inMemoryTaskManager.deleteEpicById(2);
        inMemoryTaskManager.deleteTaskById(1);

        System.out.println(inMemoryTaskManager.getTasks());
        System.out.println(inMemoryTaskManager.getSubtasks());
        System.out.println(inMemoryTaskManager.getEpics());
        System.out.println(inMemoryTaskManager.getTaskById(0));
        System.out.println(inMemoryTaskManager.getTaskById(0));
        System.out.println(inMemoryTaskManager.getEpicById(5));
        System.out.println(inMemoryHistoryManager.getHistory());
    }
}
