package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Task;
import task.TypeTask;
import java.time.Duration;
import java.time.LocalDateTime;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));

    @BeforeEach
    @Override
    public void initializeManager() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void addTaskInList() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        manager.addTaskInList(epic);
        Assertions.assertEquals(manager.getPrioritizedTasks().size(),1);
    }

    @Test
    void checkPriority() {
        Task task = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        Task task2 = new Task("wevffvf", "wersvg", 1, Status.IN_PROGRESS, TypeTask.TASK, duration, startTime);
        manager.addTaskInList(task);
        Assertions.assertFalse(manager.checkPriority(task2));
    }
}