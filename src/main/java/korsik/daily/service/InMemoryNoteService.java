package korsik.daily.service;

import korsik.daily.model.Expense;
import korsik.daily.model.Note;
import korsik.daily.model.NoteLinkType;
import korsik.daily.model.Label;
import korsik.daily.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class InMemoryNoteService {

    private final Map<Long, Note> notes;

    public InMemoryNoteService(Map<Long, Note> notes) {
        this.notes = Objects.requireNonNull(notes, "notes may not be null");
    }

    public InMemoryNoteService() {
        this(new HashMap<>());
    }

    public List<Note> getAllNotes(){
        return List.copyOf(notes.values());
    }

    public void addNote(Note note){

        if (notes.containsKey(note.getId())) {
            throw new IllegalArgumentException(String.format("Note with id %d is already added", note.getId()));
        }

        notes.put(Objects.requireNonNull(note, "note must not be null").getId(),
                note);
        //System.out.println("Given note was successfully saved");
    }

    public void updateNote(Note note){
        notes.put(Objects.requireNonNull(note.getId()), note);
    }

    public boolean addLabelToNote(Long noteId, Label label){

        if (notes.containsKey(Objects.requireNonNull(noteId, "noteId must be set"))) {
            return notes.get(noteId).addLabel(Objects.requireNonNull(label, "label must be set"));
        }
        return false;
    }

    public boolean deleteLabelFromNote(Long noteId, Label label){
        if (notes.containsKey(Objects.requireNonNull(noteId, "noteId must be set"))) {
            return notes.get(noteId).removeLabel(Objects.requireNonNull(label, "label must be set"));
        }
        return false;
    }

    public boolean removeNoteById(Long noteId){
        if (notes.containsKey(Objects.requireNonNull(noteId, "noteId must be set"))) {
            notes.remove(noteId);
            return true;
        }
        return false;
    }

    public Optional<Note> findNoteById(Long noteId){
        return Optional.ofNullable(notes.get(Objects.requireNonNull(noteId, "noteId must be set")));
    }

    public List<Note> findNotesByLabelName(String labelName) {

        String normalizedLabelName = Objects.requireNonNull(labelName, "noteId must be set").trim().toLowerCase();

        return notes.values().stream()
                .filter(note -> note.getLabels().stream()
                        .anyMatch(label -> label.getName().equals(normalizedLabelName)))
                .toList();
    }

    //todo add nullability check
    // todo try stream api
//    public List<Note> findLinkedWithObjNotes(Long linkId, NoteLinkType linkType){
//        List<Note> linkedNotes = new ArrayList<>();
//
//        for (Note note : notes.values()){
//            if (note.getNoteLinks().containsKey(linkId) && note.getNoteLinks().containsValue(linkType)){
//                linkedNotes.add(note);
//            }
//        }
//
//        return linkedNotes;
//    }

    public List<Note> findNotesByTitlePart(String titlePart){
        Objects.requireNonNull(titlePart, "titlePart must be set");

        return notes.values().stream()
                .filter(note-> note.getTitle().contains(titlePart))
                .toList();
    }

    public List<Note> findNotesByContentPart(String contentPart){
        Objects.requireNonNull(contentPart, "contentPart must be set");

        return notes.values().stream()
                .filter(note-> note.getContent().contains(contentPart))
                .toList();
    }

}
