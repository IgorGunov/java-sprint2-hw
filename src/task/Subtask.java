package task;

public class Subtask extends Task {
    private int epicId = 0;
    private final TypeTask typeTask = TypeTask.SUBTASK;

    public Subtask(String title, String description, int id, int idEpic, Status status) {
        super(title, description, id, status);
        this.epicId = idEpic;
    }

    @Override
    public String toString() {
        return (typeTask + "," + getId() + "," + getTitle() + "," +
                getStatus() + "," + getDescription() + "," + getEpicId());
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}