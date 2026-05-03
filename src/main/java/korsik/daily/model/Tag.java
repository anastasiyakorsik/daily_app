package korsik.daily.model;

import java.util.Locale;
import java.util.Objects;

public class Tag {

    private Long id;
    private String name;
    private String color;
    private boolean custom;

    private static final int MAX_TAG_NAME_LENGTH = 120;

    public Tag(Long id, String name, String color, boolean custom) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.custom = custom;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        try {
            if (name == null){
                throw new Exception("Tag name can not be null.");
            }

            if (name.isBlank()){
                throw new Exception("Tag name can not be empty or contains only spaces.");
            }

            if (name.length() > MAX_TAG_NAME_LENGTH){
                throw new Exception("Tag name is too big. Please, make it shorter");
            }

            name = name.trim().toLowerCase();
            this.name = name;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        if (!(o instanceof Tag tag)) return false;
        return Objects.equals(id, tag.id) || (custom == tag.custom && Objects.equals(name.toLowerCase(), tag.name.toLowerCase()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, custom);
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
