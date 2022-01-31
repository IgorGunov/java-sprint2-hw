package task;

public class Subtask extends Task {
    private int epicId = 0;

    public Subtask(String title, String description, int id, int idEpic, Status status) {
        super(title, description, id, status);
        this.epicId = idEpic;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}