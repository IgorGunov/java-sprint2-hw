package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest {
    private final LocalDateTime startTime = LocalDateTime.MIN;
    private final Duration duration = Duration.between(startTime, startTime.plusSeconds(10));

    Task task1 = new Task("wevf", "werg", 0, Status.NEW, TypeTask.TASK, duration, startTime);
    Task task2 = new Task("bfhe", "etty", 1, Status.NEW, TypeTask.TASK, duration, startTime);
    Subtask subtask1 = new Subtask("wevf", "werg", 2, 4, Status.NEW, duration, startTime);
    Subtask subtask2 = new Subtask("rtygg", "ekkoitr", 3, 4, Status.NEW, duration, startTime);
    Epic epic = new Epic("qwe", "qwerty", 4);
    private final HTTPTaskManager manager = new HTTPTaskManager("http://localhost:8078/");
    private static KVTaskClient client;
    private static KVServer kvServer;
    private static Gson gson;

    @BeforeAll
    public static void beforeAll(){
        try {
            kvServer = new KVServer();
            kvServer.start();
            gson = new GsonBuilder().setPrettyPrinting().create();
            client = new KVTaskClient("http://localhost:8078/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void taskTask() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task/task/");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(response.body(), manager.getTasks());
    }

    @Test
    void taskSubtask() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task/subtask/");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(response.body(), manager.getSubtaskTasks());
    }

    @Test
    void taskEpic() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task/epic/");
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(response.body(), manager.getEpicTasks());
    }

    @Test
    void saveTask() {
        manager.addTask(task1);
        String body = client.load("tasks");
        JsonArray jsonArray = JsonParser.parseString(body).getAsJsonArray();
        Task taskInServer = gson.fromJson(jsonArray.get(0), Task.class);
        assertEquals(taskInServer, task1);
    }
}