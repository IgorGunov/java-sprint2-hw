package Http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.FileBackedTasksManager;
import manager.HistoryManager;
import manager.Managers;
import task.Epic;
import task.Subtask;
import task.Task;

import java.net.MalformedURLException;
import java.net.URL;

public class HTTPTaskManager extends FileBackedTasksManager {
    private KVTaskClient client;

    public HTTPTaskManager(String url) {
        super(url);
        client = new KVTaskClient(url);
    }

    @Override
    public void save() {
        Gson gson = new GsonBuilder().create();
        StringBuilder stringBuilder = new StringBuilder();

        for (Task task: getTasks().values()) {
            stringBuilder.append(task);
            String gsonTask = gson.toJson(stringBuilder);
            client.put("task", gsonTask);
        }

        for (Subtask task: getSubtaskTasks().values()) {
            stringBuilder.append(task);
            String gsonTask = gson.toJson(stringBuilder);
            client.put("subtask", gsonTask);
        }

        for (Epic task: getEpicTasks().values()) {
            stringBuilder.append(task);
            String gsonTask = gson.toJson(stringBuilder);
            client.put("epic", gsonTask);
        }
    }

    @Override
    public void loadFromFile() {
        super.loadFromFile();
    }

    @Override
    public void fromString(String value) {
        super.fromString(value);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
    }

    @Override
    public Task getTaskById(int numberId) {
        return super.getTaskById(numberId);
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
    }

    @Override
    public void deleteTaskId(int idTask) {
        super.deleteTaskId(idTask);
    }
}
