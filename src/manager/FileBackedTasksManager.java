package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager{
    private static String file;
    public FileBackedTasksManager(String file) {
        this.file = file;
    }

    public void save () {
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
            if (quantitu < getHistory().size())
            stringBuilder.append(",");
        }
        stringBuilder.append("\n");

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(String.valueOf(stringBuilder));
        } catch (IOException e) {
            System.out.println("ошибка считывания");
        }
    }

    public FileBackedTasksManager loadFromFile(String file) {
        String fileContents = readFileContentsOrNull(file);
        String[] lines = fileContents.split("\\n");
        for (String line: lines) {
            fromString(line);
        }
        return new FileBackedTasksManager(file);
    }

    public void fromString(String value) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        String[] elementLine = value.split(",");
        if (value!= null) {
            if (!value.equals("id,type,Name,status,description,epic") && !value.equals("")) {
                if (elementLine.length > 3)
                switch (elementLine[1]) {
                    case "Task":
                        addTask(new Task(elementLine[2], elementLine[4],
                                Integer.parseInt(elementLine[0]), returnStatus(elementLine[3])));
                        break;
                    case "Epic":
                        addEpic(new Epic(elementLine[2], elementLine[4],
                                Integer.parseInt(elementLine[0]), returnStatus(elementLine[3])));
                        break;
                    case "Subtask":
                        Subtask subtask = new Subtask(elementLine[2], elementLine[4], Integer.parseInt(elementLine[0]),
                                Integer.parseInt(elementLine[5]), returnStatus(elementLine[3]));
                        addSubtask(subtask);
                        System.out.println(subtask);
                        break;
                    default:System.out.println("end");
                }
            } /*else if (!value.equals("id,type,Name,status,description,epic") && (elementLine.length == 1 ||
                    (elementLine.length >= 3 && !(elementLine[1] instanceof String)))) {
                for (String s: elementLine) {
                    taskManager.getTaskById(Integer.parseInt(s));
                }
            }*/
        }
    }

    public static Status returnStatus(String status) {
        if (status.equals(Status.NEW)) {
            return Status.NEW;
        } else if (status.equals(Status.DONE)) {
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
        if (getEpicTasks().containsKey(numberId)) {
            getHistory().add(getEpicTasks().get(numberId));
        } else if (getSubtaskTasks().containsKey(numberId)) {
            getHistory().add(getSubtaskTasks().get(numberId));
        } else
            getHistory().add(getTasks().get(numberId));
        save();
        return super.getTaskById(numberId);
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

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    private static String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}