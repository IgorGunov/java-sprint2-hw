package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task.*;
import java.time.Duration;
import java.time.LocalDateTime;

class InMemoryHistoryManagerTest {
    private final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));

    @Test
    void add() {
        Task task = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        historyManager.add(task);
        Assertions.assertEquals(historyManager.getHistory().get(0), task);
    }

    @Test
    void removeTaskFromHistory() {
        Task task = new Task("wevf", "werg", 3, Status.NEW, TypeTask.TASK, duration, startTime);
        Epic epic = new Epic("qwe", "qwerty", 2);
        Subtask subtask = new Subtask("wevf", "werg", 0, 2, Status.NEW, duration, startTime);
        Subtask subtask2 = new Subtask("wevffvf", "wersvg", 1, 2, Status.IN_PROGRESS, duration, startTime);
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.add(subtask2);
        historyManager.removeTaskFromHistory(subtask);
        Assertions.assertEquals(historyManager.getHistory().size(), 3);
        Assertions.assertEquals(historyManager.getHistory().get(0), task);
        Assertions.assertEquals(historyManager.getHistory().get(1), epic);
        Assertions.assertEquals(historyManager.getHistory().get(2), subtask2);
        historyManager.removeTaskFromHistory(task);
        Assertions.assertEquals(historyManager.getHistory().size(), 2);
        Assertions.assertEquals(historyManager.getHistory().get(0), epic);
        Assertions.assertEquals(historyManager.getHistory().get(1), subtask2);
        historyManager.add(task);
        historyManager.removeTaskFromHistory(task);
        Assertions.assertEquals(historyManager.getHistory().size(), 2);
        Assertions.assertEquals(historyManager.getHistory().get(0), epic);
        Assertions.assertEquals(historyManager.getHistory().get(1), subtask2);
    }

    @Test
    void getHistory() {
        Assertions.assertEquals(historyManager.getHistory().size(), 0);
        Task task = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
        historyManager.add(task);
        historyManager.add(task);
        Assertions.assertEquals(historyManager.getHistory().get(0), task);
        Assertions.assertEquals(historyManager.getHistory().size(), 1);
    }
}