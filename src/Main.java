import manager.Manager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Manager manager = new Manager();

    public static void main(String[] args) {


        while (true) {
            int number = printMenu();
            if (number == 1) {
                printTask();
            } else if (number == 2) {
                System.out.println("Какого эпика вывести все подзадачи ? Введите id");
                printEpic();
                int id = scanner.nextInt();
                printsubtaskTaskOnId(manager.getEpicTask().get(id).getIdSubtask());
            } else if (number == 3) {
                System.out.println("Какой номер задачи вам нужен ?");
                int numberId = scanner.nextInt();
                manager.gettingTaskById(numberId);
            } else if (number == 4) {
                manager.addendumEpic(new Epic("эпикэпику","reger",10,"new"));

                System.out.println("Что вы хотите добавить ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int typeTask = scanner.nextInt();
                System.out.println("Введите название, описание через enter");
                String title = scanner.next();
                String description = scanner.next();
                if (typeTask == 1) {
                    Epic epic = new Epic(title, description, Manager.getId(), "NEW");
                    manager.addendumTask(epic);
                } else if (typeTask == 2) {
                    System.out.println("К какому эпику добавить сабтаск ?");
                    printEpic();
                    int epicId = scanner.nextInt();
                    Subtask subtask = new Subtask(title, description, Manager.getId(), epicId, "NEW");
                    manager.addendumTask(subtask);
                    manager.epicTask.get(epicId).setIdSubtask(Manager.getId());
                    manager.subtaskTask.get(Manager.getId()).setIdEpic(epicId);
                    Manager.setId(Manager.getId());
                } else if (typeTask == 3) {
                    Task task = new Task(title, description, Manager.getId(), "NEW");
                    manager.addendumTask(task);
                } else {
                    System.out.println("Такой команды нет");
                }

            } else if (number == 5) {
                System.out.println("Обновить какую задачу вы хотите ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int typeTask = scanner.nextInt();
                switch (typeTask) {
                    case 1: {
                        printEpic();
                        break;
                    }
                    case 2: {
                        printSubtask();
                        break;
                    }
                    case 3: {
                        printTask();
                        break;
                    }
                    default: break;
                }
                System.out.println("С каким идентификатором обновить задачу ?");
                int idTask = scanner.nextInt();
                System.out.println("Введите название, описание через enter");
                String title = scanner.next();
                String description = scanner.next();
                switch (typeTask) {
                    case (1) : {
                        manager.epicTask.remove(idTask);
                        Epic epic = new Epic(title, description, idTask, "NEW");
                        manager.updateTask(idTask, epic);
                        break;
                    }
                    case (2) : {
                        manager.subtaskTask.remove(idTask);
                        Subtask subtask = new Subtask(title, description, idTask, manager.subtaskTask.get(idTask).getIdEpic(), "NEW");
                        manager.updateTask(idTask, subtask);
                        break;
                    }
                    case (3) : {
                        manager.task.remove(idTask);
                        Task tasks = new Task(title, description, idTask, "NEW");
                        manager.updateTask(idTask, tasks);
                        break;
                    }
                    default : {
                        System.out.println("Такого типа задачи нет");
                        break;
                    }
                }
            } else if (number == 6) {
                System.out.println("Какого типа удалить все задачи ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int type = scanner.nextInt();
                manager.deleteTask(type);
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
                    printSubtask();
                    int idSubtask = scanner.nextInt();
                    Subtask subtask = new Subtask(manager.subtaskTask.get(idSubtask).getTitle(), manager.subtaskTask.get(idSubtask).getDescription(), manager.subtaskTask.get(idSubtask).getId(), manager.subtaskTask.get(idSubtask).getIdEpic(), status);
                    manager.addendumTask(subtask);
                } else if (typeTask == 2) {
                    System.out.println("С каким идентификатором менять статус таск ?");
                    printTask();
                    int idTask = scanner.nextInt();
                    Task task = new Task(manager.task.get(idTask).getTitle(), manager.task.get(idTask).getDescription(), manager.task.get(idTask).getId(), status);
                    manager.updateStatusTask(typeTask, 0, idTask, status);
                } else {
                    System.out.println("Такой команды нет");
                }




               /* System.out.println("Статус чего вы хотите обновить ?\n 1 - subtask\n 2 - task");
                int typeTask = scanner.nextInt();
                if (typeTask == 1) {
                    System.out.println("С каким идентификатором менять статус сабтаска ?");
                    printSubtask();
                    int idSubtask = scanner.nextInt();
                    System.out.println("На какой статус(NEW, IN_PROGRESS, DONE) менять ?");
                    String status = scanner.next();
                    manager.updateStatusTask(typeTask, idSubtask, 0, status);
                } else if (typeTask == 2) {
                    System.out.println("С каким идентификатором менять статус таск ?");
                    printTask();
                    int idTask = scanner.nextInt();
                    System.out.println("На какой статус(NEW, IN_PROGRESS, DONE) менять ?");
                    String status = scanner.next();
                    manager.updateStatusTask(typeTask, 0, idTask, status);
                } else {
                    System.out.println("Такой команды нет");
                }*/


            } else if (number == 9) {
                manager.chekStatus();
                printEpic();
            } else if (number == 10) {
                break;
            }else System.out.println("Такой команды нет");
        }
    }

    public static void printTask() {
        for (Integer idTask : manager.task.keySet()) {
            System.out.println("ID : " + manager.getTask().get(idTask).getId()
                    + " title : " + manager.getTask().get(idTask).getTitle()
                    + " description : " + manager.getTask().get(idTask).getDescription()
                    + " status : " + manager.getTask().get(idTask).getStatus());
        }
    }

    public static void printEpic() {
        for (Integer idEpic : manager.getEpicTask().keySet()) {
            System.out.println("ID : " + manager.getEpicTask().get(idEpic).getId()
                    + " title : " + manager.getEpicTask().get(idEpic).getTitle()
                    + " description : " + manager.getEpicTask().get(idEpic).getDescription()
                    + " status : " + manager.getEpicTask().get(idEpic).getStatus());
        }
    }

    public static void printSubtask() {
        for (Integer idSubtask : manager.getSubtaskTask().keySet()) {
            System.out.println("ID : " + manager.getSubtaskTask().get(idSubtask).getId()
                    + " title : " + manager.getSubtaskTask().get(idSubtask).getTitle()
                    + " description : " + manager.getSubtaskTask().get(idSubtask).getDescription()
                    + " status : " + manager.getSubtaskTask().get(idSubtask).getStatus());
        }
    }

    public static void printsubtaskTaskOnId(ArrayList<Integer> id) {
        for (Integer ids : id) {
            System.out.println("ID : " + manager.getSubtaskTask().get(ids).getId()
                    + " title : " + manager.getSubtaskTask().get(ids).getTitle()
                    + " description : " + manager.getSubtaskTask().get(ids).getDescription()
                    + " status : " + manager.getSubtaskTask().get(ids).getStatus());
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
