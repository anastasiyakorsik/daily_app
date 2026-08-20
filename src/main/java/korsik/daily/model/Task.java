package korsik.daily.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


public class Task {
    private static final int MAX_TITLE_LENGTH = 120;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    //todo 8 aug 2026: add field finishedAt
    //todo 10 aug 2026: add filed describing if task is needed to be repeated in certain period of time
    // private boolean repeatable;
    // private List<LocalDateTime> repeatTime - currently do not understand;
    // need to think of; maybe Enum for repeatableType: DAILY, WEEKLY, BIWEEKLY, MONTHLY, QUARTERLY, ANNUAL, DECADELY, etc
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

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
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

    public boolean addLabel(Label label) {
        return labels.add(Objects.requireNonNull(label, "Label must not be null"));
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
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title.trim();
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder deadline(LocalDateTime deadline){
            this.deadline = deadline;
            return this;
        }

        public Builder status(TaskStatus status){
            this.status = status == null ? TaskStatus.PLANNED : status;
            return this;
        }

        public Builder priority(Priority priority){
            this.priority = priority == null ? Priority.LOW : priority;
            return this;
        }

        public Task build() {
            Objects.requireNonNull(id, "id must not be null");

            if (title == null) {
                throw new IllegalArgumentException("Title of task can not be null.");
            }

            if (title.isBlank()) {
                throw new IllegalArgumentException("Title of task can not be empty or contains only spaces.");
            }

            if (title.length() > MAX_TITLE_LENGTH) {
                throw new IllegalArgumentException("Title is too big. Please, make it shorter.");
            }

            if (description == null) {
                throw new IllegalArgumentException("Description can not be null.");
            }

            if (description.length() > MAX_DESCRIPTION_LENGTH) {
                throw new IllegalArgumentException("Description is too big. Please, make it shorter");
            }

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

        if (dateTime.isBefore(deadline)) {
            return false;
        }

        return isStatusRequiredToDo();
    }

    //todo (6/27/2026) make multiple methods with validation
    public void changeStatus(TaskStatus status) {
        this.status = Objects.requireNonNull(status, "Status must not be null");
//        if (status == TaskStatus.CANCELLED || status == TaskStatus.DONE) {
//
//            //todo: finishedAt (8/17/2026)
//        }
    }

    public void setDeadline(LocalDateTime newDeadline){
        if (LocalDateTime.now().isAfter(Objects.requireNonNull(newDeadline, "Deadline must not be null"))){
            throw new IllegalArgumentException("Deadline can not be in the past");
        }
        this.deadline = newDeadline;
    }

    //todo (6/27/2026)
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

