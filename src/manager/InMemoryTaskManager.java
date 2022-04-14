package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private final HashMap<Integer, Task> task = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskTask = new HashMap<>();
    private final HashMap<Integer, Epic> epicTask = new HashMap<>();
    private final HistoryManager history;
    private TreeSet<Task> list = new TreeSet<>((task1, task2) -> task1.getStartTime().compareTo(task2.getStartTime()));

    public InMemoryTaskManager(HistoryManager manager) {
        this.history = manager;
    }

    public void addTaskInList(Task task) {
        if (checkPriority(task)) {
            list.add(task);
        } else {
            System.out.println("Время задачи пересекается с уже имеющимися");
        }
    }

    public boolean checkPriority(Task task) {
        int count = 0;
        for (Task task1: list) {
            if (task1.getStartTime().isBefore(task.getStartTime()) &&
                    task1.getEndTime().isBefore(task.getEndTime()) ||
                    (task1.getStartTime().isAfter(task.getStartTime()) &&
                            task1.getEndTime().isAfter(task.getEndTime()))) {
            } else {
                count ++ ;
            }
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

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
    public Task getTaskById(int numberId) {
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
        addTaskInList(subtask);
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
        addTaskInList(epic);
    }

    @Override
    public void addTask(Task tasks) {
        task.put(tasks.getId(), tasks);
        addTaskInList(tasks);
    }

    @Override
    public void deleteAllEpic() {
        for (Epic epic: getEpicTasks().values()) {
            history.removeTaskFromHistory(epic);
        }
        for (Subtask subtask: getSubtaskTasks().values()) {
            history.removeTaskFromHistory(subtask);
        }
        epicTask.clear();
        subtaskTask.clear();
    }

    @Override
    public void deleteAllSubtask() {
        for (Epic epic : epicTask.values()) {
            epic.clearList();
        }
        for (Subtask subtask: getSubtaskTasks().values()) {
            history.removeTaskFromHistory(subtask);
        }
        subtaskTask.clear();
    }

    @Override
    public void deleteAllTask() {
        for (Task tasks: task.values()) {
            history.removeTaskFromHistory(tasks);
        }
        task.clear();
    }

    @Override
    public void deleteTaskId(int idTask) {
        history.removeTaskFromHistory(getTaskById(idTask));
        if (epicTask.containsKey(idTask)) {
            for (Subtask sub : epicTask.get(idTask).getListSubtask()) {
                if (sub.getEpicId() == idTask) {
                    history.removeTaskFromHistory(sub);
                    subtaskTask.remove(sub.getId());
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

    public TreeSet<Task> getPrioritizedTasks() {
        return list;
    }
}