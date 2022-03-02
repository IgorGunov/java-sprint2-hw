package task;

public class Task {
    private String title;
    private String description;
    private int id;
    private Status status;
    private TypeTask typeTask;

    public Task(TypeTask typeTask, String title, String description, int id, Status status) {
        this.typeTask = typeTask;
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
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
