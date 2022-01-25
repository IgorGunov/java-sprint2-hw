/*import manager.Manager;
import task.Epic;
import task.Subtask;
import task.Task;
import java.util.Scanner;

public class CommandLineManager {
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
                System.out.println("Обновить какую задачу вы хотите ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int typeTask = scanner.nextInt();
                switch (typeTask) {
                    case 1: {
                        Main.printEpic();
                        break;
                    }
                    case 2: {
                        Main.printSubtask();
                        break;
                    }
                    case 3: {
                        Main.printTask();
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
                        manager.getEpicTasks().remove(idTask);
                        Epic epic = new Epic(title, description, idTask, "NEW");
                        if (manager.updateTask(epic)) {
                            System.out.println("Нет задачи с таким id");
                        }
                        break;
                    }
                    case (2) : {
                        manager.getSubtaskTasks().remove(idTask);
                        Subtask subtask = new Subtask(title, description, idTask, manager.getSubtaskTasks().get(idTask).getIdEpic(), "NEW");
                        if (manager.updateTask(subtask)) {
                            System.out.println("Нет задачи с таким id");
                        }
                        break;
                    }
                    case (3) : {
                        manager.getTasks().remove(idTask);
                        Task tasks = new Task(title, description, idTask, "NEW");
                        if (manager.updateTask(tasks)) {
                            System.out.println("Нет задачи с таким id");
                        }
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
}*/
