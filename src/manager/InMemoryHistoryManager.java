package manager;

import task.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    ArrayList<Task> history = new ArrayList<>();
    @Override
    public void add(Task task) {
        while (history.size() > 10) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }

    @Override
    public void remove(Task task) {
        history.remove(task);
    }
}
