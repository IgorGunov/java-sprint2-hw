public class Task {
    String title;
    String description;
    int id;
    String status;

    public Task(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = "NEW";
    }
}
