import manager.Manager;
import task.Epic;
import task.Subtask;
import task.Task;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Manager manager = new Manager();

    public static void main(String[] args) {
        while (true) {
            int number = printMenu();
            if (number == 1) {
                System.out.println(manager.getTasks());
            } else if (number == 2) {
                System.out.println("Какого эпика вывести все подзадачи ? Введите id");
                System.out.println(manager.getEpicTasks());
                int id = scanner.nextInt();
                for (Integer idSubtask: manager.getEpicTasks().get(id).getIdSubtask()) {
                    System.out.println(manager.getSubtaskTasks().get(idSubtask));
                }
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
                    System.out.println(manager.getEpicTasks());
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
                System.out.println("С каким идентификатором обновить задачу ?");
                int idTask = scanner.nextInt();
                System.out.println("Введите название, описание через enter");
                String title = scanner.next();
                String description = scanner.next();
                if (manager.getEpicTasks().containsKey(idTask)) {
                    manager.removeTask(idTask);
                    Epic epic = new Epic(title, description, idTask, "NEW");
                    manager.addEpic(epic);
                } else if (manager.getSubtaskTasks().containsKey(idTask)) {
                    int getEpic = manager.getSubtaskTasks().get(idTask).getIdEpic();
                    manager.removeTask(idTask);
                    Subtask subtask = new Subtask(title, description, idTask, getEpic, "NEW");
                    manager.addSubtask(subtask);
                } else if (manager.getTasks().containsKey(idTask)) {
                    manager.removeTask(idTask);
                    Task tasks = new Task(title, description, idTask, "NEW");
                    manager.addTask(tasks);
                } else {
                    System.out.println("Такого типа задачи нет");
                }
            } else if (number == 6) {
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
                    System.out.println(manager.getSubtaskTasks());
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
                    System.out.println(manager.getTasks());
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
                System.out.println(manager.getEpicTasks());
            } else if (number == 10) {
                break;
            }else System.out.println("Такой команды нет");
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
