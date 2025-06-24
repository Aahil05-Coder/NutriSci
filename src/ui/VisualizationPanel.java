package ui;

import model.UserProfile;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class VisualizationPanel extends JPanel {
    private final UserProfile user;
    private final JButton refreshBtn = new JButton("Refresh Chart");
    private JPanel chartHolder = new JPanel(new BorderLayout());

    public VisualizationPanel(UserProfile user) {
        this.user = user;
        setLayout(new BorderLayout(8, 8));

        JLabel label = new JLabel("Visualization for user: " + user.getName(), SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(18f));
        add(label, BorderLayout.NORTH);

        chartHolder.setPreferredSize(new Dimension(500, 300));
        add(chartHolder, BorderLayout.CENTER);

        add(refreshBtn, BorderLayout.SOUTH);
    }

    // Called by controller after computing a new chart
    public void showChart(JFreeChart chart) {
        chartHolder.removeAll();
        chartHolder.add(new ChartPanel(chart), BorderLayout.CENTER);
        chartHolder.revalidate();
        chartHolder.repaint();
    }

    public JButton getRefreshButton() { return refreshBtn; }
}
