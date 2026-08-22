package korsik.daily.model;

import java.time.LocalDate;
import java.util.Map;

// todo (8/22/2026):
//  Фокус дня + выполнен ли
//  Список задач на день
//  Дата -> по ней можно как раз будет узнать Расходы за день, записки за день
//  Чек-бокс для тренировки (была или нет) + связь с тренировкой
public class Day {

    private long id;
    private LocalDate date;

    private String focusOfDay;
    private boolean focusOfDayCompleted;

    private Map<Long, Workout> workouts;
    private boolean dayWithWorkout; //todo (8/22/2026): false if workouts.isEmpty() else true;

    private Map<Long, Expense> expenses;
    private Map<Long, Note> notes;
    private Map<Long, Task> tasks;


}
