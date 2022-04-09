package task;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;

public class EpicTest {
    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));
    private static final InMemoryTaskManager manager = new InMemoryTaskManager();

    @Test
    public void shouldEpicEmptyList() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        manager.addEpic(epic);
        Assertions.assertEquals(epic.getStatus(), Status.NEW);
        Assertions.assertEquals(manager.getEpicTasks().get(0), Status.NEW);
    }

    @Test
    public void shouldEpicAllSubtaskNew() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        Subtask subtask = new Subtask("qwe", "qwerty", 1, 0, Status.NEW, duration, startTime);
        Subtask subtask2 = new Subtask("qwe", "qwerty", 2, 0, Status.NEW, duration, startTime);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask2);
        Assertions.assertEquals(epic.getStatus(), Status.NEW);
    }

    @Test
    public void shouldEpicAllSubtaskDone() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        Subtask subtask = new Subtask("qwe", "qwerty", 1, 0, Status.DONE, duration, startTime);
        Subtask subtask2 = new Subtask("qwe", "qwerty", 2, 0, Status.DONE, duration, startTime);
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask2);
        Assertions.assertEquals(epic.getStatus(), Status.DONE);
    }

    @Test
    public void shouldEpicSubtaskDoneAndNew() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        Subtask subtask = new Subtask("qwe", "qwerty", 1, 0, Status.NEW, duration, startTime);
        Subtask subtask2 = new Subtask("qwe", "qwerty", 2, 0, Status.DONE, duration, startTime);
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask2);
        Assertions.assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void shouldEpicAllSubtaskInprocess() {
        Epic epic = new Epic("qwe", "qwerty", 0);
        Subtask subtask = new Subtask("qwe", "qwerty", 1, 0, Status.IN_PROGRESS, duration, startTime);
        Subtask subtask2 = new Subtask("qwe", "qwerty", 2, 0, Status.IN_PROGRESS, duration, startTime);
        manager.addEpic(epic);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask2);
        Assertions.assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }

}