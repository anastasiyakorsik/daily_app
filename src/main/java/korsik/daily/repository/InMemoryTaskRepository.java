package korsik.daily.repository;

import korsik.daily.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository{

    private final Map<Long, Task> tasks;

    public InMemoryTaskRepository(Map<Long, Task> tasks) {
        this.tasks = new HashMap<>(tasks);
    }

    public InMemoryTaskRepository() {
        this(new HashMap<>());
    }

    public void add(Task task){
        if (tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException(String.format("Task with id %d is already added", task.getId()));
        }
        tasks.put(
                Objects.requireNonNull(task, "task may not be null").getId(),
                task
        );
    }

    public void update(Task task){
        tasks.put(Objects.requireNonNull(task.getId()), task);
    }

    public boolean deleteById(Long id) {
        if (tasks.containsKey(Objects.requireNonNull(id, "taskId must be set"))) {
            tasks.remove(id);
            return true;
        }
        return false;
    }

    public Optional<Task> findTaskById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<Task> findAllTasks() {
        return List.copyOf(tasks.values());
    }

    public boolean existsById(Long id) {
        return tasks.containsKey(id);
    }
}
