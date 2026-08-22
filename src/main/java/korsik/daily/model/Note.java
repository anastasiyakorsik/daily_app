package korsik.daily.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Note {

    private static final int MAX_TITLE_LENGTH = 100;
    //private static AtomicLong currentId = new AtomicLong(0L);

    private final Long id;
    private final String title;
    private String content;
    private Set<Label> labels;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // private Map<Long, NoteLinkType> noteLinks;

    public Note(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;

        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.labels = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Set<Label> getLabels() {
        if (labels == null){
            return Set.of();
        }
        return Set.copyOf(labels);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updatedAt;
    }

    public boolean addLabel(Label label) {
        boolean add = labels.add(Objects.requireNonNull(label, "label must not be null"));
        if (add){
            setUpdateAt();
        }
        return add;
    }

    public boolean removeLabel(Label label) {
        boolean remove = labels.remove(Objects.requireNonNull(label, "label must not be null"));
        if (remove){
            setUpdateAt();
        }
        return remove;
    }

    public boolean containsLabel(String labelName) {
        if (labelName == null){
            throw new NullPointerException("label name must not be null");
        }
        if (labelName.isBlank()){
            throw new IllegalArgumentException("label name must be not empty or contains only spaces");
        }
        if (labels == null){
            return false;
        }
        for (Label label : labels){
            if (label.getName().equals(labelName)){
                return true;
            }
        }
        return false;
    }

    public void setUpdateAt() {
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    //todo
//    public void addNoteLink(Long newNoteLinkId, NoteLinkType newNoteLinkType) {
//        if (!this.id.equals(newNoteLinkId)) {
//            if (!noteLinks.containsKey(newNoteLinkId)) {
//                noteLinks.put(newNoteLinkId, newNoteLinkType);
//            } else {
//                throw new RuntimeException("Note is already linked to given note");
//            }
//        } else {
//            throw new RuntimeException("Note can not be linked to itself");
//        }
//    }

//    public Map<Long, NoteLinkType> getNoteLinks() {
//        return noteLinks;
//    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder content(String content){
            this.content = content;
            return this;
        }

        public Note build(){
            Objects.requireNonNull(id, "id must not be null");
            if (title == null) {
                throw new NullPointerException("Note title can not be null.");
            }

            if (title.isBlank()) {
                throw new IllegalArgumentException("Note title can not be empty or contains only spaces.");
            }

            if (title.length() > MAX_TITLE_LENGTH) {
                throw new IllegalArgumentException("Note title. Please, make it shorter");
            }
            Objects.requireNonNull(content, "content must not be null");

            return new Note(this);
        }
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + labels +
                ", createdAt=" + createdAt +
                ", updateAt=" + updatedAt +
                '}';
    }
}
