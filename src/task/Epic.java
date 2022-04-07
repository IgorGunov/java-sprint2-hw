package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> arrayListSubtask = new ArrayList<>();

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW, TypeTask.EPIC,
                Duration.between(LocalDateTime.MIN,LocalDateTime.MIN),
                LocalDateTime.MIN);
    }

    @Override
    public String toString() {
        return (getTypeTask() + "," + getId() + "," + getTitle() + "," +
                getStatus() + "," + getDescription() + "," + getDuration().getSeconds() + "," + getStartTime());
    }

    public List<Subtask> getListSubtask() {
        return arrayListSubtask;
    }

    public void addSubtaskInList(Subtask sub) {
        arrayListSubtask.add(sub);
    }

    public void clearList() {
        arrayListSubtask.clear();
    }

    public void removeSubtaskInList(Subtask sub) {
        arrayListSubtask.remove(sub);
    }

    @Override
    public Status getStatus() {
        int numberNew = 0;
        int numberDone = 0;
        for (Subtask sub : getListSubtask()) {
            if (sub.getStatus().equals(Status.NEW)) {
                numberNew++;
            } else if (sub.getStatus().equals(Status.DONE)) {
                numberDone++;
            }
        }
        if (getListSubtask().size() == numberNew) {
            return Status.NEW;
        } else if (getListSubtask().size() == numberDone) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    @Override
    public Duration getDuration() {
        Duration duration = Duration.between(LocalTime.of(0,0),LocalTime.of(0,0));
        for (Subtask subtask: arrayListSubtask) {
            duration.plus(subtask.getDuration());
        }
        return duration;
    }

    @Override
    public LocalDateTime getStartTime() {
        LocalDateTime startTime = LocalDateTime.MAX;
        for (Subtask subtask: arrayListSubtask) {
            if (subtask.getStartTime().isBefore(startTime))
                startTime = subtask.getStartTime();
        }
        return startTime;
    }
}