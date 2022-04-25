package manager;

import Http.HTTPTaskManager;

public class Managers {

    public static HTTPTaskManager getDefaultTaskManager(){
        return new HTTPTaskManager("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
