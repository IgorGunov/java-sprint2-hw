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

    class MyLinkedList<Task> extends LinkedList {
        private Node<Task> head;
        private Node<Task> tail;
        private int size = 0;
        private HashMap<Integer, Node<Task>> entities = new HashMap<>();

        public HashMap<Integer, Node<Task>> getMap() {
            return entities;
        }

        public void setMap(HashMap<Integer, Node<Task>> entities) {
            this.entities = entities;
        }

        public void linkLast(int id, Task element) {
            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(oldTail, element, null);
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

        public void removeNode(Node node) {
            if (node.getNext() != null && node.getPrev() != null) {
                Node<task.Task> next = node.getNext();
                Node<task.Task> prev = node.getPrev();
                next.setPrev(prev);
                prev.setNext(next);
                size --;
            } else if (node.getPrev() == null && node.getNext() != null) {
                history.head = node.getNext();
                Node<task.Task> next = node.getNext();
                next.setPrev(null);
                size --;
            } else if (node.getNext() == null && node.getPrev() != null) {
                history.tail = node.getPrev();
                Node<task.Task> prev = node.getPrev();
                prev.setNext(null);
                size --;
            } else {
                tail = null;
                head = null;
            }
        }

        public ArrayList<Task> getTask() {
            ArrayList<Task> listHistory = new ArrayList<>(history.size);
            Node<Task> oldTail = (Node<Task>) history.head;

            while (oldTail != null) {
                listHistory.add(oldTail.getData());
                oldTail = oldTail.getNext();
            }
            return listHistory;
        }
    }
}
