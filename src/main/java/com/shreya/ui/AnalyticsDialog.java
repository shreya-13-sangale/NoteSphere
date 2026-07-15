   

package com.shreya.ui;

import com.shreya.model.Note;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalyticsDialog extends JDialog {

    public AnalyticsDialog(JFrame parent, List<Note> notes) {

        super(parent, "📊 NoteSphere Analytics", true);

        setSize(750, 550);
        setLocationRelativeTo(parent);

        // =========================
        // COUNT NOTES
        // =========================

        int work = 0;
        int study = 0;
        int personal = 0;
        int others = 0;

        int pinned = 0;
        int favorite = 0;

        for (Note note : notes) {

            // Ignore deleted notes
            if (note.isDeleted()) {
                continue;
            }

            if (note.isPinned()) {
                pinned++;
            }

            if (note.isFavorite()) {
                favorite++;
            }

            switch (note.getCategory()) {

                case "Work":
                    work++;
                    break;

                case "Study":
                    study++;
                    break;

                case "Personal":
                    personal++;
                    break;

                default:
                    others++;
                    break;
            }
        }

        // =========================
        // CREATE PIE DATA
        // =========================

        DefaultPieDataset<String> dataset =
                new DefaultPieDataset<>();

        if (work > 0) {
            dataset.setValue("Work", work);
        }

        if (study > 0) {
            dataset.setValue("Study", study);
        }

        if (personal > 0) {
            dataset.setValue("Personal", personal);
        }

        if (others > 0) {
            dataset.setValue("Others", others);
        }

        // =========================
        // CREATE CHART
        // =========================

        JFreeChart chart =
                ChartFactory.createPieChart(
                        "Notes by Category",
                        dataset,
                        true,
                        true,
                        false
                );

        ChartPanel chartPanel =
                new ChartPanel(chart);

        // =========================
        // SUMMARY
        // =========================

        int total =
                work + study + personal + others;

        JLabel summaryLabel =
                new JLabel(
                        "Total: " + total
                                + "     📌 Pinned: " + pinned
                                + "     ⭐ Favorites: " + favorite
                );

        summaryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18));

        summaryLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        summaryLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        15,
                        10));

        // =========================
        // ADD COMPONENTS
        // =========================

        setLayout(
                new BorderLayout());

        add(
                summaryLabel,
                BorderLayout.NORTH);

        add(
                chartPanel,
                BorderLayout.CENTER);
    }
}