package com.shreya.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.shreya.export.PDFExporter;
import com.shreya.model.Note;
import com.shreya.service.ReminderService;
import com.shreya.utils.BackupManager;
import com.shreya.utils.FileManager;
import com.shreya.utils.PasswordManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame {

    private JTextField searchField;

    private JButton addButton;
    private JButton exportButton;
    private JButton trashButton;
    private JButton analyticsButton;
    private JButton settingsButton;

    private JComboBox<String> sortCombo;

    private JPanel notesPanel;
    private StatsPanel statsPanel;

    private JLabel statusLabel;

    private List<Note> notes = new ArrayList<>();

    private boolean darkMode = true;

    // =========================
    // CONSTRUCTOR
    // =========================

    public Dashboard() {

        setTitle("NoteSphere - Smart Note Taking Application");
        setSize(1350, 750);
        setMinimumSize(new Dimension(1050, 650));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DO_NOTHING_ON_CLOSE);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(15, 15));

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        15,
                        25));

        // =========================
        // HEADER
        // =========================

        JLabel heading =
                new JLabel("NoteSphere");

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        36));

        heading.setHorizontalAlignment(
                SwingConstants.CENTER);

        JLabel subtitle =
                new JLabel(
                        "Organize your ideas. Stay productive.");

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14));

        subtitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS));

        titlePanel.setOpaque(false);

        heading.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        titlePanel.add(heading);

        titlePanel.add(
                Box.createVerticalStrut(3));

        titlePanel.add(subtitle);

        // =========================
        // STATS PANEL
        // =========================

        statsPanel =
                new StatsPanel();

        JPanel northPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10));

        northPanel.setOpaque(false);

        northPanel.add(
                titlePanel,
                BorderLayout.NORTH);

        northPanel.add(
                statsPanel,
                BorderLayout.CENTER);

        mainPanel.add(
                northPanel,
                BorderLayout.NORTH);

        // =========================
        // TOOLBAR
        // =========================

        JPanel toolbarPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10));

        toolbarPanel.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        5,
                        0));

        // =========================
        // SEARCH FIELD
        // =========================

        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16));

        searchField.putClientProperty(
                "JTextField.placeholderText",
                "Search your notes...");

        searchField.setToolTipText(
                "Search by title, content or category");

        searchField.setPreferredSize(
                new Dimension(
                        300,
                        40));

        // =========================
        // SEARCH LISTENER
        // =========================

        searchField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e) {

                                searchNotes();

                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e) {

                                searchNotes();

                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e) {

                                searchNotes();

                            }

                            private void searchNotes() {

                                String keyword =
                                        searchField
                                                .getText()
                                                .trim()
                                                .toLowerCase();

                                for (Component component :
                                        notesPanel.getComponents()) {

                                    if (component
                                            instanceof NoteCard card) {

                                        card.setVisible(
                                                card.matches(
                                                        keyword));

                                    }

                                }

                                notesPanel.revalidate();
                                notesPanel.repaint();

                            }

                        });

        // =========================
        // BUTTONS
        // =========================

        addButton =
                createToolbarButton(
                        "+ Add Note");

        exportButton =
                createToolbarButton(
                        "Export PDF");

        trashButton =
                createToolbarButton(
                        "Trash");

        analyticsButton =
                createToolbarButton(
                        "Analytics");

        settingsButton =
                createToolbarButton(
                        "Settings");

        // =========================
        // SORT COMBO
        // =========================

        sortCombo =
                new JComboBox<>(
                        new String[]{
                                "Newest",
                                "Oldest",
                                "Work",
                                "Study",
                                "Personal"
                        });

        sortCombo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14));

        sortCombo.setPreferredSize(
                new Dimension(
                        110,
                        38));

        // =========================
        // ACTION PANEL
        // =========================

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                7,
                                0));

        actionPanel.setOpaque(false);

        actionPanel.add(sortCombo);
        actionPanel.add(exportButton);
        actionPanel.add(trashButton);
        actionPanel.add(analyticsButton);
        actionPanel.add(settingsButton);
        actionPanel.add(addButton);

        toolbarPanel.add(
                searchField,
                BorderLayout.CENTER);

        toolbarPanel.add(
                actionPanel,
                BorderLayout.EAST);

        // =========================
        // NOTES PANEL
        // =========================

        notesPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                3,
                                15,
                                15));

        notesPanel.setBorder(
                new EmptyBorder(
                        10,
                        5,
                        10,
                        5));

        JScrollPane scrollPane =
                new JScrollPane(
                        notesPanel);

        scrollPane.setBorder(null);

        scrollPane
                .getVerticalScrollBar()
                .setUnitIncrement(16);

        // =========================
        // CENTER PANEL
        // =========================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10));

        centerPanel.add(
                toolbarPanel,
                BorderLayout.NORTH);

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER);

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER);

        // =========================
        // STATUS BAR
        // =========================

        statusLabel =
                new JLabel(
                        "NoteSphere Ready");

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13));

        statusLabel.setBorder(
                new EmptyBorder(
                        8,
                        10,
                        5,
                        10));

        mainPanel.add(
                statusLabel,
                BorderLayout.SOUTH);

        add(mainPanel);

        // =========================
        // LOAD NOTES
        // =========================

        List<Note> loadedNotes =
                FileManager.loadNotes();

        if (loadedNotes != null) {

            notes = loadedNotes;

        }

        refreshNotes();

        // =========================
        // ADD NOTE
        // =========================

        addButton.addActionListener(e -> {

            AddNoteDialog dialog =
                    new AddNoteDialog(this);

            dialog.setVisible(true);

            Note note =
                    dialog.getNote();

            if (note != null) {

                notes.add(note);

                FileManager.saveNotes(
                        notes);

                sortNotes();

            }

        });

        // =========================
        // SORT ACTION
        // =========================

        sortCombo.addActionListener(
                e -> sortNotes());

        // =========================
        // EXPORT PDF
        // =========================

        exportButton.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            chooser.setDialogTitle(
                    "Save Notes as PDF");

            chooser.setSelectedFile(
                    new java.io.File(
                            "NoteSphere_Notes.pdf"));

            int option =
                    chooser.showSaveDialog(
                            this);

            if (option ==
                    JFileChooser.APPROVE_OPTION) {

                PDFExporter.export(
                        notes,
                        chooser
                                .getSelectedFile()
                                .getAbsolutePath());

                JOptionPane.showMessageDialog(
                        this,
                        "PDF exported successfully!",
                        "Export Complete",
                        JOptionPane.INFORMATION_MESSAGE);

            }

        });

        // =========================
        // TRASH
        // =========================

        trashButton.addActionListener(e -> {

            TrashDialog trashDialog =
                    new TrashDialog(this);

            trashDialog.setVisible(true);

        });

        // =========================
        // ANALYTICS
        // =========================

        analyticsButton.addActionListener(e -> {

            AnalyticsDialog dialog =
                    new AnalyticsDialog(
                            this,
                            notes);

            dialog.setVisible(true);

        });

        // =========================
        // SETTINGS MENU
        // =========================

        settingsButton.addActionListener(e -> {

            JPopupMenu settingsMenu =
                    new JPopupMenu();

            JMenuItem backupItem =
                    new JMenuItem(
                            "Backup Notes");

            JMenuItem restoreItem =
                    new JMenuItem(
                            "Restore Backup");

            JMenuItem passwordItem =
                    new JMenuItem(
                            "Change Password");

            JMenuItem themeItem =
                    new JMenuItem(
                            darkMode
                                    ? "Switch to Light Mode"
                                    : "Switch to Dark Mode");

            JMenuItem lockItem =
                    new JMenuItem(
                            "Lock NoteSphere");

            JMenuItem shortcutsItem =
                    new JMenuItem(
                            "Keyboard Shortcuts");

            JMenuItem aboutItem =
                    new JMenuItem(
                            "About NoteSphere");

            // =========================
            // BACKUP
            // =========================

            backupItem.addActionListener(
                    event -> {

                        BackupManager.manualBackup(
                                notes,
                                this);

                    });

            // =========================
            // RESTORE
            // =========================

            restoreItem.addActionListener(
                    event -> {

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        this,
                                        "Restoring a backup will replace "
                                                + "your current notes.\n"
                                                + "Do you want to continue?",
                                        "Restore Backup",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.WARNING_MESSAGE);

                        if (confirm !=
                                JOptionPane.YES_OPTION) {

                            return;

                        }

                        List<Note> restoredNotes =
                                BackupManager.restoreBackup(
                                        this);

                        if (restoredNotes != null) {

                            notes =
                                    new ArrayList<>(
                                            restoredNotes);

                            FileManager.saveNotes(
                                    notes);

                            refreshNotes();

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Notes restored successfully!",
                                    "Restore Complete",
                                    JOptionPane.INFORMATION_MESSAGE);

                        }

                    });

            // =========================
            // CHANGE PASSWORD
            // =========================

            passwordItem.addActionListener(
                    event -> {

                        showChangePasswordDialog();

                    });

            // =========================
            // THEME
            // =========================

            themeItem.addActionListener(
                    event -> {

                        switchTheme();

                    });

            // =========================
            // LOCK
            // =========================

            lockItem.addActionListener(
                    event -> {

                        lockApplication();

                    });

            // =========================
            // KEYBOARD SHORTCUTS
            // =========================

            shortcutsItem.addActionListener(
                    event -> {

                        JOptionPane.showMessageDialog(
                                this,
                                """
                                NoteSphere Keyboard Shortcuts

                                Ctrl + N   -   Create New Note
                                Ctrl + F   -   Search Notes
                                Ctrl + E   -   Export Notes as PDF
                                Ctrl + T   -   Open Trash
                                Ctrl + L   -   Lock NoteSphere
                                """,
                                "Keyboard Shortcuts",
                                JOptionPane.INFORMATION_MESSAGE);

                    });

            // =========================
            // ABOUT NOTESPHERE
            // =========================

            aboutItem.addActionListener(
                    event -> {

                        JOptionPane.showMessageDialog(
                                this,
                                """
                                NoteSphere

                                Smart Note Taking Application

                                FEATURES

                                • Create and edit notes
                                • Search notes instantly
                                • Organize notes by category
                                • Pin important notes
                                • Mark notes as favorites
                                • Attach images to notes
                                • Set reminder notifications
                                • Sort notes
                                • Trash and restore notes
                                • Permanently delete notes
                                • Export notes as PDF
                                • Analytics dashboard
                                • Password protection
                                • Application lock
                                • Dark and light themes
                                • Manual backup
                                • Automatic backup
                                • Restore backups
                                • Keyboard shortcuts

                                Version 1.0
                                """,
                                "About NoteSphere",
                                JOptionPane.INFORMATION_MESSAGE);

                    });

            // =========================
            // ADD MENU ITEMS
            // =========================

            settingsMenu.add(
                    backupItem);

            settingsMenu.add(
                    restoreItem);

            settingsMenu.addSeparator();

            settingsMenu.add(
                    passwordItem);

            settingsMenu.add(
                    themeItem);

            settingsMenu.add(
                    lockItem);

            settingsMenu.addSeparator();

            settingsMenu.add(
                    shortcutsItem);

            settingsMenu.add(
                    aboutItem);

            // =========================
            // SHOW SETTINGS MENU
            // =========================

            settingsMenu.show(
                    settingsButton,
                    0,
                    settingsButton.getHeight());

        });

        // =========================
        // REMINDER SERVICE
        // =========================

        ReminderService reminderService =
                new ReminderService();

        reminderService.start();

        // =========================
        // AUTO BACKUP ON CLOSE
        // =========================

        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(
                            WindowEvent e) {

                        FileManager.saveNotes(
                                notes);

                        BackupManager.createBackup(
                                notes);

                        dispose();

                        System.exit(0);

                    }

                });

        // =========================
        // KEYBOARD SHORTCUTS
        // =========================

        setupKeyboardShortcuts();

    }

    // =========================
    // KEYBOARD SHORTCUTS
    // =========================

    private void setupKeyboardShortcuts() {

        JRootPane rootPane =
                getRootPane();

        InputMap inputMap =
                rootPane.getInputMap(
                        JComponent.WHEN_IN_FOCUSED_WINDOW);

        ActionMap actionMap =
                rootPane.getActionMap();

        // CTRL + N

        inputMap.put(
                KeyStroke.getKeyStroke(
                        "control N"),
                "addNote");

        actionMap.put(
                "addNote",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent e) {

                        addButton.doClick();

                    }

                });

        // CTRL + F

        inputMap.put(
                KeyStroke.getKeyStroke(
                        "control F"),
                "searchNotes");

        actionMap.put(
                "searchNotes",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent e) {

                        searchField
                                .requestFocusInWindow();

                        searchField
                                .selectAll();

                    }

                });

        // CTRL + E

        inputMap.put(
                KeyStroke.getKeyStroke(
                        "control E"),
                "exportPDF");

        actionMap.put(
                "exportPDF",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent e) {

                        exportButton.doClick();

                    }

                });

        // CTRL + T

        inputMap.put(
                KeyStroke.getKeyStroke(
                        "control T"),
                "openTrash");

        actionMap.put(
                "openTrash",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent e) {

                        trashButton.doClick();

                    }

                });

        // CTRL + L

        inputMap.put(
                KeyStroke.getKeyStroke(
                        "control L"),
                "lockApplication");

        actionMap.put(
                "lockApplication",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(
                            java.awt.event.ActionEvent e) {

                        lockApplication();

                    }

                });

    }

    // =========================
    // LOCK APPLICATION
    // =========================

    private void lockApplication() {

        FileManager.saveNotes(
                notes);

        LockScreen lockScreen =
                new LockScreen();

        lockScreen.setVisible(
                true);

        dispose();

    }

    // =========================
    // CREATE TOOLBAR BUTTON
    // =========================

    private JButton createToolbarButton(
            String text) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14));

        button.setFocusPainted(
                false);

        button.setPreferredSize(
                new Dimension(
                        110,
                        38));

        return button;

    }

    // =========================
    // CHANGE PASSWORD
    // =========================

    private void showChangePasswordDialog() {

        JPasswordField currentField =
                new JPasswordField();

        JPasswordField newField =
                new JPasswordField();

        JPasswordField confirmField =
                new JPasswordField();

        Object[] fields = {

                "Current Password:",
                currentField,

                "New Password:",
                newField,

                "Confirm New Password:",
                confirmField

        };

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

        if (result !=
                JOptionPane.OK_OPTION) {

            return;

        }

        String currentPassword =
                new String(
                        currentField
                                .getPassword());

        String newPassword =
                new String(
                        newField
                                .getPassword());

        String confirmPassword =
                new String(
                        confirmField
                                .getPassword());

        if (!PasswordManager.checkPassword(
                currentPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Current password is incorrect!",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);

            return;

        }

        if (newPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password cannot be empty!",
                    "Password Error",
                    JOptionPane.WARNING_MESSAGE);

            return;

        }

        if (newPassword.length() < 4) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 4 characters!",
                    "Password Error",
                    JOptionPane.WARNING_MESSAGE);

            return;

        }

        if (!newPassword.equals(
                confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "New passwords do not match!",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);

            return;

        }

        PasswordManager.changePassword(
                newPassword);

        JOptionPane.showMessageDialog(
                this,
                "Password changed successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

    }

    // =========================
    // SWITCH THEME
    // =========================

    private void switchTheme() {

        try {

            if (darkMode) {

                UIManager.setLookAndFeel(
                        new FlatLightLaf());

                darkMode = false;

            } else {

                UIManager.setLookAndFeel(
                        new FlatDarkLaf());

                darkMode = true;

            }

            for (Window window :
                    Window.getWindows()) {

                SwingUtilities
                        .updateComponentTreeUI(
                                window);

                window.revalidate();
                window.repaint();

            }

        } catch (Exception exception) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to change theme.",
                    "Theme Error",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();

        }

    }

    // =========================
    // SORT NOTES
    // =========================

    private void sortNotes() {

        String option =
                (String)
                        sortCombo
                                .getSelectedItem();

        if (option == null) {

            return;

        }

        switch (option) {

            case "Newest":

                notes.sort(
                        (a, b) ->
                                safeString(
                                        b.getDateTime())
                                        .compareTo(
                                                safeString(
                                                        a.getDateTime())));

                break;

            case "Oldest":

                notes.sort(
                        (a, b) ->
                                safeString(
                                        a.getDateTime())
                                        .compareTo(
                                                safeString(
                                                        b.getDateTime())));

                break;

            case "Work":

                sortCategoryFirst(
                        "Work");

                break;

            case "Study":

                sortCategoryFirst(
                        "Study");

                break;

            case "Personal":

                sortCategoryFirst(
                        "Personal");

                break;

            default:
                break;

        }

        refreshNotes();

    }

    // =========================
    // CATEGORY SORT
    // =========================

    private void sortCategoryFirst(
            String category) {

        notes.sort(
                (a, b) -> {

                    boolean first =
                            category.equals(
                                    a.getCategory());

                    boolean second =
                            category.equals(
                                    b.getCategory());

                    return Boolean.compare(
                            second,
                            first);

                });

    }

    // =========================
    // SAFE STRING
    // =========================

    private String safeString(
            String value) {

        return value == null
                ? ""
                : value;

    }

    // =========================
    // REFRESH NOTES
    // =========================

    public void refreshNotes() {

        notesPanel.removeAll();

        int activeNotes = 0;

        for (Note note :
                notes) {

            if (!note.isDeleted()) {

                notesPanel.add(
                        new NoteCard(
                                note));

                activeNotes++;

            }

        }

        // =========================
        // EMPTY STATE
        // =========================

        if (activeNotes == 0) {

            JPanel emptyPanel =
                    new JPanel();

            emptyPanel.setLayout(
                    new BoxLayout(
                            emptyPanel,
                            BoxLayout.Y_AXIS));

            emptyPanel.setOpaque(
                    false);

            JLabel emptyTitle =
                    new JLabel(
                            "No notes yet");

            emptyTitle.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            24));

            emptyTitle.setAlignmentX(
                    Component.CENTER_ALIGNMENT);

            JLabel emptyMessage =
                    new JLabel(
                            "Click + Add Note to create your first note.");

            emptyMessage.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            15));

            emptyMessage.setAlignmentX(
                    Component.CENTER_ALIGNMENT);

            emptyPanel.add(
                    Box.createVerticalStrut(
                            80));

            emptyPanel.add(
                    emptyTitle);

            emptyPanel.add(
                    Box.createVerticalStrut(
                            10));

            emptyPanel.add(
                    emptyMessage);

            notesPanel.add(
                    emptyPanel);

        }

        notesPanel.revalidate();
        notesPanel.repaint();

        refreshStats();

    }

    // =========================
    // REFRESH STATS
    // =========================

    public void refreshStats() {

        int total = 0;
        int pinned = 0;

        int work = 0;
        int study = 0;
        int personal = 0;

        for (Note note :
                notes) {

            if (note.isDeleted()) {

                continue;

            }

            total++;

            if (note.isPinned()) {

                pinned++;

            }

            String category =
                    note.getCategory();

            if ("Work".equals(
                    category)) {

                work++;

            } else if (
                    "Study".equals(
                            category)) {

                study++;

            } else if (
                    "Personal".equals(
                            category)) {

                personal++;

            }

        }

        statsPanel.updateStats(
                total,
                pinned,
                work,
                study,
                personal);

        if (statusLabel != null) {

            statusLabel.setText(
                    "Showing "
                            + total
                            + " active notes"
                            + "   |   "
                            + pinned
                            + " pinned"
                            + "   |   NoteSphere Ready");

        }

    }

    // =========================
    // GETTERS
    // =========================

    public JTextField getSearchField() {

        return searchField;

    }

    public JButton getAddButton() {

        return addButton;

    }

    public JPanel getNotesPanel() {

        return notesPanel;

    }

    public List<Note> getNotes() {

        return notes;

    }

}