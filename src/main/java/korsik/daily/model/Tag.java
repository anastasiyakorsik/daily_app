package korsik.daily.model;

import java.util.Objects;

public class Tag {

    private Long id;
    private String name;
    private String color;
    private boolean custom;

    public Tag(Long id, String name, String color, boolean custom) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.custom = custom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tag tag)) return false;
        return custom == tag.custom && Objects.equals(id, tag.id) && Objects.equals(name, tag.name) && Objects.equals(color, tag.color);
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
