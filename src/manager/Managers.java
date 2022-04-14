package manager;

public class Managers {

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static TaskManager getDefaultFileBacked() {
        return new FileBackedTasksManager(getDefaultHistory(),"word.txt");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
