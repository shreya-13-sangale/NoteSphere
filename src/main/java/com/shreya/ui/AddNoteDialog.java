package com.shreya.ui;

import com.shreya.model.Note;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddNoteDialog extends JDialog {

    private JTextField titleField;
    private JTextArea contentArea;
    private JComboBox<String> categoryBox;

    private JCheckBox pinBox;
    private JCheckBox favoriteBox;

    private JButton imageButton;
    private JButton saveButton;

    private JTextField reminderDateField;
    private JTextField reminderTimeField;

    private String imagePath;

    private Note note;

    // =========================
    // NEW NOTE CONSTRUCTOR
    // =========================

    public AddNoteDialog(JFrame parent) {

        super(parent, "Add New Note", true);

        initializeUI();
    }

    // =========================
    // EDIT NOTE CONSTRUCTOR
    // =========================

    public AddNoteDialog(JFrame parent, Note note) {

        super(parent, "Edit Note", true);

        initializeUI();

        titleField.setText(note.getTitle());
        contentArea.setText(note.getContent());

        categoryBox.setSelectedItem(
                note.getCategory());

        pinBox.setSelected(
                note.isPinned());

        favoriteBox.setSelected(
                note.isFavorite());

        // Load existing image
        imagePath = note.getImagePath();

        if (imagePath != null &&
                !imagePath.isEmpty()) {

            imageButton.setText(
                    "✅ Image Selected");
        }

        // Load existing reminder
        if (note.getReminderDate() != null) {

            reminderDateField.setText(
                    note.getReminderDate());
        }

        if (note.getReminderTime() != null) {

            reminderTimeField.setText(
                    note.getReminderTime());
        }
    }

    // =========================
    // INITIALIZE UI
    // =========================

    private void initializeUI() {

        setSize(550, 720);

        setLocationRelativeTo(
                getParent());

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15));

        mainPanel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        15,
                        15));

        // =========================
        // FORM PANEL
        // =========================

        JPanel form =
                new JPanel();

        form.setLayout(
                new BoxLayout(
                        form,
                        BoxLayout.Y_AXIS));

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("Title");

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16));

        titleField =
                new JTextField();

        titleField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16));

        // =========================
        // CATEGORY
        // =========================

        JLabel categoryLabel =
                new JLabel("Category");

        categoryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16));

        categoryBox =
                new JComboBox<>(
                        new String[] {
                                "Personal",
                                "Study",
                                "Work",
                                "Others"
                        });

        categoryBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15));

        // =========================
        // PIN AND FAVORITE
        // =========================

        pinBox =
                new JCheckBox(
                        "📌 Pin this note");

        favoriteBox =
                new JCheckBox(
                        "⭐ Favorite Note");

        pinBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15));

        favoriteBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15));

        // =========================
        // IMAGE BUTTON
        // =========================

        imageButton =
                new JButton(
                        "📷 Choose Image");

        imageButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            int result =
                    chooser.showOpenDialog(this);

            if (result ==
                    JFileChooser.APPROVE_OPTION) {

                imagePath =
                        chooser
                                .getSelectedFile()
                                .getAbsolutePath();

                imageButton.setText(
                        "✅ Image Selected");
            }

        });

        // =========================
        // REMINDER DATE
        // =========================

        JLabel reminderDateLabel =
                new JLabel(
                        "🔔 Reminder Date (dd/MM/yyyy)");

        reminderDateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14));

        reminderDateField =
                new JTextField();

        reminderDateField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15));

        reminderDateField.setToolTipText(
                "Example: 15/07/2026");

        // =========================
        // REMINDER TIME
        // =========================

        JLabel reminderTimeLabel =
                new JLabel(
                        "⏰ Reminder Time (HH:mm)");

        reminderTimeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14));

        reminderTimeField =
                new JTextField();

        reminderTimeField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15));

        reminderTimeField.setToolTipText(
                "Example: 14:30");

        // =========================
        // CONTENT
        // =========================

        JLabel contentLabel =
                new JLabel("Content");

        contentLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16));

        contentArea =
                new JTextArea(
                        8,
                        20);

        contentArea.setLineWrap(true);

        contentArea.setWrapStyleWord(true);

        contentArea.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15));

        JScrollPane scrollPane =
                new JScrollPane(
                        contentArea);

        // =========================
        // ADD COMPONENTS
        // =========================

        form.add(titleLabel);
        form.add(titleField);

        form.add(
                Box.createVerticalStrut(10));

        form.add(categoryLabel);
        form.add(categoryBox);

        form.add(
                Box.createVerticalStrut(10));

        form.add(pinBox);
        form.add(favoriteBox);

        form.add(
                Box.createVerticalStrut(10));

        form.add(imageButton);

        form.add(
                Box.createVerticalStrut(15));

        // REMINDER FIELDS

        form.add(reminderDateLabel);

        form.add(
                Box.createVerticalStrut(5));

        form.add(reminderDateField);

        form.add(
                Box.createVerticalStrut(10));

        form.add(reminderTimeLabel);

        form.add(
                Box.createVerticalStrut(5));

        form.add(reminderTimeField);

        form.add(
                Box.createVerticalStrut(15));

        form.add(contentLabel);

        form.add(
                Box.createVerticalStrut(5));

        form.add(scrollPane);

        // =========================
        // SAVE BUTTON
        // =========================

        saveButton =
                new JButton(
                        "💾 Save Note");

        saveButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16));

        saveButton.addActionListener(e -> {

            String title =
                    titleField
                            .getText()
                            .trim();

            String content =
                    contentArea
                            .getText()
                            .trim();

            // Check title

            if (title.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a title!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            String category =
                    categoryBox
                            .getSelectedItem()
                            .toString();

            String time =
                    LocalDateTime
                            .now()
                            .format(
                                    DateTimeFormatter
                                            .ofPattern(
                                                    "dd MMM yyyy  hh:mm a"));

            // =========================
            // CREATE NOTE
            // =========================

            note =
                    new Note(
                            title,
                            content,
                            time,
                            category,
                            favoriteBox.isSelected(),
                            pinBox.isSelected(),
                            imagePath);

            // =========================
            // SAVE REMINDER
            // =========================

            note.setReminderDate(
                    reminderDateField
                            .getText()
                            .trim());

            note.setReminderTime(
                    reminderTimeField
                            .getText()
                            .trim());

            note.setReminded(false);

            dispose();

        });

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT));

        buttonPanel.add(
                saveButton);

        // =========================
        // ADD TO DIALOG
        // =========================

        mainPanel.add(
                form,
                BorderLayout.CENTER);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH);

        add(mainPanel);
    }

    // =========================
    // GET NOTE
    // =========================

    public Note getNote() {

        return note;
    }
}