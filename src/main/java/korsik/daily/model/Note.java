package korsik.daily.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Note {

    private Long id;
    private String title;
    private String content;
    private List<Tag> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private NoteLinkType noteLinkType;
    private Long noteLinkId;

    private static final int MAX_TITLE_LENGTH = 120;

    public Note(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public Note(Long id, String title, String content, NoteLinkType noteLinkType, Long noteLinkId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.noteLinkType = noteLinkType;
        this.noteLinkId = noteLinkId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        try {
            if (title == null){
                throw new Exception("Note title can not be null.");
            }

            if (title.isBlank()){
                throw new Exception("Note title can not be empty or contains only spaces.");
            }

            if (title.length() > MAX_TITLE_LENGTH){
                throw new Exception("Note title. Please, make it shorter");
            }

            this.title = title.trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt() {
        this.updateAt = LocalDateTime.now();
    }

    public NoteLinkType getNoteLinkType() {
        return noteLinkType;
    }

    public void setNoteLinkType(NoteLinkType noteLinkType) {
        this.noteLinkType = noteLinkType;
    }

    public Long getNoteLinkId() {
        return noteLinkId;
    }

    public void setNoteLinkId(Long noteLinkId) {
        if (!this.id.equals(noteLinkId)){
            this.noteLinkId = noteLinkId;
        }
        else{
            throw new RuntimeException("Note can not be linked to itself");
        }
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
