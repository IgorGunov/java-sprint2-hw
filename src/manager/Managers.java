package manager;

public class Managers {

    public TaskManager getDefault() {
        TaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    }

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
