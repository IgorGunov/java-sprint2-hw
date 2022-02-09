package manager;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager{
    private MyLinkedList<Task> history = new MyLinkedList<>();

    @Override
    public void add(Task task) {
        history.linkLast(task.getId(), task);
    }

    @Override
    public void removeNodeOnTask(Task task) {
        for (Node<Task> x = history.head; x != null; x = x.next) {
            if (x.data == task) {
                removeNode(x);
            }
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history.getTask(history);
    }

    @Override
    public void removeNode(Node node) {
        if (node.next != null && node.prev != null) {
            Node<Task> next = node.next;
            Node<Task> prev = node.prev;
            next.prev = prev;
            prev.next = next;
        } else if (node.prev == null && node.next != null) {
            history.head = node.next;
            Node<Task> next = node.next;
            next.prev = null;
        } else if (node.next == null && node.prev != null) {
            history.tail = node.prev;
            Node<Task> prev = node.prev;
            prev.next = null;
        }
    }

    class MyLinkedList<Task> extends LinkedList {
        private Node<Task> head;
        private Node<Task> tail;
        private int size = 0;
        private final HashMap<Integer, Node<Task>> entities = new HashMap<>();

        public void linkLast(int id, Task element) {

            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(oldTail, element, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else {
                oldTail.next = newNode;
            }
            size++;
            if (entities.containsKey(id)) {
                removeNode(entities.get(id));
                entities.remove(id);
            }
            entities.put(id, newNode);
        }

        public ArrayList<Task> getTask(MyLinkedList<Task> history) {
            ArrayList<Task> listHistory = new ArrayList<>();
            Node<Task> oldTail = history.head;

            while (oldTail != null) {
                listHistory.add(oldTail.data);
                oldTail = oldTail.next;
            }
            return listHistory;
        }
    }
}
