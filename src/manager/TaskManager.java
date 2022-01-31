package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Subtask> getSubtaskTasks();

    HashMap<Integer, Epic> getEpicTasks();

    int getId();

    Task getTaskById(int numberId);

    void addSubtask(Subtask subtask);

    void updateTask(Task newTask);

    void addEpic(Epic epic);

    void addTask(Task tasks);

    void deleteAllEpic();

    void deleteAllSubtask();

    void deleteAllTask();

    void deleteTaskId(int idTask);

    List<Task> getHistory();
}
