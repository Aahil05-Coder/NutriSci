package ui;

import model.UserProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SwapPanel extends JPanel {
    private final UserProfile user;
    private final JComboBox<String> nutrientBox = new JComboBox<>(new String[]{"Calories", "Protein", "Fiber"});
    private final JComboBox<String> directionBox = new JComboBox<>(new String[]{"INCREASE", "DECREASE"});
    private final JTextField amountField = new JTextField(5);
    private final JButton addGoalButton = new JButton("Add Goal");
    private final JButton suggestButton = new JButton("Generate Suggestions");
    private final DefaultTableModel goalTableModel = new DefaultTableModel(new Object[]{"Nutrient", "Direction", "Amount"}, 0);
    private final JTable goalTable = new JTable(goalTableModel);

    private final DefaultTableModel suggestionTableModel = new DefaultTableModel(
            new Object[]{"Current Ingredient", "Suggested Swap", "Expected Change"}, 0);
    private final JTable suggestionTable = new JTable(suggestionTableModel);

    public SwapPanel(UserProfile user) {
        this.user = user;
        setLayout(new BorderLayout(8, 8));

        // Top controls for defining goals
        JPanel top = new JPanel();
        top.add(new JLabel("Nutrient:"));
        top.add(nutrientBox);
        top.add(directionBox);
        top.add(new JLabel("Amount:"));
        top.add(amountField);
        top.add(addGoalButton);

        add(top, BorderLayout.NORTH);

        // Center: goals table
        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.add(new JLabel("Goals:"), BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(goalTable), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // South: generate suggestions and show results
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.add(suggestButton, BorderLayout.NORTH);
        bottom.add(new JScrollPane(suggestionTable), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    // --- Getters for the controller ---
    public JButton getAddGoalButton() { return addGoalButton; }
    public JButton getGenerateSuggestionsButton() { return suggestButton; }
    public String getSelectedNutrient() { return (String) nutrientBox.getSelectedItem(); }
    public String getSelectedDirection() { return (String) directionBox.getSelectedItem(); }
    public String getAmountText() { return amountField.getText().trim(); }
    public DefaultTableModel getGoalModel() { return goalTableModel; }
    public DefaultTableModel getSuggestionModel() { return suggestionTableModel; }
}
