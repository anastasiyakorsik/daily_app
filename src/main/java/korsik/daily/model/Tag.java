package korsik.daily.model;

import java.util.Locale;
import java.util.Objects;

public class Tag {

    private final Long id;
    private final String name;
    private String color;
    private final boolean custom;

    private static final int MAX_TAG_NAME_LENGTH = 120;

    public Tag(Long id, String name, String color, boolean custom) {
        this.id = id;
        this.name = normalizeAndValidateName(name);
        this.color = color;
        this.custom = custom;
    }

    private String normalizeAndValidateName(String name) {

        try {
            if (name == null){
                throw new IllegalArgumentException("Tag name can not be null.");
            }

            if (name.isBlank()){
                throw new IllegalArgumentException("Tag name can not be empty or contains only spaces.");
            }

            if (name.length() > MAX_TAG_NAME_LENGTH){
                throw new IllegalArgumentException("Tag name is too big. Please, make it shorter");
            }

            name = name.trim().toLowerCase();
            return name;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isCustom() {
        return custom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag tag)) return false;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", custom=" + custom +
                '}';
    }
}
