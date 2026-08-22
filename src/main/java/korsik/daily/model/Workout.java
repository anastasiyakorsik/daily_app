package korsik.daily.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Workout {
    private long id;
    private String title;
    private String comment;
    private LocalDate date;
    private Duration duration;
    private Set<WorkoutType> workoutTypes;

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_COMMENT_LENGTH = 200;

    public Workout(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.comment = builder.comment;
        this.date = builder.date;
        this.duration = builder.duration;
        this.workoutTypes = builder.workoutTypes;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Optional<String> getComment() {
        return Optional.of(comment);
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<Duration> getDuration() {
        return Optional.of(duration);
    }

    public Set<WorkoutType> getWorkoutTypes() {
        return Set.copyOf(workoutTypes);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String title;
        private String comment;
        private LocalDate date;
        private Duration duration;
        private Set<WorkoutType> workoutTypes = new HashSet<>();

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder comment(String comment){
            this.comment = comment;
            return this;
        }

        public Builder date(LocalDate date){
            this.date = date;
            return this;
        }

        public Builder duration(Duration duration){
            this.duration = duration;
            return this;
        }

        public Builder comment(Set<WorkoutType> workoutTypes){
            this.workoutTypes = workoutTypes;
            return this;
        }

        public Workout build() {
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

            if (comment.length() > MAX_COMMENT_LENGTH) {
                throw new IllegalArgumentException("Comment is too big. Please, make it shorter");
            }

            Objects.requireNonNull(date, "Date must not be null");

            return new Workout(this);
        }

    }

    public boolean addWorkoutType(WorkoutType workoutType){
        return workoutTypes.add(Objects.requireNonNull(workoutType, "workoutType can not be null"));
    }

    public void setDuration(int hours, int minutes, int seconds){
        Duration hoursDuration = Duration.ofHours(hours);
        Duration minutesDuration = Duration.ofMinutes(minutes);
        Duration secondsDuration = Duration.ofSeconds(seconds);
        this.duration = hoursDuration.plus(minutesDuration).plus(secondsDuration);
    }

    public void setDuration(int hours, int minutes){
        Duration hoursDuration = Duration.ofHours(hours);
        Duration minutesDuration = Duration.ofMinutes(minutes);
        this.duration = hoursDuration.plus(minutesDuration);
    }

    public void setDuration(int minutes){
        this.duration = Duration.ofMinutes(minutes);
    }

    public void setComment(String comment){
        if (Objects.requireNonNull(comment, "comment must not be null").isBlank()){
            throw new IllegalArgumentException("comment can not be blank");
        }
        this.comment = comment;
    }
}
