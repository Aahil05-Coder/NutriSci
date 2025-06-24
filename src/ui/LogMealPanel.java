package ui;

import model.UserProfile;
import model.MealRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class LogMealPanel extends JPanel {
    private final UserProfile user;
    private final JTextField dateField = new JTextField(10);
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{"BREAKFAST", "LUNCH", "DINNER", "SNACK"});
    private final DefaultTableModel entryTableModel = new DefaultTableModel(new Object[]{"Ingredient", "Quantity", "Unit"}, 0);
    private final JTable entryTable = new JTable(entryTableModel);
    private final JButton addRowButton = new JButton("Add Row");
    private final JButton deleteRowButton = new JButton("Delete Row");
    private final JButton saveMealButton = new JButton("Save Meal");

    // Saved meals table
    private final DefaultTableModel savedMealsTableModel = new DefaultTableModel(new Object[]{"Date", "Meal Type", "Items"}, 0);
    private final JTable savedMealsTable = new JTable(savedMealsTableModel);
    private final JButton deleteMealButton = new JButton("Delete Meal");

    // Store meal details for expandable dialog
    private final List<List<String[]>> savedMealsDetails = new ArrayList<>();

    // Example ingredient/unit data (replace with DB or CSV load if needed)
    private final List<String> ingredientList = List.of(
            "Beef pot roast, with browned potatoes", "Eggs", "Bread", "Apple", "Milk, fluid, sheep");
    private final List<String> unitList = List.of("g", "kg", "cup", "package", "can (284 ml)");

    public LogMealPanel(UserProfile user) {
        this.user = user;
        setLayout(new BorderLayout(8, 8));

        // --- Top: Date/Meal type ---
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        dateField.setText(java.time.LocalDate.now().toString());
        top.add(new JLabel("Date (YYYY-MM-DD):"));
        top.add(dateField);
        top.add(new JLabel("Meal Type:"));
        top.add(typeBox);

        // --- Meal entry table ---
        setUpIngredientComboBox();
        setUpUnitComboBox();
        JScrollPane entryScroll = new JScrollPane(entryTable);

        // --- Button row (add/delete/save) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        buttonPanel.add(addRowButton);
        buttonPanel.add(deleteRowButton);
        buttonPanel.add(saveMealButton);

        // --- Combine: top + table + buttons ---
        JPanel entrySection = new JPanel();
        entrySection.setLayout(new BoxLayout(entrySection, BoxLayout.Y_AXIS));
        entrySection.add(top);
        entrySection.add(entryScroll);
        entrySection.add(buttonPanel);

        add(entrySection, BorderLayout.CENTER);

        // --- Saved meals table + Delete button ---
        JPanel savedPanel = new JPanel(new BorderLayout(8, 8));
        savedPanel.add(new JLabel("Saved Meals"), BorderLayout.NORTH);

        JScrollPane savedMealsScroll = new JScrollPane(savedMealsTable);
        savedMealsScroll.setPreferredSize(new Dimension(500, 120));
        savedPanel.add(savedMealsScroll, BorderLayout.CENTER);
        savedPanel.add(deleteMealButton, BorderLayout.SOUTH);

        // --- Add saved meals panel at bottom ---
        add(savedPanel, BorderLayout.SOUTH);

        // --- Double click (or single click) to expand meal details ---
        savedMealsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = savedMealsTable.getSelectedRow();
                if (row >= 0 && row < savedMealsDetails.size()) {
                    List<String[]> mealDetails = savedMealsDetails.get(row);
                    showMealDetailsDialog(mealDetails);
                }
            }
        });
    }

    // --- Add/remove entry rows (for controller integration) ---
    public void addMealEntryRow() {
        entryTableModel.addRow(new Object[]{ingredientList.get(0), "", unitList.get(0)});
    }

    public void deleteSelectedMealEntryRow() {
        int row = entryTable.getSelectedRow();
        if (row >= 0) entryTableModel.removeRow(row);
    }

    public void clearMealEntryTable() {
        entryTableModel.setRowCount(0);
    }

    // --- Utility for displaying details dialog ---
    public void showMealDetailsDialog(List<String[]> mealDetails) {
        String[] columns = {"Ingredient", "Quantity", "Unit"};
        String[][] data = mealDetails.toArray(new String[0][]);
        JTable detailsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(detailsTable);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        JOptionPane.showMessageDialog(this, scrollPane, "Meal Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- Table editor setup ---
    private void setUpIngredientComboBox() {
        TableColumn ingCol = entryTable.getColumnModel().getColumn(0);
        JComboBox<String> ingCombo = new JComboBox<>(ingredientList.toArray(new String[0]));
        ingCol.setCellEditor(new DefaultCellEditor(ingCombo));
    }

    private void setUpUnitComboBox() {
        TableColumn unitCol = entryTable.getColumnModel().getColumn(2);
        JComboBox<String> unitCombo = new JComboBox<>(unitList.toArray(new String[0]));
        unitCol.setCellEditor(new DefaultCellEditor(unitCombo));
    }

    // --- Getters for controller/service ---
    public String getDateText() { return dateField.getText(); }
    public String getMealType() { return (String) typeBox.getSelectedItem(); }
    public List<String[]> getMealEntries() {
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < entryTableModel.getRowCount(); i++) {
            Object ing = entryTableModel.getValueAt(i, 0);
            Object qty = entryTableModel.getValueAt(i, 1);
            Object unit = entryTableModel.getValueAt(i, 2);
            if (ing != null && qty != null && unit != null) {
                result.add(new String[]{ing.toString(), qty.toString(), unit.toString()});
            }
        }
        return result;
    }
    public JButton getAddRowButton() { return addRowButton; }
    public JButton getDeleteRowButton() { return deleteRowButton; }
    public JButton getSaveMealButton() { return saveMealButton; }
    public JButton getDeleteMealButton() { return deleteMealButton; }
    public int getSelectedSavedMealRow() { return savedMealsTable.getSelectedRow(); }
    public String getSavedMealsTableValue(int row, int col) {
        return savedMealsTableModel.getValueAt(row, col).toString();
    }
    public JTable getSavedMealsTable() { return savedMealsTable; }
    public void setSavedMeals(List<List<MealRecord>> grouped) {
        savedMealsTableModel.setRowCount(0);
        savedMealsDetails.clear();
        for (List<MealRecord> meal : grouped) {
            if (meal.isEmpty()) continue;
            savedMealsTableModel.addRow(new Object[]{
                    meal.get(0).getDate().toString(),
                    meal.get(0).getMealType(),
                    meal.size()
            });
            List<String[]> detail = new ArrayList<>();
            for (MealRecord mr : meal) {
                detail.add(new String[]{mr.getIngredient(), mr.getQuantity(), mr.getUnit()});
            }
            savedMealsDetails.add(detail);
        }
    }
}
