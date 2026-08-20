package korsik.daily.model;

import java.util.Objects;

//todo подумать реально ли нужен id
public class Label {

    private final Long id;
    private final String name;
    private LabelColor color;
    private final boolean custom;

    private static final int MAX_LABEL_NAME_LENGTH = 120;

    public Label(Long id, String name, LabelColor color, boolean custom) {
        this.id = Objects.requireNonNull(id, "id must be set");
        this.name = normalizeAndValidateName(name);
        this.color = color == null ? LabelColor.TRANSPARENT : color;
        this.custom = custom;
    }

    private String normalizeAndValidateName(String name) {

            if (name == null){
                throw new IllegalArgumentException("Label name can not be null.");
            }

            if (name.isBlank()){
                throw new IllegalArgumentException("Label name can not be empty or contains only spaces.");
            }

            if (name.length() > MAX_LABEL_NAME_LENGTH){
                throw new IllegalArgumentException(String.format("Label name is too big. Please, make it shorter than %d.", MAX_LABEL_NAME_LENGTH));
            }

            name = name.trim().toLowerCase();
            return name;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LabelColor getColor() {
        return color;
    }

    public void setColor(LabelColor color) {

        this.color = Objects.requireNonNull(color, "color must be set");
    }

    public boolean isCustom() {
        return custom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label label)) return false;
        return Objects.equals(name, label.name);
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
