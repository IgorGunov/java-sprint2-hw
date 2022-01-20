import java.util.ArrayList;

public class Epic extends Task {

    ArrayList<Integer> idSubtask = new ArrayList<>();

    public Epic(String title, String description, int id) {
        super(title, description, id);
    }
}