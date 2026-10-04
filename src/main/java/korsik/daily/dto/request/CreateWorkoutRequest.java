package korsik.daily.dto.request;

import korsik.daily.model.WorkoutType;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

//todo допиши этот класс
public class CreateWorkoutRequest {
    private String title;
    private String comment;
    private LocalDate date;
    private Duration duration;
    private Set<WorkoutType> workoutTypes;
}
