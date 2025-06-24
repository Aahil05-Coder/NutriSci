package dao;

import model.MealRecord;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MealRecordDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:./nutrisci_db", "sa", "");
    }

    public void insert(MealRecord record) {
        String sql = "INSERT INTO meal_record (user_id, date, meal_type, ingredient, quantity, unit) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, record.getUserId());
            ps.setDate(2, Date.valueOf(record.getDate()));
            ps.setString(3, record.getMealType());
            ps.setString(4, record.getIngredient());
            ps.setString(5, record.getQuantity());
            ps.setString(6, record.getUnit());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<List<MealRecord>> getMealsByUser(int userId) {
        String sql = "SELECT * FROM meal_record WHERE user_id = ? ORDER BY date DESC, meal_type";
        List<List<MealRecord>> grouped = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            List<MealRecord> currentMeal = new ArrayList<>();
            LocalDate lastDate = null;
            String lastType = null;
            while (rs.next()) {
                LocalDate d = rs.getDate("date").toLocalDate();
                String t = rs.getString("meal_type");
                if (lastDate == null || !d.equals(lastDate) || !t.equals(lastType)) {
                    if (!currentMeal.isEmpty()) grouped.add(currentMeal);
                    currentMeal = new ArrayList<>();
                    lastDate = d;
                    lastType = t;
                }
                currentMeal.add(new MealRecord(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        d,
                        t,
                        rs.getString("ingredient"),
                        rs.getString("quantity"),
                        rs.getString("unit")
                ));
            }
            if (!currentMeal.isEmpty()) grouped.add(currentMeal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grouped;
    }

    public void deleteMeal(int userId, LocalDate date, String mealType) {
        String sql = "DELETE FROM meal_record WHERE user_id=? AND date=? AND meal_type=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(date));
            ps.setString(3, mealType);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<MealRecord> findAllByUser(int userId) {
        String sql = "SELECT * FROM meal_record WHERE user_id = ?";
        List<MealRecord> all = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate d = rs.getDate("date").toLocalDate();
                all.add(new MealRecord(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        d,
                        rs.getString("meal_type"),
                        rs.getString("ingredient"),
                        rs.getString("quantity"),
                        rs.getString("unit"),
                        rs.getDouble("calories") // Make sure this column exists!
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

}
