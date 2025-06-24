// src/dao/IngredientDAO.java
package dao;

import model.Ingredient;
import util.DatabaseInitializer;

import java.sql.*;

/**
 * CRUD for ingredients table.
 * Assumes a table `ingredient(id INT AUTO_INCREMENT, name VARCHAR, calories_per_unit DOUBLE)`.
 */
public class IngredientDAO {
    private final Connection conn;

    public IngredientDAO() {
        this.conn = DatabaseInitializer.getConnection();
    }

    /**
     * Look up an Ingredient by name.
     * @throws SQLException if DB error
     */
    public Ingredient findByName(String name) throws SQLException {
        String sql = "SELECT id,name,calories_per_unit FROM ingredient WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                int    id   = rs.getInt("id");
                String nm   = rs.getString("name");
                double cpu  = rs.getDouble("calories_per_unit");
                return new Ingredient(id, nm, cpu);
            }
        }
    }

    /**
     * Multiply quantity * caloriesPerUnit. Returns 0 if ingredient not found.
     */
    public double calculateCalories(String name, double qty) throws SQLException {
        Ingredient ing = findByName(name);
        return (ing == null)
            ? 0
            : ing.getCaloriesPerUnit() * qty;
    }
}
