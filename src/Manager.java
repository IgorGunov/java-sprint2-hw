import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    static Scanner scanner = new Scanner(System.in);
    static int id = 0;
    static HashMap<Integer, Task> task = new HashMap<>();
    static HashMap<Integer, Subtask> subtaskTask = new HashMap<>();
    static HashMap<Integer, Epic> epicTask = new HashMap<>();

    public static void printTask() {
        for (Integer idTask : task.keySet()) {
            System.out.println("ID : " + task.get(idTask).id + " title : " + task.get(idTask).title
                    + " description : " + task.get(idTask).description + " status : " + task.get(idTask).status);
        }
    }

    public static void printEpic() {
        for (Integer idEpic : epicTask.keySet()) {
            System.out.println("ID : " + epicTask.get(idEpic).id + " title : " + epicTask.get(idEpic).title
                    + " description : " + epicTask.get(idEpic).description + " status : " + epicTask.get(idEpic).status);
        }
    }

    public static void printSubtask() {
        for (Integer idSubtask : subtaskTask.keySet()) {
            System.out.println("ID : " + subtaskTask.get(idSubtask).id + " title : " + subtaskTask.get(idSubtask).title
                    + " description : " + subtaskTask.get(idSubtask).description + " status : " + subtaskTask.get(idSubtask).status);
        }
    }

    public static void gettingListAllSubtask() {
        System.out.println("Какого эпика вывести все подзадачи ? Введите id");
        printEpic();
        int id = scanner.nextInt();
        if (epicTask.containsKey(id)) {
            if (epicTask.get(id).idSubtask != null) {
                printsubtaskTaskOnId(epicTask.get(id).idSubtask);
            } else {
                System.out.println("В данном эпике нет сабтасков");
            }
        } else {
            System.out.println("Такого эпика нет");
        }
    }

    public static void printsubtaskTaskOnId(ArrayList<Integer> id) {
        for (Integer ids : id) {
            System.out.println("ID : " + subtaskTask.get(ids).id + " title : " + subtaskTask.get(ids).title
                    + " description : " + subtaskTask.get(ids).description + " status : " + subtaskTask.get(ids).status);
        }
    }

    public static void gettingTaskById(){
        System.out.println("Какой номер задачи вам нужен ?");
        int numberId = scanner.nextInt();
        if (task.containsKey(numberId)) {
            System.out.println(task.get(numberId).title);
        } else if (subtaskTask.containsKey(numberId)) {
            System.out.println(subtaskTask.get(numberId).title);
        } else if (epicTask.containsKey(numberId)) {
            System.out.println(epicTask.get(numberId).title);
        } else {
            System.out.println("Задачи с таким идентификатором нет");
        }
    }

    public static void addendumTask() {
        System.out.println("Что вы хотите добавить ?\n 1 - epic\n 2 - subtask\n 3 - task");
        int typeTask = scanner.nextInt();
        System.out.println("Введите название, описание через enter");
        String title = scanner.next();
        String description = scanner.next();

        switch (typeTask) {
            case (1) : {
                Epic epic = new Epic(title, description, id);
                epicTask.put(id, epic);
                id++;
                break;
            }
            case (2) : {
                Subtask subtask = new Subtask(title, description, id);
                subtaskTask.put(id, subtask);
                System.out.println("К какому эпику добавить сабтаск ?");
                printEpic();
                int epicId = scanner.nextInt();
                if (epicTask.containsKey(epicId) && epicTask.get(epicId) != null) {
                    epicTask.get(epicId).idSubtask.add(id);
                    id++;
                } else {
                    System.out.println("Такого эпика нет");
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

    public static void updateTask(int idTask, Object task, int titleTask) {
        switch (titleTask) {
            case (1): {
                Manager.epicTask.put(idTask, (Epic) task);
                break;
            }
            case (2): {
                Manager.subtaskTask.put(idTask, (Subtask) task);
                break;
            }
            case (3): {
                Manager.task.put(idTask, (Task) task);
                break;
            }
        }
    }

    public static void deleteTask() {
        System.out.println("Удалить полностью - 1\n Удалить по идентификатору - 2");
        int number = scanner.nextInt();/*поменять название переменную*/

        switch (number) {
            case (1) : {
                System.out.println("Какой тип удалить ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int type = scanner.nextInt();
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
                System.out.println("С каким идентификатором удалить задачу ");
                int typeId = scanner.nextInt();
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
                System.out.println("Такой команды нет");
                break;
            }
        }
    }

    public static void updateStatusTask() {
        System.out.println("Статус чего вы хотите обновить ?\n 1 - subtask\n 2 - task");
        int typeTask = scanner.nextInt();
        switch (typeTask) {
            case (1) : {
                System.out.println("С каким идентификатором менять статус сабтаска ?");
                printSubtask();
                int idSubtask = scanner.nextInt();
                if (subtaskTask.containsKey(idSubtask)) {
                    System.out.println("На какой статус(NEW, IN_PROGRESS, DONE) менять ?");
                    subtaskTask.get(idSubtask).status = scanner.next();
                    chekStatus();
                } else {
                    System.out.println("Задачи с таким идентификатором нет");
                }
                break;
            }
            case (2) : {
                System.out.println("С каким идентификатором менять статус таск ?");
                printTask();
                int idTask = scanner.nextInt();
                if (task.containsKey(idTask)) {
                    System.out.println("На какой статус(NEW, IN_PROGRESS, DONE) менять ?");
                    task.get(idTask).status = scanner.nextLine();
                } else {
                    System.out.println("Задачи с таким идентификатором нет");
                }
                break;
            }
            default : {
                System.out.println("Такого типа задачи нет");
                break;
            }
        }
    }

    public static void chekStatus() {
        String statusChek;
        int error = 0;
        for (Epic epic: epicTask.values()) {
            statusChek = subtaskTask.get(epic.idSubtask.get(0)).status;
            for (Integer subtask: epic.idSubtask) {
                if (!subtaskTask.get(subtask).status.equals(statusChek)) {
                    error ++;
                }
            }
            if (error == 0) {
                epic.status = statusChek;
            } else {
                error = 0;
            }
        }
    }
}