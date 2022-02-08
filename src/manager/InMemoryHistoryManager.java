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
    public ArrayList<Task> getHistory() {
        return history.getTask(history);
    }

    @Override
    public void removeNode(Node<Task> node) {
        Node<Task> next = node.next;
        Node<Task> prev = node.prev;
        next.prev = prev;
        prev.next = next;
    }

    class MyLinkedList<Task> extends LinkedList {
        private Node<Task> head;
        private Node<Task> tail;
        private int size = 0;
        private final HashMap<Integer, Node<Task>> entities = new HashMap<>();

        public void linkLast(int id, Task element) {
            if (entities.containsKey(id)) {
                removeNode((Node<task.Task>) entities.get(id));
                entities.remove(id);
            }
            entities.put(id, (Node<Task>) element);

            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(null, element, oldTail);
            tail = newNode;
            if (oldTail == null)
                head = newNode;

            else {
                oldTail.prev = newNode;
            }
            size++;
        }

        public ArrayList<task.Task> getTask(MyLinkedList<task.Task> history) {
            ArrayList<task.Task> listHistory = new ArrayList<>();
            while (listHistory.size() != history.size()) {
                Node<Task> oldTail = tail;
                listHistory.add((task.Task) oldTail.data);
            }
            return listHistory;
        }
    }
}
