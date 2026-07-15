package com.shreya.ui;

import com.shreya.model.Note;
import com.shreya.utils.FileManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TrashDialog extends JDialog {

    private final Dashboard dashboard;
    private final List<Note> notes;

    private JPanel trashPanel;

    public TrashDialog(Dashboard dashboard) {

        super(dashboard, "🗑 Trash Bin", true);

        this.dashboard = dashboard;
        this.notes = dashboard.getNotes();

        setSize(750, 550);
        setLocationRelativeTo(dashboard);

        initializeUI();
        refreshTrash();
    }

    // =========================
    // UI
    // =========================

    private void initializeUI() {

        setLayout(new BorderLayout(15, 15));

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setBorder(
                new EmptyBorder(15, 15, 5, 15));

        JLabel heading =
                new JLabel("🗑 Deleted Notes");

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26));

        JButton emptyTrashButton =
                new JButton("🗑 Empty Trash");

        emptyTrashButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14));

        headerPanel.add(
                heading,
                BorderLayout.WEST);

        headerPanel.add(
                emptyTrashButton,
                BorderLayout.EAST);

        add(
                headerPanel,
                BorderLayout.NORTH);

        // =========================
        // TRASH PANEL
        // =========================

        trashPanel = new JPanel();

        trashPanel.setLayout(
                new BoxLayout(
                        trashPanel,
                        BoxLayout.Y_AXIS));

        JScrollPane scrollPane =
                new JScrollPane(trashPanel);

        scrollPane.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        15,
                        15));

        add(
                scrollPane,
                BorderLayout.CENTER);

        // =========================
        // EMPTY TRASH
        // =========================

        emptyTrashButton.addActionListener(e -> {

            boolean hasDeletedNotes = false;

            for (Note note : notes) {

                if (note.isDeleted()) {

                    hasDeletedNotes = true;
                    break;
                }
            }

            if (!hasDeletedNotes) {

                JOptionPane.showMessageDialog(
                        this,
                        "Trash is already empty!");

                return;
            }

            int option =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Permanently delete all notes in Trash?",
                            "Empty Trash",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

            if (option ==
                    JOptionPane.YES_OPTION) {

                notes.removeIf(
                        Note::isDeleted);

                FileManager.saveNotes(notes);

                refreshTrash();

                dashboard.refreshNotes();

                JOptionPane.showMessageDialog(
                        this,
                        "Trash emptied successfully!");
            }

        });
    }

    // =========================
    // REFRESH TRASH
    // =========================

    private void refreshTrash() {

        trashPanel.removeAll();

        boolean found = false;

        for (Note note : notes) {

            if (note.isDeleted()) {

                found = true;

                trashPanel.add(
                        createTrashCard(note));

                trashPanel.add(
                        Box.createVerticalStrut(10));
            }
        }

        if (!found) {

            JLabel emptyLabel =
                    new JLabel(
                            "🗑 Trash is empty",
                            SwingConstants.CENTER);

            emptyLabel.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            20));

            emptyLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT);

            trashPanel.add(
                    Box.createVerticalStrut(50));

            trashPanel.add(emptyLabel);
        }

        trashPanel.revalidate();
        trashPanel.repaint();
    }

    // =========================
    // CREATE TRASH CARD
    // =========================

    private JPanel createTrashCard(
            Note note) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                10));

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120));

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory
                                .createLineBorder(
                                        new Color(
                                                100,
                                                100,
                                                100)),
                        new EmptyBorder(
                                12,
                                12,
                                12,
                                12)));

        // =========================
        // NOTE INFORMATION
        // =========================

        JPanel informationPanel =
                new JPanel();

        informationPanel.setLayout(
                new BoxLayout(
                        informationPanel,
                        BoxLayout.Y_AXIS));

        JLabel titleLabel =
                new JLabel(
                        note.getTitle());

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18));

        JLabel categoryLabel =
                new JLabel(
                        "Category: "
                                + note.getCategory());

        JLabel dateLabel =
                new JLabel(
                        note.getDateTime());

        informationPanel.add(
                titleLabel);

        informationPanel.add(
                Box.createVerticalStrut(5));

        informationPanel.add(
                categoryLabel);

        informationPanel.add(
                dateLabel);

        // =========================
        // BUTTONS
        // =========================

        JButton restoreButton =
                new JButton("♻ Restore");

        JButton deleteButton =
                new JButton(
                        "❌ Delete Permanently");

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT));

        buttonPanel.add(
                restoreButton);

        buttonPanel.add(
                deleteButton);

        card.add(
                informationPanel,
                BorderLayout.CENTER);

        card.add(
                buttonPanel,
                BorderLayout.EAST);

        // =========================
        // RESTORE
        // =========================

        restoreButton.addActionListener(e -> {

            note.setDeleted(false);

            FileManager.saveNotes(notes);

            refreshTrash();

            dashboard.refreshNotes();

            JOptionPane.showMessageDialog(
                    this,
                    "Note restored successfully!");

        });

        // =========================
        // PERMANENT DELETE
        // =========================

        deleteButton.addActionListener(e -> {

            int option =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Permanently delete \""
                                    + note.getTitle()
                                    + "\"?\n\n"
                                    + "This action cannot be undone.",
                            "Permanent Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

            if (option ==
                    JOptionPane.YES_OPTION) {

                notes.remove(note);

                FileManager.saveNotes(notes);

                refreshTrash();

                dashboard.refreshNotes();
            }

        });

        return card;
    }
}