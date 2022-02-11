import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager manager = Managers.getDefault();

    public static void main(String[] args) {
        while (true) {
            int number = printMenu();
            if (number == 1) {
                printTask();
            } else if (number == 2) {
                printListSubtask();
            } else if (number == 3) {
                printTaskOnId();
            } else if (number == 4) {
                addTask();
            } else if (number == 5) {
                updateTask();
            } else if (number == 6) {
                deleteTasks();
            } else if (number == 7) {
                deleteTaskOnId();
            } else if (number == 8) {
                updateStatus();
            } else if (number == 9) {
                printEpic();
            } else if (number == 10) {
                for (Task task: manager.getHistory()) {
                    System.out.println(task.getId());
                }
            } else if (number == 11) {
                break;
            }else System.out.println("Такой команды нет");
        }
    }

    public static void printListSubtask() {
        System.out.println("Какого эпика вывести все подзадачи ? Введите id");
        printEpic();
        int id = scanner.nextInt();
        printSubtaskTaskOnId(manager.getEpicTasks().get(id).getListSubtask());
    }

    public static void printTaskOnId() {
        System.out.println("Какой номер задачи вам нужен ?");
        System.out.println(manager.getTaskById(scanner.nextInt()));
    }

    public static void addTask() {
        System.out.println("Что вы хотите добавить ?\n 1 - epic\n 2 - subtask\n 3 - task");
        int typeTask = scanner.nextInt();
        System.out.println("Введите название, описание через enter");
        String title = scanner.next();
        String description = scanner.next();
        if (typeTask == 1) {
            Epic epic = new Epic(title, description, manager.getId(), Status.NEW);
            manager.addEpic(epic);
        } else if (typeTask == 2) {
            System.out.println("К какому эпику добавить сабтаск ?");
            printEpic();
            int epicId = scanner.nextInt();
            Subtask subtask = new Subtask(title, description, manager.getId(), epicId, Status.NEW);
            manager.addSubtask(subtask);
        } else if (typeTask == 3) {
            Task task = new Task(title, description, manager.getId(), Status.NEW);
            manager.addTask(task);
        } else {
            System.out.println("Такой команды нет");
        }
    }

    public static void updateTask() {
        System.out.println("С каким идентификатором обновить задачу ?");
        int idTask = scanner.nextInt();
        System.out.println("Введите название, описание через enter");
        String title = scanner.next();
        String description = scanner.next();
        if (manager.getEpicTasks().containsKey(idTask)) {
            if (!manager.getEpicTasks().get(idTask).getListSubtask().isEmpty()) {
                List<Subtask> sub = manager.getEpicTasks().get(idTask).getListSubtask();
                manager.getEpicTasks().remove(idTask);
                Epic epic = new Epic(title, description, idTask, Status.NEW);
                for (Subtask subs: sub) {
                    epic.addSubtaskInList(subs);
                }
                manager.updateTask(epic);
            } else {
                manager.getEpicTasks().remove(idTask);
                Epic epic = new Epic(title, description, idTask, Status.NEW);
                manager.updateTask(epic);
            }
        } else if (manager.getSubtaskTasks().containsKey(idTask)) {
            int getEpic = manager.getSubtaskTasks().get(idTask).getEpicId();
            for (Subtask sub: manager.getEpicTasks().get(getEpic).getListSubtask()) {
                if (sub.getId() == idTask) {
                    manager.getEpicTasks().get(getEpic).removeSubtaskInList(sub);
                    break;
                }
            }
            manager.getSubtaskTasks().remove(idTask);
            Subtask subtask = new Subtask(title, description, idTask, getEpic, Status.NEW);
            manager.updateTask(subtask);
            manager.getEpicTasks().get(getEpic).addSubtaskInList(subtask);
        } else if (manager.getTasks().containsKey(idTask)) {
            printTask();
            manager.deleteTaskId(idTask);
            Task tasks = new Task(title, description, idTask, Status.NEW);
            manager.updateTask(tasks);
        } else {
            System.out.println("Такого типа задачи нет");
        }
    }

    public static void deleteTasks() {
        System.out.println("Какого типа удалить все задачи ?\n 1 - epic\n 2 - subtask\n 3 - task");
        int type = scanner.nextInt();
        if (type == 1) {
            manager.deleteAllEpic();
        } else if (type == 2) {
            manager.deleteAllSubtask();
        } else if (type == 3) {
            manager.deleteAllTask();
        } else {
            System.out.println("Такого типа задачи нет");
        }
    }

    public static void deleteTaskOnId() {
        System.out.println("С каким идентификатором удалить задачу ");
        int id = scanner.nextInt();
        manager.deleteTaskId(id);
    }

    public static void updateStatus() {
        System.out.println("Статус чего вы хотите обновить ?\n 1 - subtask\n 2 - task");
        int typeTask = scanner.nextInt();
        System.out.println("На какой статус(NEW - 1, IN_PROGRESS - 2, DONE - 3) менять ?");
        int statusNumber = scanner.nextInt();
        Status status = Status.NEW;
        if (statusNumber == 2) {
            status = Status.DONE;
        } else if (statusNumber == 3) {
            status = Status.IN_PROGRESS;
        }
        if (typeTask == 1) {
            System.out.println("С каким идентификатором менять статус сабтаска ?");
            printSubtask();
            int idSubtask = scanner.nextInt();
            String title = manager.getSubtaskTasks().get(idSubtask).getTitle();
            String description = manager.getSubtaskTasks().get(idSubtask).getDescription();
            int idEpic = manager.getSubtaskTasks().get(idSubtask).getEpicId();
            manager.deleteTaskId(idSubtask);
            Subtask subtask = new Subtask(title, description, idSubtask, idEpic, status);
            manager.addSubtask(subtask);
        } else if (typeTask == 2) {
            System.out.println("С каким идентификатором менять статус таск ?");
            printTask();
            int idTask = scanner.nextInt();
            String title = manager.getTasks().get(idTask).getTitle();
            String description = manager.getTasks().get(idTask).getDescription();
            int idTasks = manager.getTasks().get(idTask).getId();
            manager.deleteTaskId(idTask);
            Task task = new Task(title, description, idTasks , status);
            manager.addTask(task);
        } else {
            System.out.println("Такой команды нет");
        }
    }

    public static int printMenu() {
        System.out.println("Что вы хотите сделать ?\n" +
                "1- получения списка всех задач\n" +
                "2- получение списка всех подзадач определенного эпика\n" +
                "3- получение задачи любого типа по идентификатору\n" +
                "4- добавление новой задачи, эпика, подзадачи\n" +
                "5- обновление задачи любого типа по идентификатору\n" +
                "6- удаление задач\n" +
                "7- удаление задач по id\n" +
                "8- обновление статусом\n" +
                "9- Вывод всех эпиков\n" +
                "10- Печать истории\n" +
                "11- выход");
        return scanner.nextInt();
    }

    public static void printTask() {
        for (Integer idTask : manager.getTasks().keySet()) {
            System.out.println("ID : " + manager.getTasks().get(idTask).getId()
                    + " title : " + manager.getTasks().get(idTask).getTitle()
                    + " description : " + manager.getTasks().get(idTask).getDescription()
                    + " status : " + manager.getTasks().get(idTask).getStatus());
        }
    }

    public static void printEpic() {
        for (Epic epic : manager.getEpicTasks().values()) {
            System.out.println("ID : " + epic.getId()
                    + " title : " + epic.getTitle()
                    + " description : " + epic.getDescription()
                    + " status : " + epic.getStatus());
        }
    }

    public static void printSubtask() {
        for (Integer idSubtask : manager.getSubtaskTasks().keySet()) {
            System.out.println("ID : " + manager.getSubtaskTasks().get(idSubtask).getId()
                    + " title : " + manager.getSubtaskTasks().get(idSubtask).getTitle()
                    + " description : " + manager.getSubtaskTasks().get(idSubtask).getDescription()
                    + " status : " + manager.getSubtaskTasks().get(idSubtask).getStatus());
        }
    }

    public static void printSubtaskTaskOnId(List<Subtask> subtask) {
        for (Subtask sub: subtask) {
            System.out.println("ID : " + sub.getId()
                    + " title : " + sub.getTitle()
                    + " description : " + sub.getDescription()
                    + " status : " + sub.getStatus());
        }
    }
}
