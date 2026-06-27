package korsik.daily.service;

import korsik.daily.model.Label;
import korsik.daily.model.Priority;
import korsik.daily.model.Task;
import korsik.daily.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class InMemoryTaskService {
    private final Map<Long, Task> tasks;

    public InMemoryTaskService(Map<Long, Task> tasks) {
        this.tasks = Objects.requireNonNull(tasks, "tasks may not be null");
    }

    public InMemoryTaskService() {
        this(new HashMap<>());
    }

    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    //todo logging!
    public void addTask(Task task) {
        tasks.put(
                Objects.requireNonNull(task, "task may not be null").getId(),
                task
        );
        System.out.println("Given task was successfully saved");
    }

    public void changeTaskStatus(Task task, TaskStatus newStatus) {
        task.changeStatus(newStatus);
    }

    //todo: think about check containing
    public void addTagToTask(Task task, Label label) {
        if (!task.getLabels().contains(label)) {
            task.addLabel(label);
        } else {
            throw new RuntimeException("Given tag is already applied to task");
        }
    }

    public void removeTaskById(Long taskId) {
        try {
            tasks.remove(taskId);
            System.out.println(String.format("Task with Id: %d is not found in saved tasks", taskId));
        } catch (Exception e) {
            System.out.println("Failed to remove provided task");
            throw new RuntimeException(e);
        }
    }

    public Task findTaskById(Long taskId) {
        if (tasks.keySet().contains(taskId)) {
            return tasks.get(taskId);
        }
        System.out.println(String.format("Task with Id: %d is not found in saved tasks", taskId));
        return null;
    }

    public List<Task> findTasksByTaskStatus(TaskStatus taskStatus) {
        List<Task> tasksWithGivenStatus = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getStatus().equals(taskStatus)) {
                tasksWithGivenStatus.add(task);
            }
        }
        return tasksWithGivenStatus;
    }

    public List<Task> findTasksByPriority(Priority taskPriority) {
        List<Task> tasksWithGivenPriority = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getPriority().equals(taskPriority)) {
                tasksWithGivenPriority.add(task);
            }
        }
        return tasksWithGivenPriority;
    }

//    public List<Task> sortTasksByPriority(){
//        List<Task> sortedTasksByPriority = new ArrayList<>();
//        for (Task task : tasks){
//            if (task.getPriority().equals(taskPriority)){
//                sortedTasksByPriority.add(task);
//            }
//        }
//        return sortedTasksByPriority;
//    }

    public List<Task> findTasksByTagName(String tagName) {
        List<Task> tasksWithGivenTag = new ArrayList<>();
        for (Task task : tasks.values()) {
            Set<Label> taskTagEntities = task.getLabels();
            for (Label label : taskTagEntities) {
                if (label.getName().equals(tagName)) {
                    tasksWithGivenTag.add(task);
                }
            }
        }
        return tasksWithGivenTag;
    }

    public List<Task> getOverdueTasks(LocalDateTime dateTime) {
        List<Task> overdueTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.isOverdue(dateTime)) {
                overdueTasks.add(task);
            }
        }
        return overdueTasks;
    }

    public List<Task> getTodayTasks() {
        LocalDate today = LocalDateTime.now().toLocalDate();
        List<Task> todayTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            task.getDeadline().ifPresent(
                    new Consumer<LocalDateTime>() {
                        @Override
                        public void accept(LocalDateTime presentDeadline) {
                            if (presentDeadline.toLocalDate().isEqual(today) &&
                                    !task.getStatus().equals(TaskStatus.DONE) && !task.getStatus().equals(TaskStatus.CANCELLED)) {
                                todayTasks.add(task);
                            }
                        }
                    }
            );
        }
        return todayTasks;
    }

    public List<Task> getTasksWithoutDeadline() {
        List<Task> tasksWithoutDeadline = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getDeadline() == null) {
                tasksWithoutDeadline.add(task);
            }
        }
        return tasksWithoutDeadline;
    }

    public List<Task> sortTasksByDeadlineFromEarliestToLatest() {
        List<Task> sortedTasks = new ArrayList<>(tasks.values());

        sortedTasks.sort(
                Comparator.comparing(task -> task.getDeadline().orElse(LocalDateTime.MAX))
        );

        return sortedTasks;
    }

    public List<Task> sortTasksByCreationDateTimeEarliestToLatest() {
        List<Task> sortedTask = tasks.values().stream().toList();
        sortedTask.sort(Comparator.comparing(Task::getCreatedAt));
        return sortedTask;
    }

}
