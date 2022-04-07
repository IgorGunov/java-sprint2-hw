package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class InMemoryTaskManagerTest {

    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));
    InMemoryTaskManager managerInMemory = new InMemoryTaskManager();
    HashMap list = new HashMap<>();

    @Test
    void getTasks() {
        Assertions.assertEquals(0, managerInMemory.getTasks().size());
        Task task = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        Task task2 = new Task("wevffvf", "wersvg", 1, Status.IN_PROGRESS, TypeTask.TASK, duration, startTime);
        managerInMemory.addTask(task);
        managerInMemory.addTask(task2);
        list.put(task.getId(), task);
        list.put(task2.getId(), task2);
        Assertions.assertEquals(list.get(0), managerInMemory.getTasks().get(0));
        Assertions.assertEquals(list.get(1), managerInMemory.getTasks().get(1));
    }

    @Test
    void getSubtaskTasks() {
        Assertions.assertEquals(0, managerInMemory.getSubtaskTasks().size());
        Epic epic = new Epic("qwe", "qwerty", 2);
        Subtask task = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Subtask task2 = new Subtask("wevffvf", "wersvg", 1, 2, Status.IN_PROGRESS, duration, startTime);
        managerInMemory.addEpic(epic);
        managerInMemory.addSubtask(task);
        managerInMemory.addSubtask(task2);
        list.put(task.getId(), task);
        list.put(task2.getId(), task2);
        Assertions.assertEquals(list.get(0), managerInMemory.getSubtaskTasks().get(0));
        Assertions.assertEquals(list.get(1), managerInMemory.getSubtaskTasks().get(1));
    }

    @Test
    void getEpicTasks() {
        Assertions.assertEquals(0, managerInMemory.getEpicTasks().size());
        Epic epic = new Epic("qwe", "qwerty", 0);
        Epic epic2 = new Epic("qwe", "qwerty", 1);
        managerInMemory.addEpic(epic);
        managerInMemory.addEpic(epic2);
        list.put(epic.getId(), epic);
        list.put(epic2.getId(), epic2);
        Assertions.assertEquals(list.get(0), managerInMemory.getEpicTasks().get(0));
        Assertions.assertEquals(list.get(1), managerInMemory.getEpicTasks().get(1));
    }

    @Test
    void getId() {
        Assertions.assertEquals(managerInMemory.getId(), 0);
        Epic epic = new Epic("qwe", "qwerty", 0);
        managerInMemory.addEpic(epic);
        Assertions.assertEquals(managerInMemory.getId(), 1);
    }

    @Test
    void getTaskById() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        managerInMemory.addEpic(epic);
        Assertions.assertEquals(epic, managerInMemory.getEpicTasks().get(0));
    }

    @Test
    void addSubtask() {
        Subtask task = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Epic epic = new Epic("qwe", "qwerty", 2);
        managerInMemory.addEpic(epic);
        managerInMemory.addSubtask(task);
        Assertions.assertEquals(managerInMemory.getSubtaskTasks().get(0), task);
    }

    @Test
    void updateTask() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        Epic epic2 = new Epic("xcvb", "frgt", 0);
        managerInMemory.addEpic(epic);
        managerInMemory.updateTask(epic2);
        Assertions.assertEquals(epic2, managerInMemory.getEpicTasks().get(0));
    }

    @Test
    void addEpic() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        managerInMemory.addEpic(epic);
        Assertions.assertEquals(epic, managerInMemory.getTaskById(2));
    }

    @Test
    void addTask() {
        Task task = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        managerInMemory.addTask(task);
        Assertions.assertEquals(task, managerInMemory.getTaskById(0));
    }

    @Test
    void deleteAllEpic() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        Epic epic1 = new Epic("q3we", "qwe45rty", 1);
        managerInMemory.addEpic(epic);
        managerInMemory.addEpic(epic1);
        managerInMemory.deleteAllEpic();
        Assertions.assertEquals(managerInMemory.getEpicTasks().size(), 0);
    }

    @Test
    void deleteAllSubtask() {
        Subtask task = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Subtask task2 = new Subtask("wevffvf", "wersvg", 1, 2, Status.IN_PROGRESS, duration, startTime);
        managerInMemory.addSubtask(task);
        managerInMemory.addSubtask(task2);
        managerInMemory.deleteAllSubtask();
        Assertions.assertEquals(0, managerInMemory.getSubtaskTasks().size());
    }

    @Test
    void deleteAllTask() {
        Task task = new Task("weeevf", "werddg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        managerInMemory.addTask(task);
        Task task2 = new Task("wevf", "werg", 1, Status.DONE, TypeTask.TASK, duration, startTime);
        managerInMemory.addTask(task2);
        managerInMemory.deleteAllTask();
        Assertions.assertEquals(0, managerInMemory.getTasks().size());
    }

    @Test
    void deleteTaskId() {
        Task task = new Task("weeevf", "werddg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        managerInMemory.addTask(task);
        managerInMemory.deleteTaskId(0);
        Assertions.assertEquals(0, managerInMemory.getTasks().size());
    }

    @Test
    void getHistory() {
        Epic epic = new Epic("qwe", "qwerty", 2);
        managerInMemory.addEpic(epic);
        managerInMemory.getTaskById(2);
        List<Task> history = new ArrayList<>();
        history.add(epic);
        Assertions.assertEquals(history, managerInMemory.getHistory());
    }
}