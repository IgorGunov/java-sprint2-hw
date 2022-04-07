package task;

import manager.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeSet;

public class Subtask extends Task {
    private int epicId = 0;

    public Subtask(String title, String description, int id, int idEpic, Status status, Duration duration, LocalDateTime startTime) {
        super(title, description, id, status, TypeTask.SUBTASK, duration, startTime);
        this.epicId = idEpic;
    }

    @Override
    public String toString() {
        return (getTypeTask() + "," + getId() + "," + getTitle() + "," +
                getStatus() + "," + getDescription() + "," + getEpicId() + "," + getDuration().getSeconds() + "," + getStartTime());
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}