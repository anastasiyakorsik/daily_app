package korsik.daily.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TagTest {

    /*
    valid creation
    invalid null name
    invalid blank name
    invalid too long name
    normalization
    equals
    hashCode
    HashSet deduplication
     */

    // Стиль тестов Arrange / Act / Assert


    // validation

    @Test
    @DisplayName("Создание тега с валидным именем")
    void createCorrectTag(){
        Long id = 1L;
        String tag_name = "tag_name";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        Tag test_tag = new Tag(id, tag_name, tag_color, is_tag_custom);

        assertEquals(id, test_tag.getId());
        assertEquals(tag_name, test_tag.getName());
        assertEquals(tag_color, test_tag.getColor());
        assertEquals(is_tag_custom, test_tag.isCustom());
    }

    @Test
    @DisplayName("Создание тега с именем null")
    void createNullNameTag(){
        Long id = 1L;
        String tag_name = null;
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        try {
            Tag test_tag = new Tag(id, tag_name, tag_color, is_tag_custom);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("Создание тега с пустным именем")
    void createEmptyNameTag(){
        Long id = 1L;
        String tag_name = "";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        try {
            Tag test_tag = new Tag(id, tag_name, tag_color, is_tag_custom);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("Создание тега с именем, состоящим только из пробелов")
    void createNameOfSpacesTag(){
        Long id = 1L;
        String tag_name = "                    ";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        try {
            Tag test_tag = new Tag(id, tag_name, tag_color, is_tag_custom);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("Создание тега с именем, длинне максимальной длины")
    void createExtraLongNameTag(){
        Long id = 1L;
        String tag_name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        try {
            Tag test_tag = new Tag(id, tag_name, tag_color, is_tag_custom);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    // normalization

    @Test
    @DisplayName("Создание тега с именем, написанным в верхнем регистре")
    void createUpperCaseNameTag(){
        Long id = 1L;
        String tag_name = "TEST";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        Tag test_tag = new Tag(id, tag_name, tag_color, is_tag_custom);
        assertEquals("test", test_tag.getName());
    }

    @Test
    @DisplayName("Создание тега с именем, написанным с буквами в разном регистре")
    void createDifferentLetterCaseNameTag(){
        Long id = 1L;
        String tag_name = "TeSt";
        String tag_color = "tag_color";
        boolean is_tag_custom = true;

        Tag test_tag = new Tag(id, tag_name, tag_color, is_tag_custom);
        assertEquals("test", test_tag.getName());
    }

    // Equality / hashCode

    @Test
    @DisplayName("Тэги с одинаковыми именами равны")
    void whenSameNameThenEqualsTrue(){
        // Arrange
        Tag tag_1 = new Tag(1L, "work", "blue", false);
        Tag tag_2 = new Tag(2L, " Work", "red", false);

        //Assert
        assertEquals(tag_1, tag_2);

    }

    @Test
    @DisplayName("Тэги с разными именами не равны")
    void whenDifferentNameThenEqualsFalse(){
        // Arrange
        Tag tag_1 = new Tag(1L, "work", "blue", false);
        Tag tag_2 = new Tag(2L, " _Work", "red", false);

        //Assert
        if (tag_1.equals(tag_2)){
            assert false;
        }
        else assert true;

    }

    @Test
    @DisplayName("У равных тэгов совпадает hashCode")
    void whenSameNameThenSameHashCode(){
        // Arrange
        Tag tag_1 = new Tag(1L, "work", "blue", false);
        Tag tag_2 = new Tag(2L, " Work", "red", false);

        //Assert
        assertEquals(tag_1.hashCode(), tag_2.hashCode());

    }

    // Collections

    @Test
    @DisplayName("HashSet<Tag> не хранит два логически одинаковых тега.")
    void whenTagsHaveTheSameNameThenHashsetDoesNotCollectBoth(){
        // Arrange
        Tag first = new Tag(1L, "work", "blue", true);
        Tag second = new Tag(2L, " WORK ", "red", false);
        Set<Tag> tags = new HashSet<>();

        // Act
        tags.add(first);
        tags.add(second);

        // Assert
        assertEquals(1, tags.size());

    }

    @Test
    @DisplayName("contains() на HashSet работает для эквивалентного тега.")
    void whenTagsHaveTheSameNameThenHashsetContainsWorkWithBoth(){
        // Arrange
        Tag first = new Tag(1L, "work", "blue", true);
        Tag second = new Tag(2L, " WORK ", "red", false);
        Set<Tag> tags = new HashSet<>();

        // Act
        tags.add(first);

        // Assert
        if (tags.contains(second)) { assert true;} else assert false;

    }
}
