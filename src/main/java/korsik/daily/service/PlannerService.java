package korsik.daily.service;

import korsik.daily.model.Expense;
import korsik.daily.model.Note;
import korsik.daily.model.Tag;
import korsik.daily.model.Task;
import korsik.daily.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlannerService {

    private List<Task> tasks;
    private List<Note> notes;
    private List<Expense> expenses;

    public void addTask(Task task){
        //todo: check if provided task can be added
        try {
            tasks.add(task);
            System.out.println("Given task was successfully saved");
        } catch (Exception e) {
            System.out.println("Failed to save task");
            throw new RuntimeException(e);
        }
    }

    public void removeTaskById(Long taskId){
        try {
            for (Task task : tasks){
                if (task.getId().equals(taskId)){
                    tasks.remove(task);
                    return;
                }
            }
            System.out.println(String.format("Task with Id: %d is not found in saved tasks", taskId));
        } catch (Exception e) {
            System.out.println("Failed to remove provided task");
            throw new RuntimeException(e);
        }
    }

    public Task findTaskById(Long taskId){
        for (Task task : tasks){
            if (task.getId().equals(taskId)){
                return task;
            }
        }
        System.out.println(String.format("Task with Id: %d is not found in saved tasks", taskId));
        return null;
    }

    public List<Task> findTasksByTaskStatus(TaskStatus taskStatus){
        List<Task> tasksWithGivenStatus = new ArrayList<>();
        for (Task task : tasks){
            if (task.getStatus().equals(taskStatus)){
                tasksWithGivenStatus.add(task);
            }
        }
        return tasksWithGivenStatus;
    }

    public List<Task> findTasksByTag(Tag tag){
        List<Task> tasksWithGivenTag = new ArrayList<>();
        for (Task task : tasks){
            if (task.getTags().contains(tag)){
                tasksWithGivenTag.add(task);
            }
        }
        return tasksWithGivenTag;
    }

    public List<Task> getOverdueTasks(LocalDateTime datetime){
        List<Task> overdueTasks = new ArrayList<>();
        for (Task task : tasks){
            if (task.getDeadline().isAfter(datetime) &&
                    !task.getStatus().equals(TaskStatus.DONE) && !task.getStatus().equals(TaskStatus.CANCELLED)){
                overdueTasks.add(task);
            }
        }
        return overdueTasks;
    }


}
