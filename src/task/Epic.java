package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> arrayListSubtask = new ArrayList<>();

    public Epic(String title, String description, int id, String status) {
        super(title, description, id, status);
    }

    public ArrayList<Subtask> getArrayListSubtask() {
        return arrayListSubtask;
    }

    public void setArrayListSubtask(Subtask sub) {
            arrayListSubtask.add(sub);
    }

    public void clearArrayList(Epic epic) {
        epic.arrayListSubtask.clear();
    }

    public void removeSubtaskInArrayList(Subtask sub) {
        arrayListSubtask.remove(sub);
    }

    @Override
    public String getStatus() {
        int numberNew = 0;
        int numberDone = 0;
        for (Subtask sub : getArrayListSubtask()) {
            if (sub.getStatus().equals("NEW")) {
                numberNew++;
            } else if (sub.getStatus().equals("DONE")) {
                numberDone++;
            }
        }
        if (getArrayListSubtask().size() == numberNew) {
            return "NEW";
        } else if (getArrayListSubtask().size() == numberDone || getArrayListSubtask().size() == 0) {
            return "DONE";
        } else {
            return "IN_PROGRESS";
        }
    }
}