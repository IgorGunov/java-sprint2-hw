package task;

public class Task {
    private String title;
    private String description;
    private int id;
    private final Status status;
    private final TypeTask typeTask;

    public Task(String title, String description, int id, Status status, TypeTask typeTask) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.typeTask = typeTask;
    }

    public String toString() {
        return (typeTask + "," + getId() + "," + getTitle() + "," + getStatus() + "," + getDescription());
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
