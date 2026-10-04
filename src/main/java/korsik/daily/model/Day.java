package korsik.daily.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Day {
    private final LocalDate date;
    private final Map<Long, Workout> workouts;
    private final Map<Long, Expense> expenses;
    private final Map<Long, Note> notes;
    private final Map<Long, Task> tasks;

    private FocusOfDay focusOfDay;
    private boolean dayWithWorkout; //todo (8/22/2026): false if workouts.isEmpty() else true;

    public Day(LocalDate date) {
        this.date = Objects.requireNonNull(date);
        this.workouts = new HashMap<>();
        this.expenses = new HashMap<>();
        this.notes = new HashMap<>();
        this.tasks = new HashMap<>();
        this.dayWithWorkout = false;
    }

    public Optional<String> getFocusOfDay() {
        if (focusOfDay.getFocusOfDay() == null || focusOfDay.getFocusOfDay().isBlank()) {
            return Optional.empty();
        }
        return Optional.of(this.focusOfDay.getFocusOfDay());
    }
    public boolean isFocusOfDayCompleted() {
        return this.focusOfDay.isFocusOfDayCompleted();
    }


    //TODO - это сеттеры, а менять состояние этой сущнти надо через сервис!
    //public void setFocusOfDay(String focusOfDay) {
    //    this.focusOfDay = new FocusOfDay(focusOfDay);
    //}
//
    //public void completeFocusOfDay() {
    //    this.focusOfDay.completeFocusOfDay();
    //}



    public LocalDate getDate() {
        return date;
    }

    public Map<Long, Workout> getWorkouts() {
        return Map.copyOf(workouts);
    }

    public void addWorkout(Workout workout) {
        if (workout == null) {
            throw new NullPointerException("Can't add null workout to day");
        }
        if (workouts.containsKey(workout.getId())) {
            throw new IllegalArgumentException(String.format("Workout with %d id is already added", workout.getId()));
        }
        workouts.put(workout.getId(), workout);
        dayWithWorkout = true;
    }

    public boolean isDayWithWorkout() {
        return dayWithWorkout;
    }

    // todo выпили мапу - сделай List<Expense>
    public Map<Long, Expense> getExpenses() {
        return Map.copyOf(expenses);
    }

    //todo это сеттер
    public void addExpense(Expense expense) {
        if (expense == null) {
            throw new NullPointerException("Can't add null expense to day");
        }
        if (expenses.containsKey(expense.getId())) {
            throw new IllegalArgumentException(String.format("Expense with %d id is already added", expense.getId()));
        }
        expenses.put(expense.getId(), expense);
    }

    //todo выпилить мапу
    public Map<Long, Note> getNotes() {
        return Map.copyOf(notes);
    }

    // todo это в сервис!
    public void addNote(Note note) {
        if (note == null) {
            throw new NullPointerException("Can't add null note to day");
        }
        if (notes.containsKey(note.getId())) {
            throw new IllegalArgumentException(String.format("Note with %d id is already added", note.getId()));
        }
        notes.put(note.getId(), note);
    }

    // todo выпилить мапу
    public Map<Long, Task> getTasks() {
        return Map.copyOf(tasks);
    }

    //
    public void addTask(Task task) {
        if (task == null) {
            throw new NullPointerException("Can't add null task to day");
        }
        if (tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException(String.format("Task with %d id is already added", task.getId()));
        }
        tasks.put(task.getId(), task);
    }

    private static class FocusOfDay {
        Map<String, String> map;
        private final String focusOfDay;
        private boolean focusOfDayCompleted;
        public FocusOfDay(String focusOfDay) {
            if (focusOfDay == null) {
                throw new NullPointerException("focus of Day can not be null");
            }
            if (focusOfDay.isBlank()) {
                throw new IllegalArgumentException("focus of Day can not be blank");
            }
            this.focusOfDay = focusOfDay;
            this.focusOfDayCompleted = false;
        }

        public String getFocusOfDay() {
            Collection<String> vals = map.values();
            return focusOfDay;
        }

        public boolean isFocusOfDayCompleted() {
            return focusOfDayCompleted;
        }

        public void completeFocusOfDay() {
            this.focusOfDayCompleted = true;
        }
    }
}
