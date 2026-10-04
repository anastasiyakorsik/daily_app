package korsik.daily.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


public class Task {
    private static final int MAX_TITLE_LENGTH = 120;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private final Long id;
    private final LocalDateTime createdAt; //do not add to builder
    private LocalDateTime finishedAt; // do not add to builder

    private String title;
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
    private Priority priority;
    private TaskRepeatType repeatType;
    private Set<Label> labels; // do not add to builder
    private Set<LocalDate> assignToDays;


    public Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.description = builder.description;
        this.deadline = builder.deadline;
        this.status = builder.status;
        this.priority = builder.priority;
        this.repeatType = builder.repeatType;
        this.assignToDays = new HashSet<>();
        this.labels = new HashSet<>();
    }

    public static Builder builder() {
        return new Builder();
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

    public Optional<LocalDateTime> getFinishedAt() {
        return Optional.ofNullable(finishedAt);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public String getDesc() {
        return description;
    }

    public Optional<LocalDateTime> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    public void setDeadline(LocalDateTime newDeadline) {
        if (LocalDateTime.now().isAfter(Objects.requireNonNull(newDeadline, "Deadline must not be null"))) {
            throw new IllegalArgumentException("Deadline can not be in the past");
        }
        this.deadline = newDeadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public TaskRepeatType getRepeatType() {
        return repeatType;
    }

    public Set<LocalDate> getAssignToDays() {
        if (assignToDays == null || assignToDays.isEmpty()) {
            return new HashSet<>();
        }
        return Set.copyOf(assignToDays);
    }

    public Set<Label> getLabels() {
        if (labels == null) {
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
        if (labelName == null) {
            throw new NullPointerException("label name must be set");
        }
        if (labels == null || labelName.isEmpty()) {
            return false;
        }
        for (Label label : labels) {
            if (label.getName().equals(labelName)) {
                return true;
            }
        }
        return false;
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
        Objects.requireNonNull(status, "Status must not be null");
        if (status.equals(TaskStatus.DONE)) {
            this.finishedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        }
        this.status = status;
//        if (status == TaskStatus.CANCELLED || status == TaskStatus.DONE) {
//        }
    }

    public void changeRepeatType(TaskRepeatType repeatType) {
        Objects.requireNonNull(repeatType, "repeatType must be not null");
        this.repeatType = repeatType;
    }

    //todo (8/31/2026): auto assign if repeatable
    // or how... think of
    public boolean assignToDay(LocalDate newDay) {
        return assignToDays.add(newDay);
    }

    //todo (6/27/2026)
    public void rescheduleDeadline() {
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

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime deadline;
        private TaskStatus status;
        private Priority priority;
        private TaskRepeatType repeatType;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title.trim();
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder deadline(LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status == null ? TaskStatus.PLANNED : status;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority == null ? Priority.LOW : priority;
            return this;
        }

        public Builder repeatType(TaskRepeatType repeatType) {
            this.repeatType = repeatType == null ? TaskRepeatType.NONREPEATABLE : repeatType;
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

            if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
                throw new IllegalArgumentException("Description is too big. Please, make it shorter");
            }

            return new Task(this);
        }
    }
}

