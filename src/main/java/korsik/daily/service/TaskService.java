package korsik.daily.service;

import korsik.daily.model.Label;
import korsik.daily.model.Priority;
import korsik.daily.model.Task;
import korsik.daily.model.TaskStatus;
import korsik.daily.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// use find... for search methods
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    /*todo: полностью переписать как сервис, не зависящйи от реализации repository
    public boolean changeTaskStatus(Long taskId, TaskStatus newStatus) {
        if (repository.findAllTasks().contains(Objects.requireNonNull(taskId, "taskId must be set"))) {
            tasks.get(taskId).changeStatus(Objects.requireNonNull(newStatus, "newStatus must be set"));
            return true;
        }
        throw new IllegalArgumentException("task by id " + taskId + " is absen!");
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
        return Optional.ofNullable(tasks.get(taskId));
    }

    public List<Task> findTasksByTitlePart(String titlePart) {
        Objects.requireNonNull(titlePart, "titlePart must be set");
        if (titlePart.isBlank()) {
            throw new IllegalArgumentException("titlePart must not be blank");
        }

        return tasks.values().stream()
                .filter(task -> task.getTitle().contains(titlePart))
                .toList();
    }

    public List<Task> findTasksByDescriptionPart(String descriptionPart) {
        Objects.requireNonNull(descriptionPart, "descriptionPart must be set");
        if (descriptionPart.isBlank()) {
            throw new IllegalArgumentException("descriptionPart must not be blank");
        }

        return tasks.values().stream()
                //todo мне кажется проще без Optional
                .filter(task -> {
                    if (task.getDesc() == null) return false;
                    return task.getDesc().contains(descriptionPart);
                })
                // TODO мне кажется что сверху проще вариант
                .filter( task -> task.getDescription()
                            .map(description -> description.contains(descriptionPart))
                            .orElse(false)
                )
                .toList();
    }

    public List<Task> findTasksByTaskStatus(TaskStatus taskStatus) {
        Objects.requireNonNull(taskStatus, "taskStatus must be set");

        return tasks.values().stream()
                .filter(task -> task.getStatus().equals(taskStatus))
                .toList();
    }

    public List<Task> findTasksByPriority(Priority taskPriority) {
        Objects.requireNonNull(taskPriority, "taskPriority must be set");

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
        Objects.requireNonNull(labelName, "labelName is null");
        if (labelName.isBlank()) {
            throw new IllegalArgumentException("labelName must not be blank");
        }

        String normalizedLabelName = labelName.trim().toLowerCase();

        return tasks.values().stream()
                .filter(task -> task.getLabels().stream()
                        .anyMatch(label -> label.getName().equals(normalizedLabelName)))
                .toList();
    }

    public List<Task> getOverdueTasks(LocalDateTime dateTime) {

        return tasks.values().stream()
                .filter(task -> task.isOverdue(Objects.requireNonNull(dateTime, "dateTime must be set")))
                .toList();
    }

    public List<Task> getTodayDeadlineTasks() {
        LocalDate today = LocalDateTime.now().toLocalDate();

        return tasks.values().stream()
                .filter(task -> task.isStatusRequiredToDo() &&
                        // TODO подумай чтоб убрать optional, мне кажется это неудобно - у тебя фильтрация на 4 строчки
                        task.getDeadline()
                                .map(deadline -> deadline.toLocalDate().isEqual(today))
                                .orElse(false))
                .toList();
    }

    // TODO точно ли нужен этот метод
    public List<Task> getConcreteDayDeadlineTasks(LocalDate date) {
        return tasks.values().stream()
                .filter(task -> task.isStatusRequiredToDo() &&
                        task.getDeadline()
                                .map(deadline -> deadline.toLocalDate().isEqual(Objects.requireNonNull(date, "date must be set")))
                                .orElse(false))
                .toList();
    }

    // TODO зачем этот метод? Отфильтруй незавершенные таски - это тут логично
    public List<Task> getTasksWithoutDeadline() {
        return tasks.values().stream()
                .filter(task -> task.getDeadline().isEmpty())
                .toList();
    }

    // TODO задай начальную дату - например сегодня, чтоб не всю историю отображать
    public List<Task> sortTasksByDeadlineFromEarliestToLatest() {
        return tasks.values().stream()
                .sorted(Comparator.comparing(task -> task.getDeadline().orElse(LocalDateTime.MAX)))
                .toList();
    }

    public List<Task> sortTasksByCreationDateTimeEarliestToLatest() {

        return tasks.values().stream()
                .sorted(Comparator.comparing(Task::getCreatedAt))
                .toList();
    }

    //todo: get finished tasks

     */

}
