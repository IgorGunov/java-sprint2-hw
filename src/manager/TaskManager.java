package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    Map<Integer, Task> getTasks();

    Map<Integer, Subtask> getSubtaskTasks();

    Map<Integer, Epic> getEpicTasks();

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
