package manager;
import task.Epic;
import task.Subtask;
import task.Task;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> task = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskTask = new HashMap<>();
    private HashMap<Integer, Epic> epicTask = new HashMap<>();
    private static final HistoryManager history = Managers.getDefaultHistory();

    @Override
    public HashMap<Integer, Task> getTasks() {
        return new HashMap<>(task);
    }

    @Override
    public HashMap<Integer, Subtask> getSubtaskTasks() {
        return new HashMap<>(subtaskTask);
    }

    @Override
    public HashMap<Integer, Epic> getEpicTasks() {
        return new HashMap<>(epicTask);
    }

    @Override
    public int getId() {
        while (getTasks().containsKey(id) || getEpicTasks().containsKey(id) || getSubtaskTasks().containsKey(id)) {
            id++;
        }
        return id;
    }

    @Override
    public Task getTaskById(int numberId){
        if (task.containsKey(numberId)) {
            history.add(task.get(numberId));
            return task.get(numberId);
        } else if (subtaskTask.containsKey(numberId)) {

            history.add(subtaskTask.get(numberId));
            return subtaskTask.get(numberId);
        } else {

            history.add(epicTask.get(numberId));
            return epicTask.getOrDefault(numberId, null);
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (epicTask.containsKey(subtask.getIdEpic())) {
            subtaskTask.put(subtask.getId(), subtask);
            epicTask.get(subtask.getIdEpic()).setArrayListSubtask(subtask);
        }
    }

    @Override
    public void updateTask(Task newTask) {
        if (newTask instanceof Epic) {
            epicTask.put(newTask.getId(), (Epic) newTask);
        } else if (newTask instanceof Subtask) {
            subtaskTask.put(newTask.getId(), (Subtask) newTask);
        } else {
            task.put(newTask.getId(), newTask);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        epicTask.put(epic.getId(), epic);
        if (!epicTask.get(epic.getId()).getArrayListSubtask().isEmpty()) {
            for (Subtask sub: epicTask.get(getId()).getArrayListSubtask()) {
                for (int idSubtask: subtaskTask.keySet()) {
                    if (idSubtask == sub.getId() && subtaskTask.get(idSubtask).getIdEpic() == 0) {
                        subtaskTask.get(idSubtask).setIdEpic(sub.getId());
                    }
                }
            }
        }
    }

    @Override
    public void addTask(Task tasks) {
        task.put(tasks.getId(), tasks);
    }

    @Override
    public void deleteAllEpic() {
        epicTask.clear();
        subtaskTask.clear();
        for (Task tasks: history.getHistory()) {
            if (tasks instanceof Epic) {
                history.remove(tasks);
            }
        }
    }

    @Override
    public void deleteAllSubtask() {
        subtaskTask.clear();
        for (Epic epic : epicTask.values()) {
            epic.clearArrayList(epic);
        }
        for (Task tasks: history.getHistory()) {
            if (tasks instanceof Subtask) {
                history.remove(tasks);
            }
        }
    }

    @Override
    public void deleteAllTask() {
        for (Task tasks: history.getHistory()) {
            for (Task task1: task.values()) {
                if (task1 == tasks) {
                    history.remove(tasks);
                }
            }
        }
        task.clear();
    }

    @Override
    public void deleteTaskId(int idTask) {
        if (epicTask.containsKey(idTask)) {
            for (Subtask sub : epicTask.get(idTask).getArrayListSubtask()) {
                if (sub.getIdEpic() == idTask) {
                    subtaskTask.remove(sub.getId());
                }
            }
            for (Task tasks: history.getHistory()) {
                if (tasks == epicTask.get(idTask)) {
                    history.remove(tasks);
                }
            }
            epicTask.remove(idTask);
        } else if (task.containsKey(idTask)) {
            for (Task tasks: history.getHistory()) {
                if (tasks == task.get(idTask)) {
                    history.remove(tasks);
                }
            }
            task.remove(idTask);
        } else if (subtaskTask.containsKey(idTask)) {
            for (Task tasks: history.getHistory()) {
                if (tasks == subtaskTask.get(idTask)) {
                    history.remove(tasks);
                }
            }
            epicTask.get(subtaskTask.get(idTask).getIdEpic()).removeSubtaskInArrayList(subtaskTask.get(idTask));
            subtaskTask.remove(idTask);
        }
    }
}