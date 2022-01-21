import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    static int id = 0;
    HashMap<Integer, Task> task = new HashMap<>();
    HashMap<Integer, Subtask> subtaskTask = new HashMap<>();
    HashMap<Integer, Epic> epicTask = new HashMap<>();

    public void printTask() {
        for (Integer idTask : task.keySet()) {
            System.out.println("ID : " + task.get(idTask).getId()
                    + " title : " + task.get(idTask).getTitle()
                    + " description : " + task.get(idTask).getDescription()
                    + " status : " + task.get(idTask).getStatus());
        }
    }

    public void printEpic() {
        for (Integer idEpic : epicTask.keySet()) {
            System.out.println("ID : " + epicTask.get(idEpic).getId()
                    + " title : " + epicTask.get(idEpic).getTitle()
                    + " description : " + epicTask.get(idEpic).getDescription()
                    + " status : " + epicTask.get(idEpic).getStatus());
        }
    }

    public void printSubtask() {
        for (Integer idSubtask : subtaskTask.keySet()) {
            System.out.println("ID : " + subtaskTask.get(idSubtask).getId()
                    + " title : " + subtaskTask.get(idSubtask).getTitle()
                    + " description : " + subtaskTask.get(idSubtask).getDescription()
                    + " status : " + subtaskTask.get(idSubtask).getStatus());
        }
    }

    public int gettingListAllSubtask(int id) {
        if (epicTask.containsKey(id)) {
            if (epicTask.get(id).idSubtask != null) {
                printsubtaskTaskOnId(epicTask.get(id).idSubtask);
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }

    public void printsubtaskTaskOnId(ArrayList<Integer> id) {
        for (Integer ids : id) {
            System.out.println("ID : " + subtaskTask.get(ids).getId()
                    + " title : " + subtaskTask.get(ids).getTitle()
                    + " description : " + subtaskTask.get(ids).getDescription()
                    + " status : " + subtaskTask.get(ids).getStatus());
        }
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

    public void addendumTask(int typeTask, String title, String description, int epicId) {
        switch (typeTask) {
            case (1) : {
                Epic epic = new Epic(title, description, id);
                epicTask.put(id, epic);
                id++;
                break;
            }
            case (2) : {
                if (epicTask.containsKey(epicId) && epicTask.get(epicId) != null) {
                    Subtask subtask = new Subtask(title, description, id);
                    subtaskTask.put(id, subtask);
                    epicTask.get(epicId).idSubtask.add(id);
                    id++;
                }
                break;
            }
            case (3) : {
                task.put(id, new Task(title, description, id));
                id++;
                break;
            }
            default : System.out.println("Такого типа задачи нет");
                break;
        }
    }

    public void updateTask(int idTask, Object newTask, int titleTask) {
        switch (titleTask) {
            case (1): {
                epicTask.put(idTask, (Epic) newTask);
                break;
            }
            case (2): {
                subtaskTask.put(idTask, (Subtask) newTask);
                break;
            }
            case (3): {
                task.put(idTask, (Task) newTask);
                break;
            }
        }
    }

    public void deleteTask(int number, int type, int typeId) {
        switch (number) {
            case (1) : {
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
                break;
            }
            case (2) : {
                if (epicTask.containsKey(typeId)) {
                    epicTask.remove(typeId);
                } else if (task.containsKey(typeId)) {
                    task.remove(typeId);
                } else if (subtaskTask.containsKey(typeId)) {
                    subtaskTask.remove(typeId);
                } else {
                    System.out.println("Задачт с таким идентификатором нет");
                }
                break;
            }
            default : {
                break;
            }
        }
    }

    public void updateStatusTask(int typeTask, int idSubtask, int idTask, String status) {
        switch (typeTask) {
            case (1) : {
                if (subtaskTask.containsKey(idSubtask)) {
                    subtaskTask.get(idSubtask).setStatus(status);
                    chekStatus();
                }
                break;
            }
            case (2) : {
                if (task.containsKey(idTask)) {
                    task.get(idTask).setStatus(status);
                } else
                break;
            }
            default : {
                System.out.println("Такого типа задачи нет");
                break;
            }
        }
    }

    public void chekStatus() {
        String statusChek;
        int error = 0;
        for (Epic epic: epicTask.values()) {
            statusChek = subtaskTask.get(epic.idSubtask.get(0)).getStatus();
            for (Integer subtask: epic.idSubtask) {
                if (!subtaskTask.get(subtask).getStatus().equals(statusChek)) {
                    error ++;
                }
            }
            if (error == 0) {
                epic.setStatus(statusChek);
            } else {
                error = 0;
            }
        }
    }
}