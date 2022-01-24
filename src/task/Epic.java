package task;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> idSubtask = new ArrayList<>();

    public Epic(String title, String description, int id, String status) {
        super(title, description, id, status);
    }

    public ArrayList<Integer> getIdSubtask() {
        return idSubtask;
    }

    public void setIdSubtask(int id) {
            idSubtask.add(id);
    }

    public void removeIdArraylist(Object id) {
        idSubtask.remove(id);
    }
}