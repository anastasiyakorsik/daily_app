package korsik.daily.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


public class Task {
    private static final int MAX_TITLE_LENGTH = 120;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private final Long id;
    private final String title;
    private final LocalDateTime createdAt; //do not add to builder
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
    private Priority priority;
    private Set<Label> labels; // do not add to builder

    public Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.createdAt = LocalDateTime.now();
        this.description = builder.description;
        this.deadline = builder.deadline;
        this.status = builder.status;
        this.priority = builder.priority;
        this.labels = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public Optional<LocalDateTime> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Priority getPriority() {return priority;}

    public Set<Label> getLabels() {
        if (labels == null){
            return Set.of();
        }
        return Set.copyOf(labels);
    }

    public void addLabel(Label label) {
        labels.add(Objects.requireNonNull(label, "Label must not be null"));
    }

    public void removeLabel(Label label) {
        labels.remove(Objects.requireNonNull(label, "Label must not be null"));
    }

    public boolean containsLabel(String labelName) {
        if (labelName == null){
            throw new NullPointerException("label name must be set");
        }
        if (labels == null || labelName.isEmpty()){
            return false;
        }
        for (Label label : labels){
            if (label.getName().equals(labelName)){
                return true;
            }
        }
        return false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private Long id;
        private String title;
        private String description;
        private LocalDateTime deadline;
        private TaskStatus status;
        private Priority priority;

        public Builder id(Long id){
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        public Builder title(String title){
            if (title == null) {
                throw new IllegalArgumentException("Title of task can not be null.");
            }

            if (title.isBlank()) {
                throw new IllegalArgumentException("Title of task can not be empty or contains only spaces.");
            }

            if (title.length() > MAX_TITLE_LENGTH) {
                throw new IllegalArgumentException("Title is too big. Please, make it shorter.");
            }

            this.title = title.trim();
            return this;
        }

        public Builder description(String description){
            if (description == null) {
                throw new IllegalArgumentException("Description can not be null.");
            }

            if (description.length() > MAX_DESCRIPTION_LENGTH) {
                throw new IllegalArgumentException("Description is too big. Please, make it shorter");
            }
            this.description = description;
            return this;
        }

        public Builder deadline(LocalDateTime deadline){
            this.deadline = deadline;
            return this;
        }

        public Builder status(TaskStatus status){
            if (status == null){
                status = TaskStatus.PLANNED;
            }
            this.status = status;
            return this;
        }

        public Builder priority(Priority priority){
            if (priority == null){
                priority = Priority.LOW;
            }
            this.priority = priority;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    public boolean isStatusRequiredToDo() {
        if (status == TaskStatus.DONE) {
            return false;
        }
        return status != TaskStatus.CANCELLED;
    }

    public boolean isOverdue(LocalDateTime dateTime) {
        if (deadline == null) {
            return false;
        }

        if (dateTime.isAfter(deadline)) {
            return false;
        }

        return isStatusRequiredToDo();
    }

    //todo make multiple methods with validation
    public void changeStatus(TaskStatus status) {
        this.status = Objects.requireNonNull(status, "Status must not be null");
    }

    public void setDeadline(LocalDateTime newDeadline){
        if (LocalDateTime.now().isAfter(Objects.requireNonNull(newDeadline, "Deadline must not be null"))){
            throw new IllegalArgumentException("Deadline can not be in the past");
        }
        this.deadline = newDeadline;
    }

    //todo
    public void rescheduleDeadline(){
        return;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                ", priority=" + priority +
                ", labels=" + labels +
                '}';
    }
}

