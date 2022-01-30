package manager;

import task.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> history = new ArrayList<>();
    @Override
    public void add(Task task) {
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        while (history.size() > 10) {
            history.remove(0);
        }
        return history;
    }

    @Override
    public void remove(Task task) {
        history.remove(task);
    }
}
