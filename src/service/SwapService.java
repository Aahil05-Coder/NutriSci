// src/service/SwapService.java
package service;

import model.SwapGoal;
import dao.SwapGoalDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages swap goals and generates simple swap suggestions.
 */
public class SwapService {
    private final SwapGoalDAO goalDao = new SwapGoalDAO();

    /**
     * Persist one new swap goal.
     */
    public void addGoal(SwapGoal goal) throws Exception {
        goalDao.insert(goal);
    }

    /**
     * Fetch all saved goals.
     */
    public List<SwapGoal> getAllGoals() throws Exception {
        return goalDao.findAll();
    }

    /**
     * Generate swap suggestions based on each goal.
     * Returns a List of String[]{originalIngredient, suggestedIngredient, expectedChange}.
     */
    public List<String[]> generateSuggestions() throws Exception {
        List<String[]> suggestions = new ArrayList<>();
        for (SwapGoal g : goalDao.findAll()) {
            String nut = g.getNutrient();
            String dir = g.getDirection();
            double amt = g.getAmount();
            // Simple demo logic:
            if ("Fiber".equalsIgnoreCase(nut) && "INCREASE".equalsIgnoreCase(dir)) {
                // Example: swap white to whole wheat
                suggestions.add(new String[]{
                    "White Bread",
                    "Whole Wheat Bread",
                    "+" + amt + " g"
                });
            } else if ("Calories".equalsIgnoreCase(nut) && "DECREASE".equalsIgnoreCase(dir)) {
                suggestions.add(new String[]{
                    "Fried Chicken",
                    "Grilled Chicken",
                    "-" + amt + " kcal"
                });
            } else {
                // Fallback: no suggestion
                suggestions.add(new String[]{
                    "-",
                    "-",
                    "no suggestion"
                });
            }
        }
        return suggestions;
    }
}
