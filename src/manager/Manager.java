package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.HashMap;

public class Manager {
    static int id = 0;
    public HashMap<Integer, Task> task = new HashMap<>();
    public HashMap<Integer, Subtask> subtaskTask = new HashMap<>();
    public HashMap<Integer, Epic> epicTask = new HashMap<>();

    public HashMap<Integer, Task> getTask() {
        return task;
    }

    public HashMap<Integer, Subtask> getSubtaskTask() {
        return subtaskTask;
    }

    public HashMap<Integer, Epic> getEpicTask() {
        return epicTask;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Manager.id = id + 1;
    }

    public void gettingTaskById(int numberId){
        if (task.containsKey(numberId)) {
            System.out.println(task.get(numberId).getTitle());
        } else if (subtaskTask.containsKey(numberId)) {
            System.out.println(subtaskTask.get(numberId).getTitle());
        } else if (epicTask.containsKey(numberId)) {
            System.out.println(epicTask.get(numberId).getTitle());
        } else {
            System.out.println("Задачи с таким идентификатором нет");
        }
    }

    public void addendumTask(Object newTask) {
         if (newTask instanceof Epic) {
            epicTask.put(id, (Epic) newTask);
            id ++;
        } else if (newTask instanceof Subtask) {
            subtaskTask.put(id, (Subtask) newTask);
        } else if (newTask instanceof Task) {
             task.put(id, (Task) newTask);
             id ++;
         } else {
            System.out.println("Ошибка ввода");
        }
    }

    public void updateTask(int idTask, Object newTask) {
            if (newTask instanceof Epic) {
                epicTask.put(idTask, (Epic) newTask);
            } else if (newTask instanceof Subtask) {
                subtaskTask.put(idTask, (Subtask) newTask);
            } else if (newTask instanceof Task){
                task.put(idTask, (Task) newTask);
            } else {
                System.out.println("Такого типа задач нет");
            }
    }

    public void deleteTask(int type) {
        switch (type) {
            case (1) : {
                epicTask.clear();
                break;
            }
            case (2) : {
                subtaskTask.clear();
                break;
            }
            case (3) : {
                task.clear();
                break;
            }
            default : {
                System.out.println("Такого типа задачи нет");
                break;
            }
        }
    }

    public void deleteTaskId(int typeId) {
        if (epicTask.containsKey(typeId)) {
            epicTask.remove(typeId);
        } else if (task.containsKey(typeId)) {
            task.remove(typeId);
        } else if (subtaskTask.containsKey(typeId)) {
            subtaskTask.remove(typeId);
        } else {
            System.out.println("Задачт с таким идентификатором нет");
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
                } else if (error < epic.getIdSubtask().size()) {
                    epic.setStatus("IN_PROGRESS");
                    error = 0;
                } else {
                    error = 0;
                }
            }
        }
    }
}