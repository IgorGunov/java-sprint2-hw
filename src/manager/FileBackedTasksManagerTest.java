package manager;

import org.junit.jupiter.api.Test;
import task.*;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;


class FileBackedTasksManagerTest {
    private final FileBackedTasksManager fileManager = new FileBackedTasksManager("word.txt");
    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));

    @Test
    void add() {
        fileManager.loadFromFile();
        assertEquals(fileManager.getTasks().size(),0);
        assertEquals(fileManager.getEpicTasks().size(),0);
        assertEquals(fileManager.getSubtaskTasks().size(),0);
        assertEquals(fileManager.getHistory().size(),0);
        Epic epic = new Epic("qwe", "qwerty", 2);
        Task task = new Task("wevf", "werg", 3, Status.NEW, TypeTask.TASK, duration, startTime);
        Subtask subtask = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        fileManager.addEpic(epic);
        fileManager.deleteAllEpic();
        fileManager.loadFromFile();
        assertEquals(fileManager.getEpicTasks().size(),1);
        fileManager.addTask(task);
        fileManager.addSubtask(subtask);
        fileManager.deleteAllSubtask();
        fileManager.deleteAllTask();
        fileManager.deleteAllEpic();
        fileManager.loadFromFile();
        assertEquals(fileManager.getHistory().size(),0);
        assertEquals(fileManager.getTasks().size(),1);
        assertEquals(fileManager.getEpicTasks().size(),1);
    }

}