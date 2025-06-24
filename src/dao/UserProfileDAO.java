package dao;

import model.UserProfile;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:./nutrisci_db", "sa", "");
    }

    public List<UserProfile> getAll() {
        List<UserProfile> users = new ArrayList<>();
        String sql = "SELECT * FROM user_profile";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new UserProfile(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getDate("dob").toLocalDate(),
                        rs.getInt("height_cm"),
                        rs.getInt("weight_kg")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void create(UserProfile user) {
        String sql = "INSERT INTO user_profile (name, gender, dob, height_cm, weight_kg) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getGender());
            ps.setDate(3, Date.valueOf(user.getDob()));
            ps.setInt(4, user.getHeightCm());
            ps.setInt(5, user.getWeightKg());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(UserProfile user) {
        String sql = "UPDATE user_profile SET name=?, gender=?, dob=?, height_cm=?, weight_kg=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getGender());
            ps.setDate(3, Date.valueOf(user.getDob()));
            ps.setInt(4, user.getHeightCm());
            ps.setInt(5, user.getWeightKg());
            ps.setInt(6, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
