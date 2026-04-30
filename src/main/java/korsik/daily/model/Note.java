package korsik.daily.model;

import java.util.Date;
import java.util.List;

public class Note {

    private Long id;
    private String title;
    private String content;
    private List<Tag> tags;
    private Date createdAt;
    private Date updateAt;
    private NoteLinkType noteLinkType;
    private Long noteLinkId;

    public Note(Long id, String title, String content, List<Tag> tags, Date createdAt, Date updateAt, NoteLinkType noteLinkType, Long noteLinkId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.noteLinkType = noteLinkType;
        this.noteLinkId = noteLinkId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
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
        this.noteLinkId = noteLinkId;
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
