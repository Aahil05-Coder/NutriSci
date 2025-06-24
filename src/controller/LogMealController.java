package controller;

import model.UserProfile;
import model.MealRecord;
import service.MealService;
import ui.LogMealPanel;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogMealController {
    private final LogMealPanel panel;
    private final UserProfile user;
    private final MealService service = new MealService();

    public LogMealController(LogMealPanel panel, UserProfile user) {
        this.panel = panel;
        this.user = user;
        refreshSavedMeals();

        panel.getAddRowButton().addActionListener(e -> panel.addMealEntryRow());
        panel.getDeleteRowButton().addActionListener(e -> panel.deleteSelectedMealEntryRow());
        panel.getSaveMealButton().addActionListener(e -> {
            List<String[]> entries = panel.getMealEntries();
            if (entries.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please add at least one ingredient!");
                return;
            }
            List<MealRecord> mealRecords = new ArrayList<>();
            LocalDate date = LocalDate.parse(panel.getDateText());
            String mealType = panel.getMealType();
            for (String[] entry : entries) {
                // Set calories to 0.0 for now (replace with actual calculation later)
                double calories = 0.0;
                mealRecords.add(new MealRecord(
                    user.getId(),
                    0, date,
                    mealType,
                    entry[0], entry[1], entry[2],
                    calories
                ));
            }
            service.addMeal(mealRecords);
            panel.clearMealEntryTable();
            refreshSavedMeals();
        });

        panel.getDeleteMealButton().addActionListener(e -> {
            int selected = panel.getSelectedSavedMealRow();
            if (selected >= 0) {
                String dateStr = panel.getSavedMealsTableValue(selected, 0);
                String mealType = panel.getSavedMealsTableValue(selected, 1);
                LocalDate date = LocalDate.parse(dateStr);
                service.deleteMeal(user.getId(), date, mealType);
                refreshSavedMeals();
            }
        });

        panel.getSavedMealsTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int idx = panel.getSelectedSavedMealRow();
                if (idx >= 0) {
                    List<List<MealRecord>> all = service.getMealsByUser(user.getId());
                    if (idx < all.size()) {
                        List<MealRecord> meal = all.get(idx);
                        List<String[]> detail = new ArrayList<>();
                        for (MealRecord mr : meal) {
                            detail.add(new String[]{mr.getIngredient(), mr.getQuantity(), mr.getUnit()});
                        }
                        panel.showMealDetailsDialog(detail);
                    }
                }
            }
        });
    }

    private void refreshSavedMeals() {
        List<List<MealRecord>> meals = service.getMealsByUser(user.getId());
        panel.setSavedMeals(meals);
    }
}
