package task;

public class Subtask extends Task {
    private int idEpic;

    public Subtask(String title, String description, int id, int idEpic, String status) {
        super(title, description, id, status);
        this.idEpic= idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }
}