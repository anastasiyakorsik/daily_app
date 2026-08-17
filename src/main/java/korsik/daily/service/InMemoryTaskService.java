package korsik.daily.service;

import korsik.daily.model.Label;
import korsik.daily.model.Note;
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
import java.util.Optional;
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
    }

    public boolean changeTaskStatus(Long taskId, TaskStatus newStatus) {
        if (tasks.containsKey(Objects.requireNonNull(taskId, "taskId must be set"))){
            tasks.get(taskId).changeStatus(Objects.requireNonNull(newStatus, "newStatus must be set"));
            return true;
        }
        return false;
    }

    public boolean addLabelToTask(Long taskId, Label label) {
        if (tasks.containsKey(Objects.requireNonNull(taskId, "taskId must be set"))) {
            return tasks.get(taskId).addLabel(Objects.requireNonNull(label, "label must be set"));
        }
        return false;
    }

    public boolean removeTaskById(Long taskId) {
        if (tasks.containsKey(Objects.requireNonNull(taskId, "taskId must be set"))) {
            tasks.remove(taskId);
            return true;
        }
        return false;
    }

    public Optional<Task> findTaskById(Long taskId) {
        if (tasks.containsKey(Objects.requireNonNull(taskId, "taskId must be set"))) {
            return Optional.ofNullable(tasks.get(taskId));
        }
        return Optional.empty();
    }

    public List<Task> findTasksByTitlePart(String titlePart){
        Objects.requireNonNull(titlePart, "titlePart must be set");

        if (titlePart.isBlank()) {
            throw new IllegalArgumentException("titlePart must not be blank");
        }

        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task-> task.getTitle().contains(titlePart))
                .toList();
    }

    public List<Task> findTasksByDescriptionPart(String descriptionPart){
        Objects.requireNonNull(descriptionPart, "descriptionPart must be set");

        if (descriptionPart.isBlank()) {
            throw new IllegalArgumentException("descriptionPart must not be blank");
        }

        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task -> task.getDescription()
                        .map(description -> description.contains(descriptionPart))
                        .orElse(false))
                .toList();
    }

    public List<Task> findTasksByTaskStatus(TaskStatus taskStatus) {

        Objects.requireNonNull(taskStatus, "taskStatus must be set");

        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task -> task.getStatus().equals(taskStatus))
                .toList();
    }

    public List<Task> findTasksByPriority(Priority taskPriority) {

        Objects.requireNonNull(taskPriority, "taskPriority must be set");

        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task -> task.getPriority().equals(taskPriority))
                .toList();
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

    public List<Task> findTasksByLabelName(String labelName) {
        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        if (labelName.isBlank()){
            throw new IllegalArgumentException("labelName must not be blank");
        }

        String normalizedLabelName = Objects.requireNonNull(labelName, "labelName must be set").trim().toLowerCase();

        return tasks.values().stream()
                .filter(task -> task.getLabels().stream()
                .anyMatch(label -> label.getName().equals(normalizedLabelName)))
                .toList();
    }

    public List<Task> getOverdueTasks(LocalDateTime dateTime) {

        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task -> task.isOverdue(Objects.requireNonNull(dateTime, "dateTime must be set")))
                .toList();
    }

    public List<Task> getTodayDeadlineTasks() {
        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        LocalDate today = LocalDateTime.now().toLocalDate();

        return tasks.values().stream()
                .filter(task -> task.isStatusRequiredToDo() &&
                        task.getDeadline()
                        .map(deadline -> deadline.toLocalDate().isEqual(today))
                        .orElse(false))
                .toList();
    }

    public List<Task> getConcreteDayDeadlineTasks(LocalDate date) {
        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task -> task.isStatusRequiredToDo() &&
                        task.getDeadline()
                                .map(deadline -> deadline.toLocalDate().isEqual(Objects.requireNonNull(date, "date must be set")))
                                .orElse(false))
                .toList();
    }

    public List<Task> getTasksWithoutDeadline() {
        if (tasks.isEmpty()) {
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task -> task.getDeadline().isEmpty())
                .toList();
    }

    public List<Task> sortTasksByDeadlineFromEarliestToLatest() {
        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .sorted(Comparator.comparing(task -> task.getDeadline().orElse(LocalDateTime.MAX)))
                .toList();
    }

    public List<Task> sortTasksByCreationDateTimeEarliestToLatest() {

        if (tasks.isEmpty()){
            return new ArrayList<>();
        }

        return tasks.values().stream()
                .sorted(Comparator.comparing(Task::getCreatedAt))
                .toList();
    }

    //todo: get finished tasks

}
