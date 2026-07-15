package com.shreya.utils;

import com.shreya.model.Note;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BackupManager {

    // Folder where automatic backups are stored
    private static final String BACKUP_FOLDER =
            "backups";

    // =========================
    // CREATE BACKUP
    // =========================

    public static void createBackup(
            List<Note> notes) {

        if (notes == null) {
            return;
        }

        try {

            // Create backup folder if it doesn't exist

            File folder =
                    new File(BACKUP_FOLDER);

            if (!folder.exists()) {

                folder.mkdirs();
            }

            // Create unique backup filename

            String dateTime =
                    LocalDateTime.now()
                            .format(
                                    DateTimeFormatter.ofPattern(
                                            "yyyy-MM-dd_HH-mm-ss"));

            File backupFile =
                    new File(
                            folder,
                            "NoteSphere_Backup_"
                                    + dateTime
                                    + ".dat");

            // Save notes

            try (ObjectOutputStream output =
                         new ObjectOutputStream(
                                 new FileOutputStream(
                                         backupFile))) {

                output.writeObject(notes);

            }

            System.out.println(
                    "Backup created: "
                            + backupFile
                            .getAbsolutePath());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // =========================
    // MANUAL BACKUP
    // =========================

    public static void manualBackup(
            List<Note> notes,
            JFrame parent) {

        JFileChooser chooser =
                new JFileChooser();

        chooser.setDialogTitle(
                "Save NoteSphere Backup");

        chooser.setSelectedFile(
                new File(
                        "NoteSphere_Backup.dat"));

        int result =
                chooser.showSaveDialog(
                        parent);

        if (result !=
                JFileChooser.APPROVE_OPTION) {

            return;
        }

        File selectedFile =
                chooser.getSelectedFile();

        try {

            // Automatically add .dat extension

            if (!selectedFile
                    .getName()
                    .toLowerCase()
                    .endsWith(".dat")) {

                selectedFile =
                        new File(
                                selectedFile
                                        .getAbsolutePath()
                                        + ".dat");
            }

            try (ObjectOutputStream output =
                         new ObjectOutputStream(
                                 new FileOutputStream(
                                         selectedFile))) {

                output.writeObject(notes);

            }

            JOptionPane.showMessageDialog(
                    parent,
                    "Backup created successfully!\n\n"
                            + selectedFile
                            .getAbsolutePath(),
                    "Backup Complete",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Unable to create backup.\n"
                            + e.getMessage(),
                    "Backup Error",
                    JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

        }

    }

    // =========================
    // RESTORE BACKUP
    // =========================

    @SuppressWarnings("unchecked")
    public static List<Note> restoreBackup(
            JFrame parent) {

        JFileChooser chooser =
                new JFileChooser();

        chooser.setDialogTitle(
                "Select NoteSphere Backup");

        int result =
                chooser.showOpenDialog(
                        parent);

        if (result !=
                JFileChooser.APPROVE_OPTION) {

            return null;
        }

        File backupFile =
                chooser.getSelectedFile();

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(
                                     backupFile))) {

            Object data =
                    input.readObject();

            if (data instanceof List<?>) {

                List<Note> restoredNotes =
                        (List<Note>) data;

                JOptionPane.showMessageDialog(
                        parent,
                        "Backup restored successfully!",
                        "Restore Complete",
                        JOptionPane.INFORMATION_MESSAGE);

                return restoredNotes;

            }

            JOptionPane.showMessageDialog(
                    parent,
                    "The selected file is not a valid NoteSphere backup.",
                    "Invalid Backup",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Unable to restore backup.\n"
                            + e.getMessage(),
                    "Restore Error",
                    JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

        }

        return null;

    }

    // =========================
    // COPY BACKUP FILE
    // =========================

    public static boolean copyBackup(
            File source,
            File destination) {

        try {

            Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return true;

        } catch (IOException e) {

            e.printStackTrace();

            return false;

        }

    }

}