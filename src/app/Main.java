package app;

import model.UserProfile;
import ui.MainTabbedPanel;
import ui.ProfilePanel;
import util.DatabaseInitializer;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.init();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NutriSci");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Show ProfilePanel first
            ProfilePanel profilePanel = new ProfilePanel();
            frame.setContentPane(profilePanel);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Listener to swap panel after user selection
            profilePanel.setUserSelectedListener(selectedUser -> {
                MainTabbedPanel mainPanel = new MainTabbedPanel(selectedUser);
                frame.setContentPane(mainPanel);
                frame.revalidate();
                frame.repaint();
                frame.setTitle("NutriSci - User: " + selectedUser.getName());
            });
        });
    }
}
