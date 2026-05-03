package korsik.daily.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Task {
    private Long id;
    private String title;
    private Optional<String> description;
    private Optional<LocalDateTime> deadline;
    private TaskStatus status;
    private Priority priority;
    private Set<Tag> tags;

    private static final int MAX_TITLE_LENGTH = 120;

    private static final int MAX_DESCRIPTION_LENGTH = 2000;

    public Task() {
    }

    public Task(Long id,
                String title,
                TaskStatus status,
                Priority priority) {
        this.id = id;
        setTitle(title);
        this.status = status;
        this.priority = priority;
        this.tags = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public LocalDateTime getDeadline() {
        if (deadline.isEmpty()){
            return null;
        }
        else{
            return deadline.get();
        }
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTitle(String title) {
        try {
            if (title == null){
                 throw new Exception("Title of task can not be null.");
            }

            if (title.isBlank()){
                throw new Exception("Title of task can not be empty or contains only spaces.");
            }

            if (title.length() > MAX_TITLE_LENGTH){
                throw new Exception("Title is too big. Please, make it shorter");
            }

            this.title = title.trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setDescription(String description){
        try {
            if (description.length() > MAX_DESCRIPTION_LENGTH){
                throw new Exception("Description is too big. Please, make it shorter");
            }

            this.description = Optional.ofNullable(description);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setDeadline(LocalDateTime deadline){
        this.deadline = Optional.ofNullable(deadline);
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(deadline, task.deadline) && status == task.status && priority == task.priority && Objects.equals(tags, task.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadline, status, priority, tags);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                ", priority=" + priority +
                ", tags=" + tags +
                '}';
    }
}
