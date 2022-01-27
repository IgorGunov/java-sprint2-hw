package task;

public class Task {
    private String title;
    private String description;
    private int id;
    private String status;

    public Task(String title, String description, int id, String status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
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
}
