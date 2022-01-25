import manager.Manager;
import task.Epic;
import task.Subtask;
import task.Task;
import java.util.Scanner;

public class UpdateTask {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Manager manager = new Manager();

    public static void updateTask() {
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
            default:
                break;
        }
        System.out.println("С каким идентификатором обновить задачу ?");
        int idTask = scanner.nextInt();
        System.out.println("Введите название, описание через enter");
        String title = scanner.next();
        String description = scanner.next();
        switch (typeTask) {
            case (1): {
                manager.getEpicTasks().remove(idTask);
                Epic epic = new Epic(title, description, idTask, "NEW");
                if (manager.updateTask(epic)) {
                    System.out.println("Нет задачи с таким id");
                }
                break;
            }
            case (2): {
                manager.getSubtaskTasks().remove(idTask);
                Subtask subtask = new Subtask(title, description, idTask, manager.getSubtaskTasks().get(idTask).getIdEpic(), "NEW");
                if (manager.updateTask(subtask)) {
                    System.out.println("Нет задачи с таким id");
                }
                break;
            }
            case (3): {
                manager.getTasks().remove(idTask);
                Task tasks = new Task(title, description, idTask, "NEW");
                if (manager.updateTask(tasks)) {
                    System.out.println("Нет задачи с таким id");
                }
                break;
            }
            default: {
                System.out.println("Такого типа задачи нет");
                break;
            }
        }
    }

}
