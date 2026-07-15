package com.shreya.service;

import com.shreya.model.Note;
import com.shreya.utils.FileManager;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReminderService {

    private Timer timer;

    public void start() {

        timer = new Timer(60000, e -> checkReminders());

        timer.setInitialDelay(0);

        timer.start();

    }

    private void checkReminders() {

        List<Note> notes = FileManager.loadNotes();

        if (notes == null)
            return;

        LocalDate today = LocalDate.now();

        LocalTime now = LocalTime.now();

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm");

        for (Note note : notes) {

            try {

                if (note.getReminderDate() == null
                        || note.getReminderTime() == null)
                    continue;

                if (note.isReminded())
                    continue;

                LocalDate reminderDate =
                        LocalDate.parse(
                                note.getReminderDate(),
                                dateFormatter);

                LocalTime reminderTime =
                        LocalTime.parse(
                                note.getReminderTime(),
                                timeFormatter);

                if (today.equals(reminderDate)
                        && !now.isBefore(reminderTime)) {

                    JOptionPane.showMessageDialog(

                            null,

                            "🔔 Reminder\n\n"
                                    + note.getTitle()
                                    + "\n\n"
                                    + note.getContent(),

                            "Note Reminder",

                            JOptionPane.INFORMATION_MESSAGE

                    );

                    note.setReminded(true);

                    FileManager.saveNotes(notes);

                }

            } catch (Exception ex) {

                // Ignore invalid reminder format

            }

        }

    }

}
