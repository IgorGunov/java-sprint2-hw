package manager;

import task.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    InMemoryTaskManager manager = new InMemoryTaskManager();
    @Override
    public void add(Task task) {
        while (manager.history.size() > 10) {
            manager.history.remove(0);
        }
    }

    @Override
    public ArrayList getHistory() {
        return manager.history;
    }
}
