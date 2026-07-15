package com.shreya.utils;

import com.shreya.model.Note;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_NAME = "notes.dat";

    public static void saveNotes(List<Note> notes) {

        try {

            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME));

            out.writeObject(notes);

            out.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @SuppressWarnings("unchecked")
    public static List<Note> loadNotes() {

        try {

            ObjectInputStream in =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME));

            List<Note> notes =
                    (List<Note>) in.readObject();

            in.close();

            return notes;

        } catch (Exception e) {

            return new ArrayList<>();

        }

    }

}