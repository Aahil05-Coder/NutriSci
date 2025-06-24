package ui;

import model.UserProfile;
import controller.LogMealController;
import controller.SwapController;
import controller.VisualizationController;
import javax.swing.*;

public class MainTabbedPanel extends JPanel {
    public MainTabbedPanel(UserProfile user) {
        setLayout(new java.awt.BorderLayout());
        JTabbedPane tabs = new JTabbedPane();

        // Log Meal tab
        LogMealPanel logMealPanel = new LogMealPanel(user);
        new LogMealController(logMealPanel, user);
        tabs.addTab("Log Meal", logMealPanel);
        
        SwapPanel SwapPanel = new SwapPanel(user);
        new SwapController(SwapPanel, user);
        tabs.addTab("Swap", SwapPanel);
        
        VisualizationPanel VisualizationPanel = new VisualizationPanel(user);
        new VisualizationController(VisualizationPanel, user);
        tabs.addTab("Visualization", VisualizationPanel);

        add(tabs, java.awt.BorderLayout.CENTER);
    }
}
