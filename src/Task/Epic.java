package Task;

import Manager.Manager;

import java.util.ArrayList;

public class Epic {
    private String title;
    private String description;
    private int id;
    private String status;

    private ArrayList<Integer> idSubtask = new ArrayList<>();

    public Epic(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = "NEW";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Integer> getIdSubtask() {
        return idSubtask;
    }

    public void setIdSubtask(int id) {
        idSubtask.add(id);
    }
}