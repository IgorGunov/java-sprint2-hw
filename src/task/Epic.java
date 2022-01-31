package task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> arrayListSubtask = new ArrayList<>();

    public Epic(String title, String description, int id, Status status) {
        super(title, description, id, status);
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
        } else if (getListSubtask().size() == numberDone || getListSubtask().size() == 0) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }
}