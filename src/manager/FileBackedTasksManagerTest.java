package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));

    @BeforeEach
    @Override
    public void initializeManager() {
        manager = new FileBackedTasksManager("http://localhost:8078/");
    }

    @Test
    void emptyTaskList() {
        manager.loadFromFile();
        assertEquals(manager.getTasks().size(),0);
        assertEquals(manager.getEpicTasks().size(),0);
        assertEquals(manager.getSubtaskTasks().size(),0);
        assertEquals(manager.getHistory().size(),0);
    }

    @Test
    void epicWuthoutSubtask() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        manager.addEpic(epic);
        manager.deleteAllEpic();
        manager.loadFromFile();
        assertEquals(manager.getEpicTasks().size(),1);
    }

    @Test
    void emptyHistoryList() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        Task task = new Task("wevf", "werg", 3, Status.NEW, TypeTask.TASK, duration, startTime);
        Subtask subtask = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        manager.addEpic(epic);
        manager.addTask(task);
        manager.addSubtask(subtask);
        manager.deleteAllSubtask();
        manager.deleteAllTask();
        manager.deleteAllEpic();
        manager.loadFromFile();
        assertEquals(manager.getHistory().size(),0);
    }
}