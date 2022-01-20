import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            int number = printMenu();
            if (number == 1) {
                Manager.printTask();
            } else if (number == 2) {
                Manager.printEpic();
            } else if (number == 3) {
                Manager.gettingListAllSubtask();
            } else if (number == 4) {
                Manager.gettingTaskById();
            } else if (number == 5) {
                Manager.addendumTask();
            } else if (number == 6) {
                System.out.println("Обновить какую задачу вы хотите ?\n 1 - epic\n 2 - subtask\n 3 - task");
                int typeTask = scanner.nextInt();
                System.out.println("С каким идентификатором обновить задачу ?");
                int idTask = scanner.nextInt();
                System.out.println("Введите название, описание через enter");
                String title = scanner.next();
                String description = scanner.next();
                switch (typeTask) {
                    case (1) : {
                        Manager.epicTask.remove(idTask);
                        Epic epic = new Epic(title, description, idTask);
                        Manager.updateTask(idTask, epic, 1);
                        break;
                    }
                    case (2) : {
                        Manager.subtaskTask.remove(idTask);
                        Subtask subtask = new Subtask(title, description, idTask);
                        Manager.updateTask(idTask, subtask, 2);
                        break;
                    }
                    case (3) : {
                        Manager.task.remove(idTask);
                        Task tasks = new Task(title, description, idTask);
                        Manager.updateTask(idTask, tasks, 2);
                        break;
                    }
                    default : {
                        System.out.println("Такого типа задачи нет");
                        break;
                    }
                }

            } else if (number == 7) {
                Manager.deleteTask();
            } else if (number == 8) {
                Manager.updateStatusTask();
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
