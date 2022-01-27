package task;

import manager.Manager;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> arrayListSubtask = new ArrayList<>();

    public Epic(String title, String description, int id, String status) {
        super(title, description, id, status);
    }

    public ArrayList<Integer> getArrayListSubtask() {
        return arrayListSubtask;
    }

    public void setArrayListSubtask(Integer sub) {
            arrayListSubtask.add(sub);
    }

    public void removeIdArraylist(Epic epic) {
        epic.arrayListSubtask.clear();
    }

    @Override
    public String getStatus() {
        Manager manager = new Manager();
        int statusNew = 0;
        int statusDone = 0;
        for (int i = 0; i < getArrayListSubtask().size(); i ++) {
                if (manager.getSubtaskTasks().get(arrayListSubtask.get(i)).getStatus().equals("NEW")) {
                    statusNew++;
                } else if (manager.getSubtaskTasks().get(arrayListSubtask.get(i)).getStatus().equals("DONE")) {
                    statusDone++;
                }
        }
        if (getArrayListSubtask().size() == statusNew) {
            return "NEW";
        } else if (getArrayListSubtask().size() == statusDone || getArrayListSubtask().size() == 0) {
            return "DONE";
        } else {
            return "IN_PROGRESS";
        }
    }
}