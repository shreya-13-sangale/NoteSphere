package com.shreya.ui;

import com.shreya.utils.PasswordManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LockScreen extends JFrame {

    private JPasswordField passwordField;

    public LockScreen() {

        setTitle("🔒 NoteSphere Lock");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS));

        mainPanel.setBorder(
                new EmptyBorder(
                        35,
                        50,
                        35,
                        50));

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("🔒 NoteSphere");

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30));

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        // =========================
        // SUBTITLE
        // =========================

        JLabel messageLabel =
                new JLabel(
                        "Enter your password to continue");

        messageLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15));

        messageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        // =========================
        // PASSWORD
        // =========================

        passwordField =
                new JPasswordField();

        passwordField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        18));

        passwordField.setMaximumSize(
                new Dimension(
                        300,
                        40));

        // =========================
        // UNLOCK BUTTON
        // =========================

        JButton unlockButton =
                new JButton("🔓 Unlock");

        unlockButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16));

        unlockButton.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        // =========================
        // UNLOCK ACTION
        // =========================

        unlockButton.addActionListener(e ->
                unlock());

        // Press ENTER to unlock

        passwordField.addActionListener(e ->
                unlock());

        // =========================
        // ADD COMPONENTS
        // =========================

        mainPanel.add(titleLabel);

        mainPanel.add(
                Box.createVerticalStrut(15));

        mainPanel.add(messageLabel);

        mainPanel.add(
                Box.createVerticalStrut(25));

        mainPanel.add(passwordField);

        mainPanel.add(
                Box.createVerticalStrut(25));

        mainPanel.add(unlockButton);

        add(mainPanel);
    }

    // =========================
    // UNLOCK
    // =========================

    private void unlock() {

        String password =
                new String(
                        passwordField.getPassword());

        if (PasswordManager
                .checkPassword(password)) {

            Dashboard dashboard =
                    new Dashboard();

            dashboard.setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Incorrect password!",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE);

            passwordField.setText("");

            passwordField.requestFocus();
        }
    }
}