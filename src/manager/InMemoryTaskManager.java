package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> task = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskTask = new HashMap<>();
    private HashMap<Integer, Epic> epicTask = new HashMap<>();
    private HistoryManager history = Managers.getDefaultHistory();

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
        if (epicTask.containsKey(subtask.getEpicId())) {
            subtaskTask.put(subtask.getId(), subtask);
            epicTask.get(subtask.getEpicId()).addSubtaskInList(subtask);
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
        if (!epicTask.get(epic.getId()).getListSubtask().isEmpty()) {
            for (Subtask sub: epicTask.get(getId()).getListSubtask()) {
                for (int idSubtask: subtaskTask.keySet()) {
                    if (idSubtask == sub.getId() && subtaskTask.get(idSubtask).getEpicId() == 0) {
                        subtaskTask.get(idSubtask).setEpicId(sub.getId());
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
        for (Epic epic: getEpicTasks().values()) {
            history.removeNodeOnTask(epic);
        }
    }

    @Override
    public void deleteAllSubtask() {
        for (Epic epic : epicTask.values()) {
            epic.clearList();
        }
        for (Subtask subtask: getSubtaskTasks().values()) {
            history.removeNodeOnTask(subtask);
        }
        subtaskTask.clear();
    }

    @Override
    public void deleteAllTask() {
        for (Task tasks: task.values()) {
            history.removeNodeOnTask(tasks);
        }
        task.clear();
    }

    @Override
    public void deleteTaskId(int idTask) {
        history.removeNodeOnTask(getTaskById(idTask));
        if (epicTask.containsKey(idTask)) {
            for (Subtask sub : epicTask.get(idTask).getListSubtask()) {
                if (sub.getEpicId() == idTask) {
                    subtaskTask.remove(sub.getId());
                    history.removeNodeOnTask(sub);
                }
            }
            epicTask.remove(idTask);
        } else if (task.containsKey(idTask)) {
            task.remove(idTask);
        } else if (subtaskTask.containsKey(idTask)) {
            epicTask.get(subtaskTask.get(idTask).getEpicId()).removeSubtaskInList(subtaskTask.get(idTask));
            subtaskTask.remove(idTask);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }
}