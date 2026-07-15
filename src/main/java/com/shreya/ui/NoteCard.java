package com.shreya.ui;

import com.shreya.model.Note;
import com.shreya.utils.FileManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NoteCard extends JPanel {

    private final Note note;

    private JLabel titleLabel;
    private JLabel dateLabel;
    private JLabel categoryLabel;
    private JLabel reminderLabel;

    private JTextArea contentArea;

    public NoteCard(Note note) {

        this.note = note;

        setLayout(new BorderLayout(10, 10));
        setOpaque(true);

        setPreferredSize(
                new Dimension(320, 250));

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(90, 90, 90),
                                1,
                                true),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15)
                )
        );

        // =========================
        // TITLE
        // =========================

        titleLabel =
                new JLabel(
                        buildTitle());

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18));

        titleLabel.setForeground(
                Color.WHITE);

        // =========================
        // DATE
        // =========================

        dateLabel =
                new JLabel(
                        note.getDateTime());

        dateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12));

        dateLabel.setForeground(
                new Color(
                        220,
                        220,
                        220));

        // =========================
        // CATEGORY
        // =========================

        categoryLabel =
                new JLabel(
                        note.getCategory());

        categoryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12));

        // =========================
        // REMINDER
        // =========================

        reminderLabel =
                new JLabel();

        reminderLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11));

        reminderLabel.setForeground(
                new Color(
                        230,
                        230,
                        230));

        updateReminderLabel();

        // =========================
        // CARD COLOR
        // =========================

        updateCardColor();

        // =========================
        // INFORMATION PANEL
        // =========================

        JPanel informationPanel =
                new JPanel();

        informationPanel.setLayout(
                new BoxLayout(
                        informationPanel,
                        BoxLayout.Y_AXIS));

        informationPanel.setOpaque(false);

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT);

        dateLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT);

        categoryLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT);

        reminderLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT);

        informationPanel.add(
                titleLabel);

        informationPanel.add(
                Box.createVerticalStrut(3));

        informationPanel.add(
                dateLabel);

        informationPanel.add(
                Box.createVerticalStrut(3));

        informationPanel.add(
                categoryLabel);

        informationPanel.add(
                Box.createVerticalStrut(3));

        informationPanel.add(
                reminderLabel);

        // =========================
        // BUTTONS
        // =========================

        JButton favoriteButton =
                new JButton("Favorite");

        JButton editButton =
                new JButton("Edit");

        JButton deleteButton =
                new JButton("Delete");

        favoriteButton.setToolTipText(
                "Add or remove from favorites");

        editButton.setToolTipText(
                "Edit this note");

        deleteButton.setToolTipText(
                "Move this note to Trash");

        favoriteButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11));

        editButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11));

        deleteButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11));

        // Show favorite status

        if (note.isFavorite()) {

            favoriteButton.setText(
                    "Favorited");

        }

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                5,
                                0));

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                favoriteButton);

        buttonPanel.add(
                editButton);

        buttonPanel.add(
                deleteButton);

        // =========================
        // HEADER PANEL
        // =========================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                5));

        headerPanel.setOpaque(false);

        headerPanel.add(
                informationPanel,
                BorderLayout.CENTER);

        headerPanel.add(
                buttonPanel,
                BorderLayout.EAST);

        add(
                headerPanel,
                BorderLayout.NORTH);

        // =========================
        // CONTENT
        // =========================

        contentArea =
                new JTextArea(
                        note.getContent());

        contentArea.setEditable(false);
        contentArea.setOpaque(false);

        contentArea.setForeground(
                Color.WHITE);

        contentArea.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14));

        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane contentScroll =
                new JScrollPane(
                        contentArea);

        contentScroll.setBorder(null);
        contentScroll.setOpaque(false);

        contentScroll
                .getViewport()
                .setOpaque(false);

        add(
                contentScroll,
                BorderLayout.CENTER);

        // =========================
        // IMAGE PREVIEW
        // =========================

        if (note.getImagePath() != null
                && !note.getImagePath().isBlank()) {

            ImageIcon originalIcon =
                    new ImageIcon(
                            note.getImagePath());

            // Only show valid images

            if (originalIcon.getIconWidth() > 0) {

                Image scaledImage =
                        originalIcon
                                .getImage()
                                .getScaledInstance(
                                        100,
                                        75,
                                        Image.SCALE_SMOOTH);

                JLabel imageLabel =
                        new JLabel(
                                new ImageIcon(
                                        scaledImage));

                imageLabel.setCursor(
                        new Cursor(
                                Cursor.HAND_CURSOR));

                imageLabel.setToolTipText(
                        "Click to view full image");

                imageLabel.setBorder(
                        new EmptyBorder(
                                5,
                                5,
                                5,
                                5));

                // Open image preview

                imageLabel.addMouseListener(
                        new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        showImagePreview();

                    }

                });

                JPanel imagePanel =
                        new JPanel(
                                new FlowLayout(
                                        FlowLayout.CENTER));

                imagePanel.setOpaque(false);

                imagePanel.add(
                        imageLabel);

                add(
                        imagePanel,
                        BorderLayout.SOUTH);
            }
        }

        // =========================
        // FAVORITE ACTION
        // =========================

        favoriteButton.addActionListener(e -> {

            note.setFavorite(
                    !note.isFavorite());

            titleLabel.setText(
                    buildTitle());

            if (note.isFavorite()) {

                favoriteButton.setText(
                        "Favorited");

            } else {

                favoriteButton.setText(
                        "Favorite");

            }

            Dashboard dashboard =
                    getDashboard();

            if (dashboard != null) {

                FileManager.saveNotes(
                        dashboard.getNotes());

                dashboard.refreshStats();
            }

        });

        // =========================
        // EDIT ACTION
        // =========================

        editButton.addActionListener(e -> {

            Dashboard dashboard =
                    getDashboard();

            if (dashboard == null) {

                return;
            }

            AddNoteDialog dialog =
                    new AddNoteDialog(
                            dashboard,
                            note);

            dialog.setVisible(true);

            Note updated =
                    dialog.getNote();

            if (updated != null) {

                // Update existing note

                note.setTitle(
                        updated.getTitle());

                note.setContent(
                        updated.getContent());

                note.setCategory(
                        updated.getCategory());

                note.setPinned(
                        updated.isPinned());

                note.setFavorite(
                        updated.isFavorite());

                note.setImagePath(
                        updated.getImagePath());

                note.setReminderDate(
                        updated.getReminderDate());

                note.setReminderTime(
                        updated.getReminderTime());

                note.setReminded(
                        updated.isReminded());

                // Save changes

                FileManager.saveNotes(
                        dashboard.getNotes());

                // Rebuild cards so image,
                // colors and reminder update correctly

                dashboard.refreshNotes();

            }

        });

        // =========================
        // DELETE ACTION
        // =========================

        deleteButton.addActionListener(e -> {

            int option =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Move this note to Trash?",
                            "Delete Note",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

            if (option ==
                    JOptionPane.YES_OPTION) {

                Dashboard dashboard =
                        getDashboard();

                if (dashboard != null) {

                    note.setDeleted(true);

                    FileManager.saveNotes(
                            dashboard.getNotes());

                    dashboard.refreshNotes();

                }

            }

        });

    }

    // =========================
    // BUILD TITLE
    // =========================

    private String buildTitle() {

        StringBuilder builder =
                new StringBuilder();

        if (note.isPinned()) {

            builder.append("[PIN] ");

        }

        if (note.isFavorite()) {

            builder.append("[STAR] ");

        }

        builder.append(
                note.getTitle());

        return builder.toString();

    }

    // =========================
    // UPDATE REMINDER LABEL
    // =========================

    private void updateReminderLabel() {

        String reminderDate =
                note.getReminderDate();

        String reminderTime =
                note.getReminderTime();

        if (reminderDate != null
                && !reminderDate.isBlank()
                && reminderTime != null
                && !reminderTime.isBlank()) {

            reminderLabel.setText(
                    "Reminder: "
                            + reminderDate
                            + " at "
                            + reminderTime);

        } else {

            reminderLabel.setText(
                    "No reminder");

        }

    }

    // =========================
    // UPDATE CARD COLOR
    // =========================

    private void updateCardColor() {

        String category =
                note.getCategory();

        if ("Work".equals(category)) {

            setBackground(
                    new Color(
                            45,
                            70,
                            120));

            categoryLabel.setForeground(
                    new Color(
                            150,
                            200,
                            255));

        } else if (
                "Study".equals(category)) {

            setBackground(
                    new Color(
                            40,
                            90,
                            60));

            categoryLabel.setForeground(
                    new Color(
                            140,
                            255,
                            170));

        } else if (
                "Personal".equals(category)) {

            setBackground(
                    new Color(
                            110,
                            95,
                            35));

            categoryLabel.setForeground(
                    new Color(
                            255,
                            220,
                            100));

        } else {

            setBackground(
                    new Color(
                            65,
                            65,
                            70));

            categoryLabel.setForeground(
                    Color.WHITE);

        }

    }

    // =========================
    // IMAGE PREVIEW
    // =========================

    private void showImagePreview() {

        if (note.getImagePath() == null
                || note.getImagePath().isBlank()) {

            return;
        }

        JDialog previewDialog =
                new JDialog();

        previewDialog.setTitle(
                "Image Preview - "
                        + note.getTitle());

        previewDialog.setSize(
                850,
                650);

        previewDialog.setLocationRelativeTo(
                this);

        ImageIcon imageIcon =
                new ImageIcon(
                        note.getImagePath());

        JLabel imageLabel =
                new JLabel(
                        imageIcon);

        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        JScrollPane scrollPane =
                new JScrollPane(
                        imageLabel);

        previewDialog.add(
                scrollPane);

        previewDialog.setVisible(true);

    }

    // =========================
    // GET DASHBOARD
    // =========================

    private Dashboard getDashboard() {

        Window window =
                SwingUtilities
                        .getWindowAncestor(this);

        if (window
                instanceof Dashboard dashboard) {

            return dashboard;

        }

        return null;

    }

    // =========================
    // SEARCH
    // =========================

    public boolean matches(
            String keyword) {

        if (keyword == null
                || keyword.isBlank()) {

            return true;

        }

        String search =
                keyword.toLowerCase();

        return note
                .getTitle()
                .toLowerCase()
                .contains(search)

                || note
                .getContent()
                .toLowerCase()
                .contains(search)

                || note
                .getCategory()
                .toLowerCase()
                .contains(search);

    }

}