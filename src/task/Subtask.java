package task;

public class Subtask extends Task {
    private int epicId = 0;
    private TypeTask typeTask;

    public Subtask(TypeTask typeTask, String title, String description, int id, int idEpic, Status status) {
        super(typeTask, title, description, id, status);
        this.epicId = idEpic;
        this.typeTask = typeTask;
    }

    @Override
    public String toString() {
        return (typeTask + "," + getId() + "," + getTitle() + "," + getStatus() + "," + getDescription() + "," + getEpicId());
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}