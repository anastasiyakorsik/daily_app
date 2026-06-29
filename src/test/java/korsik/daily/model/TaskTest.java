package korsik.daily.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;

import java.time.LocalDateTime;

public class TaskTest {

    @Test
    @DisplayName("Create valid task")
    @Tag("creation-validation")
    void correctTaskCanBeCreated() {
        Task test_task = Task.builder()
                .id(1L)
                .title("test_title")
                .build();


        assertEquals(1L, test_task.getId());
        assertEquals("test_title", test_task.getTitle());
        assertEquals(TaskStatus.PLANNED, test_task.getStatus());
        assertEquals(Priority.LOW, test_task.getPriority());
    }

    @Test
    @DisplayName("Create task with null name")
    @Tag("creation-validation")
    void whenNullTitleThenException() {
        assertThrows(IllegalArgumentException.class,
                () -> Task.builder()
                        .id(1L)
                        .title(null)
                        .build(),
                "Title of task can not be null.");
    }

    @Test
    @DisplayName("Create tasks with blank name")
    @Tag("creation-validation")
    void whenBlankTitleThenException() {
        assertThrows(IllegalArgumentException.class,
                () -> Task.builder()
                        .id(1L)
                        .title("")
                        .build(),
                "Title of task can not be empty or contains only spaces.");

        assertThrows(IllegalArgumentException.class,
                () -> Task.builder()
                        .id(1L)
                        .title("   ")
                        .build(),
                "Title of task can not be empty or contains only spaces.");

    }

    @Test
    @DisplayName("Create task with too long name")
    @Tag("creation-validation")
    void whenTooLongTitleThenException() {
        assertThrows(IllegalArgumentException.class,
                () -> Task.builder()
                        .id(1L)
                        .title("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .build(),
                "Title is too big. Please, make it shorter.");

    }


    @Test
    @DisplayName("Add label to task")
    @Tag("Labels")
    void addLabelToTask(){
        Task test_task = Task.builder()
                .id(1L)
                .title("test_task")
                .build();

        Label test_label = new Label(2L, "test_label", null, true);
        test_task.addLabel(test_label);

        assertTrue(test_task.containsLabel(test_label.getName()));
    }

    @Test
    @DisplayName("Can not add the same Label to task")
    @Tag("Labels")
    void whenEqualTagsThenDuplicateNotAddedToTask(){
        Task test_task = Task.builder()
                .id(1L)
                .title("test_task")
                .build();

        Label test_tag_1 = new Label(2L, "test_tag", null, true);
        Label test_tag_2 = new Label(3L, "Test_Tag", null, true);
        test_task.addLabel(test_tag_1);
        test_task.addLabel(test_tag_2);

        assertEquals(1, test_task.getLabels().size());
    }

    // deadline / overdue

    @Test
    @DisplayName("Task with future deadline is not overdue")
    @Tag("deadline-overdue")
    void whenDeadlineInFutureThenTaskIsNotOverdue(){
        Task test_task = Task.builder()
                .id(1L)
                .title("test_title")
                .build();
        test_task.setDeadline(LocalDateTime.of(2026, 7, 26, 11, 20));

        assertFalse(test_task.isOverdue(LocalDateTime.now()));
    }

    @Test
    @DisplayName("Task with past deadline and TaskStatus.PLANNED is overdue")
    @Tag("deadline-overdue")
    void whenDeadlineInPastAndTaskStatusIsPlannedThenTaskIsOverdue(){
        Task test_task = Task.builder()
                .id(1L)
                .title("test_title")
                .build();
        test_task.setDeadline(LocalDateTime.of(2026, 5, 26, 11, 20));

        assertTrue(test_task.isOverdue(LocalDateTime.now()));
    }

    @Test
    @DisplayName("Task with past deadline and TaskStatus.DONE/CANCELLED is not overdue")
    @Tag("deadline-overdue")
    void whenDeadlineInPastAndTaskStatusIsDoneOrCancelledThenTaskIsNotOverdue(){
        Task test_task1 = Task.builder()
                .id(1L)
                .title("test_title")
                .build();
        test_task1.changeStatus(TaskStatus.DONE);
        test_task1.setDeadline(LocalDateTime.of(2026, 5, 26, 11, 20));

        Task test_task2 = Task.builder()
                .id(1L)
                .title("test_title")
                .build();
        test_task2.changeStatus(TaskStatus.CANCELLED);
        test_task2.setDeadline(LocalDateTime.of(2026, 4, 26, 11, 20));

        assertFalse(test_task1.isOverdue(LocalDateTime.now()));
        assertFalse(test_task2.isOverdue(LocalDateTime.now()));
    }

    @Test
    @DisplayName("Task without deadline is not overdue")
    @Tag("deadline-overdue")
    void whenNoDeadlineThenTaskIsNotOverdue(){
        Task test_task = Task.builder()
                .id(1L)
                .title("test_title")
                .build();

        assertFalse(test_task.isOverdue(LocalDateTime.now()));
    }


}
