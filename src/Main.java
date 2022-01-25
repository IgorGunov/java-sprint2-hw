import manager.Manager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Manager manager = new Manager();

    public static void main(String[] args) {
        while (true) {
            int number = Main.printMenu();
            if (number == 1) {
                Main.printTask();
            } else if (number == 2) {
                System.out.println("Какого эпика вывести все подзадачи ? Введите id");
                Main.printEpic();
                int id = scanner.nextInt();
                Main.printSubtaskTaskOnId(manager.getEpicTasks().get(id).getIdSubtask());
            } else if (number == 3) {
                System.out.println("Какой номер задачи вам нужен ?");
                System.out.println(manager.getTaskById(scanner.nextInt()));
            } else if (number == 4) {
                System.out.println("Что вы хотите добавить ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int typeTask = scanner.nextInt();
                System.out.println("Введите название, описание через enter");
                String title = scanner.next();
                String description = scanner.next();
                if (typeTask == 1) {
                    Epic epic = new Epic(title, description, manager.getId(), "NEW");
                    manager.addEpic(epic);
                } else if (typeTask == 2) {
                    System.out.println("К какому эпику добавить сабтаск ?");
                    Main.printEpic();
                    int epicId = scanner.nextInt();
                    Subtask subtask = new Subtask(title, description, manager.getId(), epicId, "NEW");
                    manager.addSubtask(subtask);
                } else if (typeTask == 3) {
                    Task task = new Task(title, description, manager.getId(), "NEW");
                    manager.addTask(task);
                } else {
                    System.out.println("Такой команды нет");
                }
            } else if (number == 5) {
                UpdateTask.updateTask();
            } else if (number == 6) {
                System.out.println("Какого типа удалить все задачи ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int type = scanner.nextInt();
                switch (type) {
                    case (1) : {
                        manager.deleteAllEpic();
                        break;
                    }
                    case (2) : {
                        manager.deleteAllSubtask();
                        break;
                    }
                    case (3) : {
                        manager.deleteAllTask();
                        break;
                    }
                    default : {
                        break;
                    }
                }
            } else if (number == 7) {
                System.out.println("С каким идентификатором удалить задачу ");
                int id = scanner.nextInt();
                manager.deleteTaskId(id);
            } else if (number == 8) {
                System.out.println("Статус чего вы хотите обновить ?\n 1 - subtask\n 2 - task");
                int typeTask = scanner.nextInt();
                System.out.println("На какой статус(NEW, IN_PROGRESS, DONE) менять ?");
                String status = scanner.next();
                if (typeTask == 1) {
                    System.out.println("С каким идентификатором менять статус сабтаска ?");
                    Main.printSubtask();
                    int idSubtask = scanner.nextInt();
                    String title = manager.getSubtaskTasks().get(idSubtask).getTitle();
                    String description = manager.getSubtaskTasks().get(idSubtask).getDescription();
                    int idEpic = manager.getSubtaskTasks().get(idSubtask).getIdEpic();
                    manager.getSubtaskTasks().remove(idSubtask);
                    manager.getEpicTasks().get(idEpic).removeIdArraylist(idSubtask);
                    Subtask subtask = new Subtask(title, description, idSubtask, idEpic, status);
                    manager.addSubtask(subtask);
                } else if (typeTask == 2) {
                    System.out.println("С каким идентификатором менять статус таск ?");
                    Main.printTask();
                    int idTask = scanner.nextInt();
                    String title = manager.getTasks().get(idTask).getTitle();
                    String description = manager.getTasks().get(idTask).getDescription();
                    int idTasks = manager.getTasks().get(idTask).getId();
                    manager.getTasks().remove(idTask);
                    Task task = new Task(title, description, idTasks , status);
                    manager.addTask(task);
                } else {
                    System.out.println("Такой команды нет");
                }
            } else if (number == 9) {
                manager.chekStatus();
                Main.printEpic();
            } else if (number == 10) {
                break;
            }else System.out.println("Такой команды нет");
        }
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
        for (Integer idEpic : manager.getEpicTasks().keySet()) {
            System.out.println("ID : " + manager.getEpicTasks().get(idEpic).getId()
                    + " title : " + manager.getEpicTasks().get(idEpic).getTitle()
                    + " description : " + manager.getEpicTasks().get(idEpic).getDescription()
                    + " status : " + manager.getEpicTasks().get(idEpic).getStatus());
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

    public static void printSubtaskTaskOnId(ArrayList<Integer> id) {
        for (Integer ids : id) {
                System.out.println("ID : " + manager.getSubtaskTasks().get(ids).getId()
                        + " title : " + manager.getSubtaskTasks().get(ids).getTitle()
                        + " description : " + manager.getSubtaskTasks().get(ids).getDescription()
                        + " status : " + manager.getSubtaskTasks().get(ids).getStatus());
        }
    }

    public static int printMenu() {
        System.out.println("Что вы хотите сделать ?\n" +
                "1- получения списка всех задач\n" +
                "2- получение списка всех подзадач определенного эпика\n" +
                "3- получение задачи любого типа по идентификатору\n" +
                "4- добавление новой задачи, эпика, подзадачи\n" +
                "5- обновление залачи любого типа по идентификатору\n" +
                "6- удаление задач\n" +
                "7- удаление задач по id\n" +
                "8- обновление статусом\n" +
                "9- Вывод всех эпиков\n" +
                "10- выход");
        return scanner.nextInt();
    }
}
