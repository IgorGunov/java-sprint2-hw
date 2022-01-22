package task;

public class Subtask {
    private String title;
    private String description;
    private int id;
    private String status;
    private int idEpic;

    public Subtask(String title, String description, int id, int idEpic) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.idEpic = idEpic;
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

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }
}