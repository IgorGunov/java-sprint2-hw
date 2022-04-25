package Http;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.*;
import task.Epic;
import task.Subtask;
import task.Task;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpTaskServer  {

    public static void httpTaskServer() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks/task", new TaskTask());
        httpServer.createContext("/tasks/subtask", new TaskSubtask());
        httpServer.createContext("/tasks/epic", new TaskEpic());
        httpServer.createContext("/tasks/", new TaskPrioritiz());
        httpServer.createContext("/tasks/history", new TaskHistory());
        httpServer.start();
    }
}

class TaskTask implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        TaskManager manager = Managers.getDefaultTaskManager();
        String method = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        String body = new String(httpExchange.getRequestBody().readAllBytes());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Task task = gson.fromJson(body, Task.class);
        String line = "";
        httpExchange.sendResponseHeaders(200, 0);
        switch (method) {
            case "GET":
                if (query == null) {
                    line = gson.toJson(manager.getTasks().values());
                } else {
                    line = gson.toJson(manager.getTaskById(Integer.parseInt(query.split("=")[1])));
                }
                break;
            case "POST":
                if (query == null) {
                    manager.addTask(task);
                } else {
                    manager.updateTask(task);
                }
                break;
            case "DELETE":
                if (query == null) {
                    manager.deleteAllTask();
                } else {
                    int id = Integer.parseInt(query.split("=")[1]);
                    manager.deleteTaskId(id);
                }
                break;
            default:
                line = "нет такого запроса";
        }
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(line.getBytes());
        }
    }
}

class TaskEpic implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        TaskManager manager = Managers.getDefaultTaskManager();
        String method = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        String body = new String(httpExchange.getRequestBody().readAllBytes());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Epic task = gson.fromJson(body, Epic.class);
        String line = "";
        switch (method) {
            case "GET":
                if (query == null) {
                    line = gson.toJson(manager.getEpicTasks().values());
                } else {
                    line = gson.toJson(manager.getTaskById(Integer.parseInt(query.split("=")[1])));
                }
                break;
            case "POST":
                if (query == null) {
                    manager.addEpic(task);
                } else {
                    manager.updateTask(task);
                }
                break;
            case "DELETE":
                if (query == null) {
                    manager.deleteAllEpic();
                } else {
                    int id = Integer.parseInt(query.split("=")[1]);
                    manager.deleteTaskId(id);
                }
                break;
            default:
                line = "нет такого запроса";
        }
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(line.getBytes());
        }
    }
}

class TaskSubtask implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        TaskManager manager = Managers.getDefaultTaskManager();
        String method = httpExchange.getRequestMethod();
        String query = httpExchange.getRequestURI().getQuery();
        String body = new String(httpExchange.getRequestBody().readAllBytes());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Subtask task = gson.fromJson(body, Subtask.class);
        String line = "";
        switch (method) {
            case "GET":
                if (query == null) {
                    line = gson.toJson(manager.getSubtaskTasks().values());
                } else {
                    line = gson.toJson(manager.getTaskById(Integer.parseInt(query.split("=")[1])));
                }
                break;
            case "POST":
                if (query == null) {
                    manager.addSubtask(task);
                } else {
                    manager.updateTask(task);
                }
                break;
            case "DELETE":
                if (query == null) {
                    manager.deleteAllSubtask();
                } else {
                    int id = Integer.parseInt(query.split("=")[1]);
                    manager.deleteTaskId(id);
                }
                break;
            default:
                line = "нет такого запроса";
        }
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(line.getBytes());
        }
    }
}

class TaskPrioritiz implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InMemoryTaskManager manager = Managers.getDefaultTaskManager();
        String method = httpExchange.getRequestMethod();
        String body = new String(httpExchange.getRequestBody().readAllBytes());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Task task = gson.fromJson(body, Task.class);
        String line = "";
        switch (method) {
            case "GET":
                line = gson.toJson(manager.getPrioritizedTasks());
                break;
            case "POST":
                manager.addTask(task);
                break;
            case "DELETE":
                manager.removeTaskInList(task);
                break;
            default:
                line = "нет такого запроса";
        }
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(line.getBytes());
        }
    }
}

class TaskHistory implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HistoryManager manager = Managers.getDefaultHistory();
        String method = httpExchange.getRequestMethod();
        String body = new String(httpExchange.getRequestBody().readAllBytes());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Task task = gson.fromJson(body, Task.class);
        String line = "";
        switch (method) {
            case "GET":
                line = gson.toJson(manager.getHistory());
                break;
            case "POST":
                manager.add(task);
                break;
            case "DELETE":
                manager.removeTaskFromHistory(task);
                break;
            default:
                line = "нет такого запроса";
        }
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(line.getBytes());
        }
    }
}