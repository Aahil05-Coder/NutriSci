package controller;

import model.UserProfile;
import service.VisualizationService;
import ui.VisualizationPanel;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.util.Map;

public class VisualizationController {
    public VisualizationController(VisualizationPanel view, UserProfile user) {
        VisualizationService service = new VisualizationService();

        view.getRefreshButton().addActionListener(e -> {
            try {
                // user-aware service call
                Map<String, Double> dist = service.computeAverageDistribution(user.getId());
                DefaultPieDataset ds = new DefaultPieDataset();
                for (Map.Entry<String, Double> en : dist.entrySet()) {
                    ds.setValue(en.getKey(), en.getValue());
                }
                JFreeChart chart = ChartFactory.createPieChart(
                        "Avg Calories by Meal Type", ds, true, true, false);
                view.showChart(chart);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Error generating chart:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Optionally auto-load the chart on panel creation
        view.getRefreshButton().doClick();
    }
}
