package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private String title;
    private String description;
    private int id;
    private final Status status;
    private final TypeTask typeTask;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime getEndTime;

    public Task(String title, String description, int id, Status status, TypeTask typeTask,
                Duration duration, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.typeTask = typeTask;
        this.duration = duration;
        this.startTime = startTime;
        getEndTime = startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getGetEndTime() {
        return getEndTime;
    }

    public String toString() {
        return (typeTask + "," + getId() + "," + getTitle() + "," + getStatus() + "," + getDescription()
                + "," + getDuration().getSeconds() + "," + getStartTime());
    }

    public TypeTask getTypeTask() {
        return typeTask;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }
}
