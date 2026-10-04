package korsik.daily.repository;

import korsik.daily.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void add(Task task);

    void update(Task task);

    boolean deleteById(Long id);

    Optional<Task> findTaskById(Long id);

    List<Task> findAllTasks();

    boolean existsById(Long id);
}
