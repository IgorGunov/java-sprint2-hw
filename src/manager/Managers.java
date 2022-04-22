package manager;

public class Managers {

    public static InMemoryTaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static FileBackedTasksManager getDefaultFileBacked() {
        return new FileBackedTasksManager(getDefaultHistory(),"word.txt");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
