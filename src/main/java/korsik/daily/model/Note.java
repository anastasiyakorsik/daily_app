package korsik.daily.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Note {

    private Long id;
    private String title;
    private String content;
    private List<Tag> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private NoteLinkType noteLinkType;
    private Long noteLinkId;
    private HashMap<Long, NoteLinkType> noteLinks;

    private static final int MAX_TITLE_LENGTH = 120;

    public Note(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        noteLinks = new HashMap<>();
    }

    public Note(Long id, String title, String content, NoteLinkType noteLinkType, Long noteLinkId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.noteLinkType = noteLinkType;
        this.noteLinkId = noteLinkId;
        noteLinks = new HashMap<>();
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

    public void addNoteLink(Long newNoteLinkId, NoteLinkType newNoteLinkType){
        if (!this.id.equals(newNoteLinkId)){
            if (!noteLinks.containsKey(newNoteLinkId)){
                noteLinks.put(newNoteLinkId, newNoteLinkType);
            }
            else{
                throw new RuntimeException("Note is already linked to given note");
            }
        }
        else{
            throw new RuntimeException("Note can not be linked to itself");
        }
    }

    public HashMap<Long, NoteLinkType> getNoteLinks() {return noteLinks;}

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
