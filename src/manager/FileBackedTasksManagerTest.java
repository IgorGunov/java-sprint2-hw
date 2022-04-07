package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.*;
import java.time.Duration;
import java.time.LocalDateTime;

class FileBackedTasksManagerTest {
    FileBackedTasksManager fileManager = new FileBackedTasksManager("D:/word.txt");
    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));

    @Test
    void add() {
        fileManager.loadFromFile();
        Assertions.assertEquals(fileManager.getTasks().size(),0);
        Assertions.assertEquals(fileManager.getEpicTasks().size(),0);
        Assertions.assertEquals(fileManager.getSubtaskTasks().size(),0);
        Assertions.assertEquals(fileManager.getHistory().size(),0);
        Epic epic = new Epic("qwe", "qwerty", 2);
        Task task = new Task("wevf", "werg", 3, Status.NEW, TypeTask.TASK, duration, startTime);
        fileManager.addEpic(epic);
        fileManager.addTask(task);
        fileManager.deleteAllTask();
        fileManager.deleteAllEpic();
        fileManager.loadFromFile();
        Assertions.assertEquals(fileManager.getHistory().size(),0);
        Assertions.assertEquals(fileManager.getTasks().size(),1);
        Assertions.assertEquals(fileManager.getEpicTasks().size(),1);
    }

}