package manager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> history;
    public final HashMap<Integer, Node> nodesHashMap;

    public InMemoryHistoryManager() {
        history = new CustomLinkedList<>();
        nodesHashMap = new HashMap<>();
    }

    @Override
    public void addTask(Task task) {
        if (nodesHashMap.containsKey(task.getUid())) {
            remove(task.getUid());
        }
        history.linkLast(task);
        nodesHashMap.put(task.getUid(), history.tail);
    }

    @Override
    public void remove(int id) {
        history.removeNode(nodesHashMap.get(id));
        nodesHashMap.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history.getTasks();
    }

    static class CustomLinkedList<V> {

        public Node<V> head;
        public Node<V> tail;
        private int size = 0;

        public void linkLast(Task element) {
            final Node<V> oldTail = tail;
            final Node<V> newNode = new Node<>(oldTail, element, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            size++;
        }

        public void removeNode(Node element) {
            Node currNode = head;
            boolean deleted = false;

            if (currNode == element) {
                head = head.next;
                head.prev = null;
                deleted = true;
            }

            while (!deleted) {
                currNode = currNode.next;
                if (currNode == null) {
                    return;
                }
                if (currNode == element) {
                    if (currNode == tail) {
                        currNode.prev.next = null;
                        tail = currNode.prev;
                    } else {
                        currNode.next.prev = currNode.prev;
                    }
                    deleted = true;
                }
            }
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> allTasks = new ArrayList<>();
            Node currNode = tail;

            while (currNode != null) {
                allTasks.add(currNode.data);
                currNode = currNode.prev;
            }

            return allTasks;
        }
    }
}

