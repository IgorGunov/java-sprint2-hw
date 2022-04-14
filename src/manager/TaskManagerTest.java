package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskManagerTest <T extends TaskManager> {
    protected T manager;
    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));

    @BeforeAll

    @BeforeEach
    public abstract void initializeManager();

    @Test
    void shouldSubtaskHaveEpic() {
        Subtask task = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Assertions.assertEquals(task.getEpicId(),2);
    }


    @Test
    void getTasks() {
        Task task = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        Task task2 = new Task("wevffvf", "wersvg", 1, Status.IN_PROGRESS, TypeTask.TASK, duration, startTime);
        manager.addTask(task);
        manager.addTask(task2);
        Assertions.assertEquals(task, manager.getTasks().get(0));
        Assertions.assertEquals(task2, manager.getTasks().get(1));
        Assertions.assertEquals(manager.getTasks().size(),2);
    }

    @Test
    void getSubtaskTasks() {
        Assertions.assertEquals(0, manager.getSubtaskTasks().size());
        Epic epic = new Epic("qwe", "qwerty", 2);
        Subtask task = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Subtask task2 = new Subtask("wevffvf", "wersvg", 1, 2, Status.IN_PROGRESS, duration, startTime);
        manager.addEpic(epic);
        manager.addSubtask(task);
        manager.addSubtask(task2);
        Assertions.assertEquals(task, manager.getSubtaskTasks().get(0));
        Assertions.assertEquals(task2, manager.getSubtaskTasks().get(1));
        Assertions.assertEquals(manager.getSubtaskTasks().size(),2);
    }

    @Test
    void getEpicTasks() {
        Assertions.assertEquals(0, manager.getEpicTasks().size());
        Epic epic = new Epic("qwe", "qwerty", 0);
        Epic epic2 = new Epic("qwe", "qwerty", 1);
        manager.addEpic(epic);
        manager.addEpic(epic2);
        Assertions.assertEquals(epic, manager.getEpicTasks().get(0));
        Assertions.assertEquals(epic2, manager.getEpicTasks().get(1));
        Assertions.assertEquals(manager.getEpicTasks().size(),2);
    }

    @Test
    void getId() {
        Assertions.assertEquals(manager.getId(), 0);
        Epic epic = new Epic("qwe", "qwerty", 0);
        manager.addEpic(epic);
        Assertions.assertEquals(manager.getId(), 1);
    }

    @Test
    void getTaskById() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        manager.addEpic(epic);
        Assertions.assertEquals(epic, manager.getEpicTasks().get(0));
    }

    @Test
    void addSubtask() {
        Subtask subtask = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Epic epic = new Epic("qwe", "qwerty", 2);
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        Assertions.assertEquals(manager.getSubtaskTasks().get(0), subtask);
    }

    @Test
    void updateTask() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        Epic epic2 = new Epic("xcvb", "frgt", 0);
        manager.addEpic(epic);
        manager.updateTask(epic2);
        Assertions.assertEquals(epic2, manager.getEpicTasks().get(0));
    }

    @Test
    void addEpic() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        manager.addEpic(epic);
        Assertions.assertEquals(epic, manager.getTaskById(2));
    }

    @Test
    void addTask() {
        Task task = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        manager.addTask(task);
        Assertions.assertEquals(task, manager.getTaskById(0));
    }

    @Test
    void deleteAllEpic() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        Epic epic1 = new Epic("q3we", "qwe45rty", 1);
        manager.addEpic(epic);
        manager.addEpic(epic1);
        manager.deleteAllEpic();
        Assertions.assertEquals(manager.getEpicTasks().size(), 0);
    }

    @Test
    void deleteAllSubtask() {
        Subtask task = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Subtask task2 = new Subtask("wevffvf", "wersvg", 1, 2, Status.IN_PROGRESS, duration, startTime);
        manager.addSubtask(task);
        manager.addSubtask(task2);
        manager.deleteAllSubtask();
        Assertions.assertEquals(0, manager.getSubtaskTasks().size());
    }

    @Test
    void deleteAllTask() {
        Task task = new Task("weeevf", "werddg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        manager.addTask(task);
        Task task2 = new Task("wevf", "werg", 1, Status.DONE, TypeTask.TASK, duration, startTime);
        manager.addTask(task2);
        manager.deleteAllTask();
        Assertions.assertEquals(0, manager.getTasks().size());
    }

    @Test
    void deleteTaskId() {
        Task task = new Task("weeevf", "werddg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        manager.addTask(task);
        manager.deleteTaskId(0);
        Assertions.assertEquals(0, manager.getTasks().size());
    }

    @Test
    void getHistory() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        manager.addEpic(epic);
        manager.getTaskById(2);
        List<Task> history = new ArrayList<>();
        history.add(epic);
        Assertions.assertEquals(history, manager.getHistory());
    }
}
