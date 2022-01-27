package task;

public class Subtask extends Task {
    private int idEpic = 0;

    public Subtask(String title, String description, int id, int idEpic, Status status) {
        super(title, description, id, status);
        this.idEpic= idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }
}