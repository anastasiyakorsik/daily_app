package korsik.daily.service;

import korsik.daily.model.Label;
import korsik.daily.model.LabelColor;
import korsik.daily.model.Priority;
import korsik.daily.model.Task;
import korsik.daily.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InMemoryTaskServiceTest {

    public InMemoryTaskService inMemoryTaskServiceFilledValues;
    public InMemoryTaskService inMemoryTaskServiceInitiallyEmptyValues;
    public Map<String, Label> labels = new HashMap<>();
    public Map<Long, Task> tasks = new HashMap<>();

    static Stream<Label> newLabelProvider() {
        return Stream.of(
                new Label(66L, "hobbies", LabelColor.PURPLE, true),
                new Label(77L, "self-care", LabelColor.ORANGE, true),
                new Label(88L, "health", LabelColor.YELLOW, true)
        );
    }

    static Stream<Arguments> taskProvider() {
        return Stream.of(
                Arguments.of(
                        10L,
                Task.builder()
                        .id(10L)
                        .title("work_task_done")
                        .deadline(LocalDateTime.of(2026, 8, 8, 9, 0))
                        .priority(Priority.HIGH)
                        .status(TaskStatus.DONE)
                        .build()
                        ),

                Arguments.of(
                        11L,
                Task.builder()
                    .id(11L)
                    .title("work_task_without_deadline")
                    .deadline(LocalDateTime.of(2026, 8, 11, 10, 0))
                    .priority(Priority.LOW)
                    .status(TaskStatus.IN_PROGRESS)
                    .build()),

                Arguments.of(
                        20L,
                Task.builder()
                    .id(20L)
                    .title("study_task_planned")
                    .deadline(LocalDateTime.of(2026, 9, 10, 12, 0))
                    .priority(Priority.MEDIUM)
                    .status(TaskStatus.PLANNED)
                    .build()),

                Arguments.of(
                        21L,
                Task.builder()
                    .id(21L)
                    .title("study_task_in_progress")
                    .deadline(LocalDateTime.of(2026, 9, 11, 12, 0))
                    .priority(Priority.HIGH)
                    .status(TaskStatus.IN_PROGRESS)
                    .build()),

                Arguments.of(
                        30L,
                Task.builder()
                    .id(30L)
                    .title("sport_task_daily")
                    .build()),

                Arguments.of(
                        31L,
                Task.builder()
                    .id(31L)
                    .title("sport_task_challenge")
                    .description("start running")
                    .build()),

                Arguments.of(
                        40L,
                Task.builder()
                    .id(40L)
                    .title("feed_dog")
                    .priority(Priority.HIGH)
                    .deadline(LocalDateTime.of(2026, 8, 11, 18, 0))
                    .description("add tabs")
                    .build())
        );
    }

    @Test
    @BeforeEach
    @DisplayName("Create test data")
    public void createTestData(){
        inMemoryTaskServiceInitiallyEmptyValues = new InMemoryTaskService();
        assertTrue(inMemoryTaskServiceInitiallyEmptyValues.getAllTasks().isEmpty());

        Label workLabel = new Label(1L, "work", LabelColor.BLUE, false);
        labels.put(workLabel.getName(), workLabel);
        Label studyLabel = new Label(2L, "study", LabelColor.GREEN, false);
        labels.put(studyLabel.getName(), studyLabel);
        Label sportLabel = new Label(3L, "sport", LabelColor.RED, false);
        labels.put(sportLabel.getName(), sportLabel);
        Label personalLabel = new Label(4L, "personal", LabelColor.PINK, true);
        labels.put(personalLabel.getName(), personalLabel);

        Task workTask1 = Task.builder()
                .id(10L)
                .title("work_task_done")
                .deadline(LocalDateTime.of(2026, 8, 8, 9, 0))
                .priority(Priority.HIGH)
                .status(TaskStatus.DONE)
                .build();

        workTask1.addLabel(workLabel);
        tasks.put(workTask1.getId(), workTask1);

        Task workTask2 = Task.builder()
                .id(11L)
                .title("work_task_without_deadline")
                .deadline(LocalDateTime.of(2026, 8, 11, 10, 0))
                .priority(Priority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .build();

        workTask2.addLabel(workLabel);
        tasks.put(workTask2.getId(), workTask2);

        Task studyTask1 = Task.builder()
                .id(20L)
                .title("study_task_planned")
                .deadline(LocalDateTime.of(2026, 9, 10, 12, 0))
                .priority(Priority.MEDIUM)
                .status(TaskStatus.PLANNED)
                .build();

        studyTask1.addLabel(studyLabel);
        tasks.put(studyTask1.getId(), studyTask1);

        Task studyTask2 = Task.builder()
                .id(21L)
                .title("study_task_in_progress")
                .deadline(LocalDateTime.of(2026, 9, 11, 12, 0))
                .priority(Priority.HIGH)
                .status(TaskStatus.IN_PROGRESS)
                .build();

        studyTask2.addLabel(studyLabel);
        tasks.put(studyTask2.getId(), studyTask2);

        Task sportTask1 = Task.builder()
                .id(30L)
                .title("sport_task_daily")
                .build();

        sportTask1.addLabel(sportLabel);
        tasks.put(sportTask1.getId(), sportTask1);

        Task sportTask2 = Task.builder()
                .id(31L)
                .title("sport_task_challenge")
                .description("start running")
                .build();

        sportTask2.addLabel(sportLabel);
        tasks.put(sportTask2.getId(), sportTask2);

        Task personalTask1 = Task.builder()
                .id(40L)
                .title("feed_dog")
                .priority(Priority.HIGH)
                .deadline(LocalDateTime.of(2026, 8, 11, 18, 0))
                .description("add tabs")
                .build();

        personalTask1.addLabel(personalLabel);
        tasks.put(personalTask1.getId(), personalTask1);

        inMemoryTaskServiceFilledValues = new InMemoryTaskService(tasks);
    }

    @Test
    @DisplayName("Try to create InMemoryTaskService obj when give null as constructor param then throws NullPointer ex")
    public void whenCreateInMemoryTaskServiceWithNullInConstructorThenNullPointer() {
        assertThrows(NullPointerException.class,
                () -> {
                    new InMemoryTaskService(null);
                });
    }

    @Test
    @DisplayName("When .addTask(null) then throws NullPointer ex")
    public void whenAddNullTaskThenNullPointer() {
        assertThrows(NullPointerException.class,
                () -> {
                    inMemoryTaskServiceInitiallyEmptyValues.addTask(null);
                });
    }

    @Test
    @DisplayName("When .addTask([valid]) then contains true")
    public void whenAddValidTaskThenContainsTrue() {
        Task newValidTask = Task.builder()
                .id(5L)
                .title("valid_task")
                .build();
        inMemoryTaskServiceInitiallyEmptyValues.addTask(newValidTask);
        assertTrue(inMemoryTaskServiceInitiallyEmptyValues.getAllTasks().contains(newValidTask));
    }

    @Test
    @DisplayName(".changeStatus with valid data")
    public void whenChangeStatusValidThenCheckChangesTrue() {
        inMemoryTaskServiceFilledValues.changeTaskStatus(11L, TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, inMemoryTaskServiceFilledValues.findTaskById(11L).get().getStatus());
    }

    @Test
    @DisplayName(".changeStatus with null task id given then throws NullPointer")
    public void whenChangeStatusNullTaskIdThenCheckChangesTrue() {
        assertThrows(NullPointerException.class,
                () -> {
                    inMemoryTaskServiceFilledValues.changeTaskStatus(null, TaskStatus.DONE);
                });
    }

    @Test
    @DisplayName(".changeStatus with null status given then throws NullPointer")
    public void whenChangeStatusNullStatusThenCheckChangesTrue() {
        assertThrows(NullPointerException.class,
                () -> {
                    inMemoryTaskServiceFilledValues.changeTaskStatus(11L, null);
                });
    }

    @Test
    @DisplayName(".addTaskLabel with null task id given then throws NullPointer")
    public void whenAddLabelToTaskNullTaskIdThenNullPointer() {
        assertThrows(NullPointerException.class,
                () -> {
                    inMemoryTaskServiceFilledValues.addLabelToTask(
                            null,
                            new Label(3L, "simple_label", LabelColor.TRANSPARENT, false));
                });
    }

    @Test
    @DisplayName(".addTaskLabel with null label given then throws NullPointer")
    public void whenAddLabelToTaskNullLabelThenNullPointer() {
        assertThrows(NullPointerException.class,
                () -> {
                    inMemoryTaskServiceFilledValues.addLabelToTask(
                            30L,
                            null);
                });
    }

    @ParameterizedTest
    @DisplayName(".assTaskLabel valid value given then returns true")
    @MethodSource("newLabelProvider")
    public void whenAddTaskLabelValidValueThenReturnsTrue(Label label) {
        assertTrue(inMemoryTaskServiceFilledValues.addLabelToTask(
                30L,
                label
        ));
    }

    @Test
    @DisplayName(".assTaskLabel already added label given then returns false")
    public void whenAddTaskLabelAlreadyAddedThenReturnsFalse() {
        assertFalse(inMemoryTaskServiceFilledValues.addLabelToTask(
                30L,
                labels.get("sport")
        ));
    }

    @Test
    @DisplayName(".assTaskLabel given task id not contains in then returns false")
    public void whenAddTaskLabelNotContainsTaskIdThenReturnsFalse() {
        assertFalse(inMemoryTaskServiceFilledValues.addLabelToTask(
                99L,
                labels.get("sport")
        ));
    }

    @Test
    public void whenRemoveTaskByIdValidIdThenReturnsTrue() {
        assertTrue(inMemoryTaskServiceFilledValues.removeTaskById(40L));
    }

    @ParameterizedTest
    @NullSource
    public void whenRemoveTaskByIdNullUdThenNullPointer(Long id) {
        assertThrows(NullPointerException.class,
                () -> {
                    inMemoryTaskServiceFilledValues.removeTaskById(id);
                });
    }

    @Test
    public void whenRemoveTaskByIdNotContainsIdThenReturnsTrue() {
        assertFalse(inMemoryTaskServiceFilledValues.removeTaskById(1000L));
    }

    @ParameterizedTest
    @NullSource
    public void whenFindTaskByIdNullIdThenNullPointer(Long id) {
        assertThrows(NullPointerException.class,
                () -> {
            inMemoryTaskServiceFilledValues.findTaskById(id);
                });
    }

    @ParameterizedTest
    @MethodSource("taskProvider")
    public void whenFindTaskByIdValidThenReturnsCorrectTask(Long id, Task task) {
        assertEquals(task, inMemoryTaskServiceFilledValues.findTaskById(id).get());
    }

    @Test
    public void whenFindTaskByIdNotContainedIdThenReturnsCorrectTask() {
        assertFalse(inMemoryTaskServiceFilledValues.findTaskById(1415L).isPresent());
    }

}
