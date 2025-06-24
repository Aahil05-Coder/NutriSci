// src/model/Ingredient.java
package model;

import java.util.Objects;

/**
 * Represents a food ingredient with its per-unit calories.
 */
public class Ingredient {
    private int id;
    private String name;
    private double caloriesPerUnit;

    /** No-arg constructor */
    public Ingredient() { }

    public Ingredient(String name, double caloriesPerUnit) {
        this.name = name;
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public Ingredient(int id, String name, double caloriesPerUnit) {
        this(name, caloriesPerUnit);
        this.id = id;
    }

    // ── Getters & Setters ────────────────────────────────────────

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getName()                  { return name; }
    public void setName(String name)         { this.name = name; }

    public double getCaloriesPerUnit()       { return caloriesPerUnit; }
    public void setCaloriesPerUnit(double c) { this.caloriesPerUnit = c; }

    // ── Helpers ──────────────────────────────────────────────────

    /**
     * Calculates calories for a given quantity.
     * @param quantity number of units
     * @return total calories
     */
    public double caloriesFor(double quantity) {
        return caloriesPerUnit * quantity;
    }

    @Override
    public String toString() {
        return String.format(
            "Ingredient[id=%d, name=%s, cal/unit=%.2f]",
            id, name, caloriesPerUnit
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient i = (Ingredient) o;
        return id == i.id
            && Double.compare(i.caloriesPerUnit, caloriesPerUnit) == 0
            && Objects.equals(name, i.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, caloriesPerUnit);
    }
}
