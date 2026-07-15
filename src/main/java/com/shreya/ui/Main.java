package com.shreya.ui;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // =========================
        // APPLY FLATLAF DARK THEME
        // =========================

        FlatDarkLaf.setup();

        // =========================
        // START APPLICATION
        // =========================

        SwingUtilities.invokeLater(() -> {

            try {

                // Open the Lock Screen first
                LockScreen lockScreen =
                        new LockScreen();

                lockScreen.setLocationRelativeTo(null);

                lockScreen.setVisible(true);

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Unable to start NoteSphere.\n\n"
                                + e.getMessage(),
                        "Application Error",
                        JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();
            }

        });

    }
}