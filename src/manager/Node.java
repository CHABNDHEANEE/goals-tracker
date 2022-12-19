package manager;

import task.Task;

public class Node<E> {
    public Task data;
    public Node<E> prev;
    public Node<E> next;

    public Node(Node<E> prev, Task data, Node<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
