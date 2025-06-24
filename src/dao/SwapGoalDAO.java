// src/dao/SwapGoalDAO.java
package dao;

import model.SwapGoal;
import util.DatabaseInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persist and retrieve swap goals.
 * Assumes table `swap_goal(id AUTO_INCREMENT, nutrient VARCHAR, direction VARCHAR, amount DOUBLE)`.
 */
public class SwapGoalDAO {
    private final Connection conn;

    public SwapGoalDAO() {
        this.conn = DatabaseInitializer.getConnection();
    }

    /** Insert a new swap goal row. */
    public void insert(SwapGoal g) throws SQLException {
        String sql = "INSERT INTO swap_goal (nutrient,direction,amount) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, g.getNutrient());
            ps.setString(2, g.getDirection());
            ps.setDouble(3, g.getAmount());
            ps.executeUpdate();
        }
    }

    /** Return all saved swap goals. */
    public List<SwapGoal> findAll() throws SQLException {
        String sql = "SELECT id,nutrient,direction,amount FROM swap_goal";
        List<SwapGoal> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int    id        = rs.getInt("id");
                String nutrient  = rs.getString("nutrient");
                String dir       = rs.getString("direction");
                double amount    = rs.getDouble("amount");
                list.add(new SwapGoal(id, nutrient, dir, amount));
            }
        }
        return list;
    }
}
