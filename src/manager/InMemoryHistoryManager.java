package manager;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager{
    private MyLinkedList<Task> history = new MyLinkedList<>();

    @Override
    public void add(Task task) {
        history.linkLast(task.getId(), task);
    }

    @Override
    public void removeTaskFromHistory(task.Task task) {
        HashMap<Integer, Node<Task>> entities = history.getMap();
        if (entities.containsKey(task.getId())) {
            history.removeNode(entities.get(task.getId()));
            entities.remove(task.getId());
            history.setMap(entities);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history.getTask();
    }

    class MyLinkedList<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;
        private HashMap<Integer, Node<T>> entities = new HashMap<>();

        public HashMap<Integer, Node<T>> getMap() {
            return entities;
        }

        public void setMap(HashMap<Integer, Node<T>> entities) {
            this.entities = entities;
        }

        public void linkLast(int id, T element) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(oldTail, element, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else {
                oldTail.setNext(newNode);
            }
            size++;
            if (entities.containsKey(id)) {
                removeNode(entities.get(id));
                entities.remove(id);
            }
            entities.put(id, newNode);
        }

        public void removeNode(Node<T> node) {
            if (node.getNext() != null && node.getPrev() != null) {
                Node<T> next = node.getNext();
                Node<T> prev = node.getPrev();
                next.setPrev(prev);
                prev.setNext(next);
                size --;
            } else if (node.getPrev() == null && node.getNext() != null) {
                head = node.getNext();
                Node<T> next = node.getNext();
                next.setPrev(null);
                size --;
            } else if (node.getNext() == null && node.getPrev() != null) {
                tail = node.getPrev();
                Node<T> prev = node.getPrev();
                prev.setNext(null);
                size --;
            } else {
                tail = null;
                head = null;
            }
        }

        public ArrayList<T> getTask() {
            ArrayList<T> listHistory = new ArrayList<>(size);
            Node<T> oldTail = head;

            while (oldTail != null) {
                listHistory.add(oldTail.getData());
                oldTail = oldTail.getNext();
            }
            return listHistory;
        }
    }
}
