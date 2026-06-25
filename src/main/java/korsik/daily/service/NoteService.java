package korsik.daily.service;

import korsik.daily.model.Note;
import korsik.daily.model.NoteLinkType;
import korsik.daily.model.Tag;
import korsik.daily.model.Task;
import korsik.daily.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NoteService {
    private Map<Long, Note> notes = new HashMap<>();

    public Map<Long, Note> getAllNotes(){
        return notes;
    }

    public void addNote(Note note){
        try {
            notes.put(note.getId(), note);
            System.out.println("Given note was successfully saved");
        } catch (Exception e) {
            System.out.println("Failed to save note");
            throw new RuntimeException(e);
        }
    }

    //todo: think about check containing
    public void addTagToNote(Note note, Tag tag){
        if (!note.getTags().contains(tag)){
            note.addTag(tag);
        }
        else{
            throw new RuntimeException("Given tag is already applied to note");
        }
    }

    public void deleteTagFromNote(Note note, Tag tag){
        if (note.getTags().contains(tag)){
            note.getTags().remove(tag);
        }
        else{
            throw new RuntimeException("Given tag was not applied to note");
        }
    }

    public void removeNoteById(Long noteId){
        try {
            notes.remove(noteId);
            System.out.println(String.format("Note with Id: %d is not found in saved notes", noteId));
        } catch (Exception e) {
            System.out.println("Failed to remove provided note");
            throw new RuntimeException(e);
        }
    }

    public Note findNoteById(Long noteId){
        if (notes.keySet().contains(noteId)){
            return notes.get(noteId);
        }
        System.out.println(String.format("Note with Id: %d is not found in saved notes", noteId));
        return null;
    }

    public List<Note> findNotesByTagName(String tagName){
        List<Note> notesWithGivenTag = new ArrayList<>();
        for (Note note : notes.values()){
            Set<Tag> noteTags = note.getTags();
            for (Tag tag : noteTags){
                if (tag.getName().equals(tagName)){
                    notesWithGivenTag.add(note);
                }
            }
        }
        return notesWithGivenTag;
    }

    public List<Note> findLinkedWithObjNotes(Long linkId, NoteLinkType linkType){
        List<Note> linkedNotes = new ArrayList<>();

        for (Note note : notes.values()){
            if (note.getNoteLinks().containsKey(linkId) && note.getNoteLinks().containsValue(linkType)){
                linkedNotes.add(note);
            }
        }

        return linkedNotes;
    }

    public List<Note> findNotesByTitlePart(String titlePart){
        List<Note> linkedNotes = new ArrayList<>();

        for (Note note : notes.values()){
            if (note.getTitle().contains(titlePart)){
                linkedNotes.add(note);
            }
        }

        return linkedNotes;
    }

    public List<Note> findNotesByContentPart(String contentPart){
        List<Note> linkedNotes = new ArrayList<>();

        for (Note note : notes.values()){
            if (note.getContent().contains(contentPart)){
                linkedNotes.add(note);
            }
        }

        return linkedNotes;
    }

}
