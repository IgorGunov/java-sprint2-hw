import server.HTTPTaskManager;
import server.HttpTaskServer;
import server.KVServer;
import server.KVTaskClient;
import task.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    private static final HTTPTaskManager manager = new HTTPTaskManager("http://localhost:8078/");

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpServer = new HttpTaskServer();
        Task task = new Task("Task","Task", 0, Status.DONE,  TypeTask.TASK,
                Duration.ofDays(2) , LocalDateTime.of(2022, 10, 1, 10, 10));
        String json = "{\n" +
                "\t\"title\": \"Subtask\",\n" +
                "\t\"description\": \"Sub \",\n" +
                "\t\"id\": 1,\n" +
                "\t\"status\": \"NEW\",\n" +
                "\t\"type\": \"SUBTASK\",\n" +
                "\t\"epicID\": 0,\n" +
                "\t\"duration\": 0\n" +
                "}";
        KVServer server = new KVServer();
        server.start();
        KVTaskClient client = new KVTaskClient("http://localhost:8078/");
        client.put("DEBUG", json);
        System.out.println(client.load("DEBUG"));
        HTTPTaskManager httpTaskManager = new HTTPTaskManager("http://localhost:8078/");
        manager.addTask(task);
        System.out.println(client.load("DEBUG"));
        httpServer.HttpTaskServer();
    }
}
