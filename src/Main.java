import Manager.Manager;
import Task.Epic;
import Task.Subtask;
import Task.Task;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Manager manager = new Manager();

        while (true) {
            int number = printMenu();
            if (number == 1) {
                manager.printTask();
            } else if (number == 2) {
                manager.printEpic();
            } else if (number == 3) {
                System.out.println("Какого эпика вывести все подзадачи ? Введите id");
                manager.printEpic();
                int id = scanner.nextInt();
                switch (manager.gettingListAllSubtask(id)) {
                    case 0: {
                        System.out.println("");
                        break;
                    }
                    case 1: {
                        System.out.println("В данном эпике нет сабтасков");
                        break;
                    }
                    default: {
                        System.out.println("Такого эпика нет");
                        break;
                    }
                }
            } else if (number == 4) {
                System.out.println("Какой номер задачи вам нужен ?");
                int numberId = scanner.nextInt();
                manager.gettingTaskById(numberId);
            } else if (number == 5) {
                System.out.println("Что вы хотите добавить ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int typeTask = scanner.nextInt();
                System.out.println("Введите название, описание через enter");
                String title = scanner.next();
                String description = scanner.next();
                if (typeTask == 2) {
                    System.out.println("К какому эпику добавить сабтаск ?");
                    manager.printEpic();
                    int epicId = scanner.nextInt();
                    manager.addendumTask(typeTask, title, description, epicId);
                } else {
                    manager.addendumTask(typeTask, title, description, 0);
                }
            } else if (number == 6) {
                System.out.println("Обновить какую задачу вы хотите ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int typeTask = scanner.nextInt();
                switch (typeTask) {
                    case 1: {
                        manager.printEpic();
                        break;
                    }
                    case 2: {
                        manager.printSubtask();
                        break;
                    }
                    case 3: {
                        manager.printTask();
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
                        Epic epic = new Epic(title, description, idTask);
                        manager.updateTask(idTask, epic, 1);
                        break;
                    }
                    case (2) : {
                        manager.subtaskTask.remove(idTask);
                        Subtask subtask = new Subtask(title, description, idTask);
                        manager.updateTask(idTask, subtask, 2);
                        break;
                    }
                    case (3) : {
                        manager.task.remove(idTask);
                        Task tasks = new Task(title, description, idTask);
                        manager.updateTask(idTask, tasks, 3);
                        break;
                    }
                    default : {
                        System.out.println("Такого типа задачи нет");
                        break;
                    }
                }
            } else if (number == 7) {
                System.out.println("Удалить полностью - 1\n Удалить по идентификатору - 2");
                int numbers = scanner.nextInt();/*поменять название переменную*/
                if (numbers == 1) {
                    System.out.println("Какой тип удалить ?\n 1 - epic\n 2 - subtask\n 3 - task");
                    int type = scanner.nextInt();
                    manager.deleteTask(numbers, type, 0);
                } else if (numbers == 2) {
                    System.out.println("С каким идентификатором удалить задачу ");
                    int typeId = scanner.nextInt();
                    manager.deleteTask(numbers, 0, typeId);
                } else {
                    System.out.println("Такой команды нет");
                }

            } else if (number == 8) {
                System.out.println("Статус чего вы хотите обновить ?\n 1 - subtask\n 2 - task");
                int typeTask = scanner.nextInt();
                if (typeTask == 1) {
                    System.out.println("С каким идентификатором менять статус сабтаска ?");
                    manager.printSubtask();
                    int idSubtask = scanner.nextInt();
                    System.out.println("На какой статус(NEW, IN_PROGRESS, DONE) менять ?");
                    String status = scanner.next();
                    manager.updateStatusTask(typeTask, idSubtask, 0, status);
                } else if (typeTask == 2) {
                    System.out.println("С каким идентификатором менять статус таск ?");
                    manager.printTask();
                    int idTask = scanner.nextInt();
                    System.out.println("На какой статус(NEW, IN_PROGRESS, DONE) менять ?");
                    String status = scanner.next();
                    manager.updateStatusTask(typeTask, 0, idTask, status);
                } else {
                    System.out.println("Такой команды нет");
                }

            } else if (number == 9) {
                break;
            } else System.out.println("Такой команды нет");
        }
    }

    public static int printMenu() {
        System.out.println("Что вы хотите сделать ?\n" +
                "1- получения списка всех задач\n" +
                "2- получение списка всех эпиков\n" +
                "3- получение списка всех подзадач определенного эпика\n" +
                "4- получение задачи любого типа по идентификатору\n" +
                "5- добавление новой задачи, эпика, подзадачи\n" +
                "6- обновление залачи любого типа по идентификатору\n" +
                "7- удаление задач\n" +
                "8- обновление статусом\n" +
                "9- выход");
        return scanner.nextInt();
    }
}
