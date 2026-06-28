package korsik.daily.model;

import javax.print.attribute.standard.JobKOctets;
import javax.swing.plaf.ButtonUI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Note {

    private static final int MAX_TITLE_LENGTH = 120;
    private static AtomicLong currentId = new AtomicLong(0L);

    private final Long id;
    private final String title;
    private String content;
    private Set<Label> labels;
    private final LocalDateTime createdAt;
    private LocalDateTime updateAt;
    // private Map<Long, NoteLinkType> noteLinks;

    public Note(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;

        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
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
        return labels;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
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
        if (labels == null || labelName.isEmpty()){
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
        this.updateAt = LocalDateTime.now();
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

    public static class Builder {
        private Long id;
        private String title;
        private String content;

        public Builder id(Long id){
            this.id = Objects.requireNonNull(id, "id must not be null");
            return this;
        }

        public Builder title(String title){
            if (title == null) {
                throw new NullPointerException("Note title can not be null.");
            }

            if (title.isBlank()) {
                throw new IllegalArgumentException("Note title can not be empty or contains only spaces.");
            }

            if (title.length() > MAX_TITLE_LENGTH) {
                throw new IllegalArgumentException("Note title. Please, make it shorter");
            }

            this.title = title;
            return this;
        }

        public Builder content(String content){
            this.content = Objects.requireNonNull(content, "content must not be null");
            return this;
        }

        public Note build(){
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
                ", updateAt=" + updateAt +
                '}';
    }
}
