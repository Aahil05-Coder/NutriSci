// src/model/SwapGoal.java
package model;

import java.util.Objects;

/**
 * Represents a user’s goal to increase or decrease a nutrient by a certain amount.
 */
public class SwapGoal {
    private int id;
    private String nutrient;   // e.g. “Fiber”, “Calories”
    private String direction;  // “INCREASE” or “DECREASE”
    private double amount;     // units or grams to adjust

    public SwapGoal() { }

    public SwapGoal(String nutrient, String direction, double amount) {
        this.nutrient  = nutrient;
        this.direction = direction;
        this.amount    = amount;
    }

    public SwapGoal(int id, String nutrient, String direction, double amount) {
        this(nutrient, direction, amount);
        this.id = id;
    }

    // ── Getters & Setters ────────────────────────────────────────

    public int getId()                    { return id; }
    public void setId(int id)             { this.id = id; }

    public String getNutrient()           { return nutrient; }
    public void setNutrient(String n)     { this.nutrient = n; }

    public String getDirection()          { return direction; }
    public void setDirection(String d)    { this.direction = d; }

    public double getAmount()             { return amount; }
    public void setAmount(double a)       { this.amount = a; }

    @Override
    public String toString() {
        return String.format(
            "SwapGoal[id=%d, nutrient=%s, dir=%s, amount=%.2f]",
            id, nutrient, direction, amount
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SwapGoal)) return false;
        SwapGoal g = (SwapGoal) o;
        return id == g.id
            && Double.compare(g.amount, amount) == 0
            && Objects.equals(nutrient, g.nutrient)
            && Objects.equals(direction, g.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nutrient, direction, amount);
    }
}
