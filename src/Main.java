import manager.FileBackedTasksManager;
import task.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String fileName = "D:/word.txt";
    private static final FileBackedTasksManager manager = new FileBackedTasksManager(fileName);
    private static final DateTimeFormatter formatt = DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm");

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
            }  else if (number == 11) {
                manager.getPrioritizedTasks();
            }  else if (number == 12) {
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
        System.out.println("В какое время вы хотите начать? Введите в формате: dd.MM.yyyy,HH:mm");
        String time = scanner.next();
        LocalDateTime startTime = LocalDateTime.parse(time, formatt);
        System.out.println("Сколько времени понадобится чтобы решить эту задачу в секундах?");
        Integer timeSec = scanner.nextInt();
        Duration duration = Duration.between(startTime, startTime.plusSeconds(timeSec));
        if (typeTask == 1) {
            Epic epic = new Epic(title, description, manager.getId());
            manager.addEpic(epic);
        } else if (typeTask == 2) {
            System.out.println("К какому эпику добавить сабтаск ?");
            printEpic();
            int epicId = scanner.nextInt();
            Subtask subtask = new Subtask(title, description, manager.getId(), epicId, Status.NEW, duration, startTime);
            manager.addSubtask(subtask);
        } else if (typeTask == 3) {
            Task task = new Task(title, description, manager.getId(), Status.NEW, TypeTask.TASK, duration, startTime);
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
        System.out.println("В какое время вы хотите начать? Введите в формате: dd.MM.yyyy,HH:mm");
        String time = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(time, formatt);
        System.out.println("Сколько времени понадобится чтобы решить эту задачу в секундах?");
        Integer timeSec = scanner.nextInt();
        Duration duration = Duration.between(startTime, startTime.plusSeconds(timeSec));
        if (manager.getEpicTasks().containsKey(idTask)) {
            if (!manager.getEpicTasks().get(idTask).getListSubtask().isEmpty()) {
                List<Subtask> sub = manager.getEpicTasks().get(idTask).getListSubtask();
                manager.getEpicTasks().remove(idTask);
                Epic epic = new Epic(title, description, idTask);
                for (Subtask subs: sub) {
                    epic.addSubtaskInList(subs);
                }
                manager.updateTask(epic);
            } else {
                manager.getEpicTasks().remove(idTask);
                Epic epic = new Epic(title, description, idTask);
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
            Subtask subtask = new Subtask(title, description, idTask, getEpic, Status.NEW, duration, startTime);
            manager.updateTask(subtask);
            manager.getEpicTasks().get(getEpic).addSubtaskInList(subtask);
        } else if (manager.getTasks().containsKey(idTask)) {
            printTask();
            manager.deleteTaskId(idTask);
            Task tasks = new Task(title, description, idTask, Status.NEW, TypeTask.TASK, duration, startTime);
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
            Duration duration = manager.getSubtaskTasks().get(idSubtask).getDuration();
            LocalDateTime startTime = manager.getSubtaskTasks().get(idSubtask).getStartTime();
            manager.deleteTaskId(idSubtask);
            Subtask subtask = new Subtask(title, description, idSubtask, idEpic, status, duration, startTime);
            manager.addSubtask(subtask);
        } else if (typeTask == 2) {
            System.out.println("С каким идентификатором менять статус таск ?");
            printTask();
            int idTask = scanner.nextInt();
            String title = manager.getTasks().get(idTask).getTitle();
            String description = manager.getTasks().get(idTask).getDescription();
            int idTasks = manager.getTasks().get(idTask).getId();
            Duration duration = manager.getTasks().get(idTask).getDuration();
            LocalDateTime startTime = manager.getTasks().get(idTask).getStartTime();
            manager.deleteTaskId(idTask);
            Task task = new Task(title, description, idTasks , status, TypeTask.TASK, duration, startTime);
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
                "11- Вывести список задач в порядке приоритета\n" +
                "12 - конец");
        return scanner.nextInt();
    }

    public static void printTask() {
        for (Task task: manager.getTasks().values()) {
            System.out.println("ID : " + task.getId()
                    + " title : " + task.getTitle()
                    + " description : " + task.getDescription()
                    + " status : " + task.getStatus()
                    + " startTime : " + task.getStartTime()
                    + " GetEndTime : " + task.getGetEndTime());

        }
    }

    public static void printEpic() {
        for (Epic epic : manager.getEpicTasks().values()) {
            System.out.println("ID : " + epic.getId()
                    + " title : " + epic.getTitle()
                    + " description : " + epic.getDescription()
                    + " status : " + epic.getStatus()
                    + " startTime : " + epic.getStartTime()
                    + " getEndTime : " + epic.getGetEndTime());
        }
    }

    public static void printSubtask() {
        for (Subtask subtask : manager.getSubtaskTasks().values()) {
            System.out.println("ID : " + subtask.getId()
                    + " title : " + subtask.getTitle()
                    + " description : " + subtask.getDescription()
                    + " status : " + subtask.getStatus()
                    + " startTime : " + subtask.getStartTime()
                    + " getEndTime : " + subtask.getGetEndTime());
        }
    }

    public static void printSubtaskTaskOnId(List<Subtask> subtask) {
        for (Subtask sub: subtask) {
            System.out.println("ID : " + sub.getId()
                    + " title : " + sub.getTitle()
                    + " description : " + sub.getDescription()
                    + " status : " + sub.getStatus()
                    + " startTime : " + sub.getStartTime()
                    + " getEndTime : " + sub.getGetEndTime());
        }
    }
}
