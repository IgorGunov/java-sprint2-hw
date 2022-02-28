package task;

public class Task {
    private String title;
    private String description;
    private int id;
    private Status status;

    public Task(String title, String description, int id, Status status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String toString() {
        return (getId() + "," + "Task" + "," + getTitle() + "," + getStatus() + "," + getDescription());
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
