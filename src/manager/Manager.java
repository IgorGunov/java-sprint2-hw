package manager;
import task.Epic;
import task.Subtask;
import task.Task;
import java.util.HashMap;

public class Manager {
    private int id = 0;
    private HashMap<Integer, Task> task = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskTask = new HashMap<>();
    private HashMap<Integer, Epic> epicTask = new HashMap<>();

    public HashMap<Integer, Task> getTasks() {
        return new HashMap<>(task);
    }

    public HashMap<Integer, Subtask> getSubtaskTasks() {
        return new HashMap<>(subtaskTask);
    }

    public HashMap<Integer, Epic> getEpicTasks() {
        return new HashMap<>(epicTask);
    }

    public int getId() {
        while (getTasks().containsKey(id) || getEpicTasks().containsKey(id) || getSubtaskTasks().containsKey(id)) {
            id++;
        }
        return id;
    }

    public Task getTaskById(int numberId){
        if (task.containsKey(numberId)) {
            return task.get(numberId);
        } else if (subtaskTask.containsKey(numberId)) {
            return subtaskTask.get(numberId);
        } else {
            return epicTask.getOrDefault(numberId, null);
        }
    }

    public void addSubtask(Subtask subtask) {
        if (epicTask.containsKey(subtask.getIdEpic())) {
            subtaskTask.put(subtask.getId(), subtask);
            epicTask.get(subtask.getIdEpic()).setArrayListSubtask(subtask);
        }
    }

    public void updateTask(Task newTask) {
        if (newTask instanceof Epic) {
            epicTask.put(newTask.getId(), (Epic) newTask);
        } else if (newTask instanceof Subtask) {
            subtaskTask.put(newTask.getId(), (Subtask) newTask);
        } else {
            task.put(newTask.getId(), newTask);
        }
    }

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

    public void addTask(Task tasks) {
        task.put(tasks.getId(), tasks);
    }

    public void deleteAllEpic() {
        epicTask.clear();
        subtaskTask.clear();
    }

    public void deleteAllSubtask() {
        subtaskTask.clear();
        for (Epic epic : epicTask.values()) {
            epic.clearArrayList(epic);
        }
    }

    public void deleteAllTask() {
        task.clear();
    }

    public void deleteTaskId(int idTask) {
        if (epicTask.containsKey(idTask)) {
                for (Subtask sub : epicTask.get(idTask).getArrayListSubtask()) {
                if (sub.getIdEpic() == idTask)
                    subtaskTask.remove(sub.getId());
            }
            epicTask.remove(idTask);
        } else if (task.containsKey(idTask)) {
            task.remove(idTask);
        } else if (subtaskTask.containsKey(idTask)) {
            epicTask.get(subtaskTask.get(idTask).getIdEpic()).removeSubtaskInArrayList(subtaskTask.get(idTask));
            subtaskTask.remove(idTask);
        }
    }
}