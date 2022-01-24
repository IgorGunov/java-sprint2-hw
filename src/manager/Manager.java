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
        chekStatus();
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
        } else return epicTask.getOrDefault(numberId, null);
    }

    public void addSubtask(Subtask subtask) {
        subtaskTask.put(subtask.getId(), subtask);
        if (epicTask.containsKey(subtask.getIdEpic())) {
            epicTask.get(subtask.getIdEpic()).setIdSubtask(subtask.getId());
        }
    }

    public void addEpic(Epic epic) {
        epicTask.put(epic.getId(), epic);
    }

    public void addTask(Task tasks) {
        task.put(tasks.getId(), tasks);
    }

    public boolean updateTask(int idTask, Task newTask) {
        if (newTask instanceof Epic && epicTask.containsKey(idTask)) {
            epicTask.put(idTask, (Epic) newTask);
            return false;
        } else if (newTask instanceof Subtask && subtaskTask.containsKey(idTask)) {
            subtaskTask.put(idTask, (Subtask) newTask);
            return false;
        } else if (task.containsKey(idTask)) {
            task.put(idTask, newTask);
            return false;
        } else {
            return true;
        }
    }

    public void deleteEpic() {
        epicTask.clear();
        subtaskTask.clear();
    }

    public void deleteSubtask() {
        subtaskTask.clear();
    }

    public void deleteTask() {
        task.clear();
    }

    public void deleteTaskId(int idTask) {
        if (epicTask.containsKey(idTask)) {
            for (int ids : epicTask.get(idTask).getIdSubtask()) {
                if (subtaskTask.get(ids).getIdEpic() == idTask)
                    subtaskTask.remove(ids);
            }
            epicTask.remove(idTask);
        } else if (task.containsKey(idTask)) {
            task.remove(idTask);
        } else if (subtaskTask.containsKey(idTask)) {
            epicTask.get(subtaskTask.get(idTask).getIdEpic()).removeIdArraylist(idTask);
            subtaskTask.remove(idTask);
        }
    }

    public void chekStatus() {
        String statusChek;
        int error = 0;
        for (Epic epic: epicTask.values()) {
            if (epic.getIdSubtask().size() != 0) {
                statusChek = subtaskTask.get(epic.getIdSubtask().get(0)).getStatus();
                for (Integer subtask : epic.getIdSubtask()) {
                    if (!subtaskTask.get(subtask).getStatus().equals(statusChek)) {
                        error++;
                    }
                }
                if (error == 0) {
                    epic.setStatus(statusChek);
                } else {
                    epic.setStatus("IN_PROGRESS");
                    error = 0;
                }
            }
        }
    }
}