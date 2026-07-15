package com.shreya.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatsPanel extends JPanel {

    private JLabel totalLabel;
    private JLabel pinnedLabel;
    private JLabel workLabel;
    private JLabel studyLabel;
    private JLabel personalLabel;

    public StatsPanel() {

        setLayout(new GridLayout(1,5,15,15));
        setBorder(new EmptyBorder(10,10,10,10));

        totalLabel = new JLabel("📝 Total : 0");
        pinnedLabel = new JLabel("📌 Pinned : 0");
        workLabel = new JLabel("💼 Work : 0");
        studyLabel = new JLabel("📚 Study : 0");
        personalLabel = new JLabel("🏠 Personal : 0");

        Font font = new Font("Segoe UI", Font.BOLD, 16);

        totalLabel.setFont(font);
        pinnedLabel.setFont(font);
        workLabel.setFont(font);
        studyLabel.setFont(font);
        personalLabel.setFont(font);

        add(totalLabel);
        add(pinnedLabel);
        add(workLabel);
        add(studyLabel);
        add(personalLabel);

    }

    public void updateStats(int total,
                            int pinned,
                            int work,
                            int study,
                            int personal){

        totalLabel.setText("📝 Total : " + total);
        pinnedLabel.setText("📌 Pinned : " + pinned);
        workLabel.setText("💼 Work : " + work);
        studyLabel.setText("📚 Study : " + study);
        personalLabel.setText("🏠 Personal : " + personal);

    }

}
