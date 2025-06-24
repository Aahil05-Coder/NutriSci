package model;

import java.time.LocalDate;

public class MealRecord {
    private int id;
    private int userId;
    private LocalDate date;
    private String mealType;
    private String ingredient;
    private String quantity;
    private String unit;
    private double calories; // Add this!

    // Updated constructor
    public MealRecord(int id, int userId, LocalDate date, String mealType, String ingredient, String quantity, String unit, double calories) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.mealType = mealType;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.calories = calories;
    }

    // Existing constructor (for non-calorie use, set calories to 0)
    public MealRecord(int id, int userId, LocalDate date, String mealType, String ingredient, String quantity, String unit) {
        this(id, userId, date, mealType, ingredient, quantity, unit, 0.0);
    }

    // ... (other constructors as needed)

    // --- Getters ---
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public LocalDate getDate() { return date; }
    public String getMealType() { return mealType; }
    public String getIngredient() { return ingredient; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public double getCalories() { return calories; } // <-- Add this!
}
