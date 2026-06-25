package korsik.daily.service;

import korsik.daily.model.Expense;
import korsik.daily.model.Note;
import korsik.daily.model.Priority;
import korsik.daily.model.Tag;
import korsik.daily.model.Task;
import korsik.daily.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskService {

    private Map<Long, Task> tasks = new HashMap<>();

    public Map<Long, Task> getAllTasks(){
        return tasks;
    }

    public void addTask(Task task){
        try {
            tasks.put(task.getId(), task);
            System.out.println("Given task was successfully saved");
        } catch (Exception e) {
            System.out.println("Failed to save task");
            throw new RuntimeException(e);
        }
    }

    public void changeTaskStatus(Task task, TaskStatus newStatus){
        task.setStatus(newStatus);
    }

    //todo: think about check containing
    public void addTagToTask(Task task, Tag tag){
        if (!task.getTags().contains(tag)){
            task.addTag(tag);
        }
        else{
            throw new RuntimeException("Given tag is already applied to task");
        }
    }

    public void removeTaskById(Long taskId){
        try {
            tasks.remove(taskId);
            System.out.println(String.format("Task with Id: %d is not found in saved tasks", taskId));
        } catch (Exception e) {
            System.out.println("Failed to remove provided task");
            throw new RuntimeException(e);
        }
    }

    public Task findTaskById(Long taskId){
        if (tasks.keySet().contains(taskId)){
            return tasks.get(taskId);
        }
        System.out.println(String.format("Task with Id: %d is not found in saved tasks", taskId));
        return null;
    }

    public List<Task> findTasksByTaskStatus(TaskStatus taskStatus){
        List<Task> tasksWithGivenStatus = new ArrayList<>();
        for (Task task : tasks.values()){
            if (task.getStatus().equals(taskStatus)){
                tasksWithGivenStatus.add(task);
            }
        }
        return tasksWithGivenStatus;
    }

    public List<Task> findTasksByPriority(Priority taskPriority){
        List<Task> tasksWithGivenPriority = new ArrayList<>();
        for (Task task : tasks.values()){
            if (task.getPriority().equals(taskPriority)){
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

    public List<Task> findTasksByTagName(String tagName){
        List<Task> tasksWithGivenTag = new ArrayList<>();
        for (Task task : tasks.values()){
            Set<Tag> taskTags = task.getTags();
            for (Tag tag : taskTags){
                if (tag.getName().equals(tagName)){
                    tasksWithGivenTag.add(task);
                }
            }
        }
        return tasksWithGivenTag;
    }

    public List<Task> getOverdueTasks(LocalDateTime datetime){
        List<Task> overdueTasks = new ArrayList<>();
        for (Task task : tasks.values()){
            if (task.getDeadline().isBefore(datetime) &&
                    !task.getStatus().equals(TaskStatus.DONE) && !task.getStatus().equals(TaskStatus.CANCELLED)){
                overdueTasks.add(task);
            }
        }
        return overdueTasks;
    }

    public List<Task> getTodayTasks(){
        LocalDate today = LocalDateTime.now().toLocalDate();
        List<Task> todayTasks = new ArrayList<>();
        for (Task task : tasks.values()){
            if (task.getDeadline().toLocalDate().isEqual(today) &&
                    !task.getStatus().equals(TaskStatus.DONE) && !task.getStatus().equals(TaskStatus.CANCELLED)){
                todayTasks.add(task);
            }
        }
        return todayTasks;
    }

    public List<Task> getTasksWithoutDeadline(){
        List<Task> tasksWithoutDeadline = new ArrayList<>();
        for (Task task : tasks.values()){
            if (task.getDeadline() == null){
                tasksWithoutDeadline.add(task);
            }
        }
        return tasksWithoutDeadline;
    }

    public List<Task> sortTasksByDeadlineFromEarliestToLatest() {
        List<Task> sortedTask = tasks.values().stream().toList();
        sortedTask.sort(Comparator.comparing(Task::getDeadline));
        return sortedTask;
    }

    public List<Task> sortTasksByCreationDateTimeEarliestToLatest() {
        List<Task> sortedTask = tasks.values().stream().toList();
        sortedTask.sort(Comparator.comparing(Task::getCreatedAt));
        return sortedTask;
    }

}
