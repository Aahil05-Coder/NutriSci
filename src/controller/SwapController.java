package controller;

import model.SwapGoal;
import model.UserProfile;
import service.SwapService;
import ui.SwapPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SwapController {
    public SwapController(SwapPanel view, UserProfile user) {
        SwapService service = new SwapService();

        // Add goal
        view.getAddGoalButton().addActionListener(e -> {
            try {
                String nut = view.getSelectedNutrient();
                String dir = view.getSelectedDirection();
                double amt = Double.parseDouble(view.getAmountText());
                // persist user-aware goal
                service.addGoal(new SwapGoal(user.getId(), nut, dir, amt));
                // update table
                DefaultTableModel gm = view.getGoalModel();
                gm.addRow(new Object[]{nut, dir, amt});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Error adding goal:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Generate suggestions
        view.getGenerateSuggestionsButton().addActionListener(e -> {
            try {
                List<String[]> sug = service.generateSuggestions();
                DefaultTableModel sm = view.getSuggestionModel();
                sm.setRowCount(0);
                for (String[] row : sug) {
                    sm.addRow(row);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view,
                        "Error generating suggestions:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
