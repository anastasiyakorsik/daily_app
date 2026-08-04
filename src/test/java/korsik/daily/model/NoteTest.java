package korsik.daily.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    @Test
    @DisplayName("Create Valid Note")
    public void createValidNote(){
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        assertEquals(1L, note.getId());
        assertEquals("test_title", note.getTitle());
        assertEquals("test_content", note.getContent());
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), note.getCreatedAt());
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), note.getUpdateAt());
    }

    @Test
    @DisplayName("Create Note with null id")
    public void createNoteNullId(){

        assertThrows(NullPointerException.class,
                () -> {
                    Note.builder()
                            .id(null)
                            .title("test_title")
                            .content("test_content")
                            .build();
                });
    }

    @Test
    @DisplayName("Create Note with null title")
    public void createNoteNullTitle(){

        assertThrows(NullPointerException.class,
                () -> {
                    Note.builder()
                            .id(1L)
                            .title(null)
                            .content("test_content")
                            .build();
                });
    }

    @Test
    @DisplayName("Create Note with empty title")
    public void createNoteEmptyTitle(){

        assertThrows(IllegalArgumentException.class,
                () -> {
                    Note.builder()
                            .id(1L)
                            .title("")
                            .content("test_content")
                            .build();
                });
    }

    @Test
    @DisplayName("Create Note with blank title")
    public void createNoteBlankTitle(){

        assertThrows(IllegalArgumentException.class,
                () -> {
                    Note.builder()
                            .id(1L)
                            .title("   ")
                            .content("test_content")
                            .build();
                });
    }

    @Test
    @DisplayName("Create Note with more than max length title")
    public void createNoteMoreThanMaxLengthTitle(){

        assertThrows(IllegalArgumentException.class,
                () -> {
                    Note.builder()
                            .id(1L)
                            .title("ffffffffffffff")
                            .content("test_content")
                            .build();
                });
    }

    @Test
    @DisplayName("Create Note with null content")
    public void createNoteNullContent(){

        assertThrows(IllegalArgumentException.class,
                () -> {
                    Note.builder()
                            .id(1L)
                            .title("test")
                            .content(null)
                            .build();
                });
    }

    @Test
    @DisplayName("Add valid label to Note")
    public void whenAddLabelValidThenReturnsTrue() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);

        assertTrue(note.addLabel(label));
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), note.getUpdateAt());
        assertTrue(note.getLabels().stream().anyMatch(label1 -> label1.equals(label)));
    }

    @Test
    @DisplayName("Add null label to Note")
    public void whenAddLabelNullThenThrowsNullPointerException() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        assertThrows(NullPointerException.class,
                () -> {
                    note.addLabel(null);
                });
    }

    @Test
    @DisplayName("Add already added label to Note")
    public void whenAddLabelAlreadyAddedThenReturnsFalse() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label1 = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);
        Label label2 = new Label(1L, "test_name", LabelColor.RED, true);

        note.addLabel(label1);
        assertFalse(note.addLabel(label2));
    }

    @Test
    @DisplayName("Remove valid label from Note")
    public void whenRemoveLabelValidThenReturnsTrue() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label1 = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);

        note.addLabel(label1);
        assertTrue(note.removeLabel(label1));
    }

    @Test
    @DisplayName("Remove null label from Note")
    public void whenRemoveLabelNullThenThrowsNullPointerException() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        assertThrows(NullPointerException.class,
                () -> {
                    note.removeLabel(null);
                });
    }

    @Test
    @DisplayName("Remove never added label from Note")
    public void whenRemoveLabelNeverAddedThenReturnsFalse() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label1 = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);
        Label label2 = new Label(1L, "test_name_2", LabelColor.RED, true);

        note.addLabel(label1);
        assertFalse(note.removeLabel(label2));
    }

    @Test
    @DisplayName("Expect true when give valid label name into containsLabel method")
    public void whenContainsLabelValidThenReturnsTrue() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);
        note.addLabel(label);

        assertTrue(note.containsLabel("test_name"));
    }

    @Test
    @DisplayName("Throws exception when give null label name into containsLabel method")
    public void whenContainsLabelNullThenThrowsNullPointerException() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);
        note.addLabel(label);

        assertThrows(NullPointerException.class,
                () -> {
                    note.containsLabel(null);
                });
    }

    @Test
    @DisplayName("Throws exception when give empty label name into containsLabel method")
    public void whenContainsLabelEmptyThenThrowsIllegalArgumentException() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);
        note.addLabel(label);

        assertThrows(IllegalArgumentException.class,
                () -> {
                    note.containsLabel("");
                });
    }

    @Test
    @DisplayName("Throws exception when give blank label name into containsLabel method")
    public void whenContainsLabelBlankThenThrowsNullPointerException() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);
        note.addLabel(label);

        assertThrows(IllegalArgumentException.class,
                () -> {
                    note.containsLabel("    ");
                });
    }

    @Test
    @DisplayName("Expect false when give never added label name into containsLabel method")
    public void whenContainsLabelNeverAddedThenReturnsFalse() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        Label label = new Label(1L, "test_name", LabelColor.TRANSPARENT, true);
        note.addLabel(label);

        assertFalse(note.containsLabel("test_name_0"));
    }

    @Test
    @DisplayName("Expect false when give label name into containsLabel method and labels are empty")
    public void whenContainsLabelAndEmptyLabelsThenReturnsFalse() {
        Note note = Note.builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .build();

        assertFalse(note.containsLabel("test_name_0"));
    }
}
