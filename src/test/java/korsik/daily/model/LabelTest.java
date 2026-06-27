package korsik.daily.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LabelTest {

    // Стиль тестов Arrange / Act / Assert

    // validation

    @Test
    @DisplayName("Create valid Label")
    void createCorrectLabel(){
        Long id = 1L;
        String tag_name = "tag_name";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        Label test_label = new Label(id, tag_name, tag_color, is_tag_custom);

        assertEquals(id, test_label.getId());
        assertEquals(tag_name, test_label.getName());
        assertEquals(tag_color, test_label.getColor());
        assertEquals(is_tag_custom, test_label.isCustom());
    }

    @Test
    @DisplayName("Create Label with null name")
    void createNullNameLabel(){
        assertThrows(IllegalArgumentException.class,
                () -> new Label(1L, null, "tag_color", true),
                "Label name can not be null.");
    }

    @Test
    @DisplayName("Create Label with empty name")
    void createEmptyNameLabel(){
        assertThrows(IllegalArgumentException.class,
                () -> new Label(1L, "", "tag_color", true),
                "Label name can not be empty or contains only spaces.");
    }

    @Test
    @DisplayName("Create Label with name of spaces")
    void createNameOfSpacesLabel(){
        assertThrows(IllegalArgumentException.class,
                () -> new Label(1L, "  ", "tag_color", true),
                "Label name can not be empty or contains only spaces.");
    }

    @Test
    @DisplayName("Create Label with too long name")
    void createExtraLongNameLabel(){
        assertThrows(IllegalArgumentException.class,
                () -> new Label(1L,
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        "tag_color", true),
                "Label name is too big. Please, make it shorter.");
    }

    // normalization

    @Test
    @DisplayName("Create Label with upper case name")
    void createUpperCaseNameLabel(){
        Long id = 1L;
        String tag_name = "TEST";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        Label test_label = new Label(id, tag_name, tag_color, is_tag_custom);
        assertEquals("test", test_label.getName());
    }

    @Test
    @DisplayName("Create Label with name of different letter case")
    void createDifferentLetterCaseNameLabel(){
        Long id = 1L;
        String tag_name = "TeSt";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        Label test_label = new Label(id, tag_name, tag_color, is_tag_custom);
        assertEquals("test", test_label.getName());
    }

    // Equality / hashCode

    @Test
    @DisplayName("Labels with same names equals")
    void whenSameNameThenEqualsTrue(){
        // Arrange
        Label tag_Entity_1 = new Label(1L, "work", "blue", false);
        Label tag_Entity_2 = new Label(2L, " Work", "red", false);

        //Assert
        assertEquals(tag_Entity_1, tag_Entity_2);

    }

    @Test
    @DisplayName("Labels with different names does not equal")
    void whenDifferentNameThenEqualsFalse(){
        // Arrange
        Label tag_Entity_1 = new Label(1L, "work", "blue", false);
        Label tag_Entity_2 = new Label(2L, " _Work", "red", false);

        //Assert
        assertNotEquals(tag_Entity_1, tag_Entity_2);

    }

    @Test
    @DisplayName("Same Labels have equal hashcode")
    void whenSameNameThenSameHashCode(){
        // Arrange
        Label tag_Entity_1 = new Label(1L, "work", "blue", false);
        Label tag_Entity_2 = new Label(2L, " Work", "red", false);

        //Assert
        assertEquals(tag_Entity_1.hashCode(), tag_Entity_2.hashCode());

    }

    // Collections

    @Test
    @DisplayName("HashSet<Label> does not add logically equal Labels")
    void whenTagsHaveTheSameNameThenHashsetDoesNotCollectBoth(){
        // Arrange
        Label first = new Label(1L, "work", "blue", true);
        Label second = new Label(2L, " WORK ", "red", false);
        Set<Label> tagEntities = new HashSet<>();

        // Act
        tagEntities.add(first);
        tagEntities.add(second);

        // Assert
        assertEquals(1, tagEntities.size());

    }

    @Test
    @DisplayName("contains() for HashSet is true for equal Label")
    void whenTagsHaveTheSameNameThenHashsetContainsIsTrueForEqualOne(){
        // Arrange
        Label first = new Label(1L, "work", "blue", true);
        Label second = new Label(2L, " WORK ", "red", false);
        Set<Label> tagEntities = new HashSet<>();

        // Act
        tagEntities.add(first);

        // Assert
        assertTrue(tagEntities.contains(second));

    }
}
