package manager;

import task.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTasksManager extends InMemoryTaskManager{
    private String file;
    private static final String fileName = "D:/word.txt";
    public FileBackedTasksManager(String file) {
        this.file = file;
        loadFromFile();
    }

    private void save () {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id,type,Name,status,description,epic\n");

        for (Task task: getTasks().values()) {
            stringBuilder.append(task.toString());
            stringBuilder.append("\n");
        }

        for (Subtask task: getSubtaskTasks().values()) {
            stringBuilder.append(task.toString());
            stringBuilder.append("\n");
        }

        for (Epic task: getEpicTasks().values()) {
            stringBuilder.append(task.toString());
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");

        int quantitu = 0;
        for (Task task: getHistory()) {
            quantitu ++ ;
            stringBuilder.append(task.getId());
            if (quantitu < getHistory().size()) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("\n");

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(String.valueOf(stringBuilder));
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    public void loadFromFile() {
        String fileContents = readFileContentsOrNull(file);
        String[] lines = fileContents.split("\\n");
        for (String line: lines) {
            fromString(line);
        }
    }

    private void fromString(String value) {
        String[] elementLine = value.split(",");
        char[] line = elementLine[0].toCharArray();
        if (!value.equals("id,type,Name,status,description,epic") && !value.equals("") && line.length > 1) {
            if (TypeTask.TASK.toString().equals(elementLine[0])) {
                super.addTask(new Task(elementLine[2], elementLine[4],
                    Integer.parseInt(elementLine[1]), returnStatus(elementLine[3]), TypeTask.TASK));
            } else if (TypeTask.SUBTASK.toString().equals(elementLine[0])) {
                super.addSubtask(new Subtask(elementLine[2], elementLine[4], Integer.parseInt(elementLine[1]),
                    Integer.parseInt(elementLine[5]), returnStatus(elementLine[3])));
            } else if (TypeTask.EPIC.toString().equals(elementLine[0])) {
                super.addEpic(new Epic(elementLine[2], elementLine[4],
                    Integer.parseInt(elementLine[1]), returnStatus(elementLine[3])));
            }
        } else if (!value.equals("id,type,Name,status,description,epic") && !value.equals("")) {
            for (int i = 0; i < elementLine.length; i++) {
                super.getTaskById(Integer.parseInt(elementLine[i]));
            }
        }
    }


    private static Status returnStatus(String status) {
        if (Status.NEW.toString().equals(status)) {
            return Status.NEW;
        } else if (Status.DONE.toString().equals(status)) {
            return Status.DONE;
        } else return Status.IN_PROGRESS;

    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public Task getTaskById(int numberId) {
        Task task = super.getTaskById(numberId);
        save();
        return task;
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteTaskId(int idTask) {
        super.deleteTaskId(idTask);
        save();
    }

    private static String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. " +
                    "Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(IOException cause) {
            super(cause);
        }
    }

    public static void main(String[] args) {
        FileBackedTasksManager manager = new FileBackedTasksManager(fileName);
        manager.addTask(new Task("Погулять",
                "Выйти из дома", 0, Status.NEW, TypeTask.TASK));
        manager.addEpic(new Epic("Уборка",
                "генеральная", 1, Status.NEW));
        manager.addSubtask(new Subtask("Пол",
                "моем", 2, 1, Status.NEW));
        manager.addSubtask(new Subtask("Пыль",
                "протираем", 3, 1, Status.NEW));
        manager.getTaskById(0);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(fileName);
        System.out.println("Если : ");
        System.out.println(fileBackedTasksManager.getHistory() + " = \n" + manager.getHistory() + "\n");
        System.out.println(fileBackedTasksManager.getSubtaskTasks() + " = \n" + manager.getSubtaskTasks() + "\n");
        System.out.println(fileBackedTasksManager.getTasks() + " = \n" + manager.getTasks() + "\n");
        System.out.println(fileBackedTasksManager.getEpicTasks() + " = \n" + manager.getEpicTasks() + "\n");
        System.out.println("все восстановилось верно");
    }
}