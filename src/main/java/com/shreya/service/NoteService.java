package com.shreya.service;

import com.shreya.model.Note;
import com.shreya.utils.FileManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NoteService {

    private List<Note> notes;

    public NoteService() {
        notes = FileManager.loadNotes();

        if (notes == null) {
            notes = new ArrayList<>();
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.add(note);
        FileManager.saveNotes(notes);
    }

    public void deleteNote(Note note) {
        notes.remove(note);
        FileManager.saveNotes(notes);
    }

    public void updateNote() {
        FileManager.saveNotes(notes);
    }

    public List<Note> searchNotes(String keyword) {

        keyword = keyword.toLowerCase();

        List<Note> result = new ArrayList<>();

        for (Note note : notes) {

            if (note.getTitle().toLowerCase().contains(keyword)
                    || note.getContent().toLowerCase().contains(keyword)
                    || note.getCategory().toLowerCase().contains(keyword)) {

                result.add(note);

            }

        }

        return result;
    }

    public void sortByNewest() {

        notes.sort((a, b) ->
                b.getDateTime().compareTo(a.getDateTime()));

    }

    public void sortByOldest() {

        notes.sort(Comparator.comparing(Note::getDateTime));

    }

    public void sortByCategory(String category) {

        notes.sort((a, b) -> {

            if (a.getCategory().equals(category)
                    && !b.getCategory().equals(category))
                return -1;

            if (!a.getCategory().equals(category)
                    && b.getCategory().equals(category))
                return 1;

            return 0;

        });

    }

    public int getPinnedCount() {

        int count = 0;

        for (Note note : notes) {

            if (note.isPinned()) {
                count++;
            }

        }

        return count;
    }

    public int getFavoriteCount() {

        int count = 0;

        for (Note note : notes) {

            if (note.isFavorite()) {
                count++;
            }

        }

        return count;
    }

    public int getCategoryCount(String category) {

        int count = 0;

        for (Note note : notes) {

            if (note.getCategory().equals(category)) {
                count++;
            }

        }

        return count;
    }

}