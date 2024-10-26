package Repository;

import Entity.User;
import Util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class UserRepository {

    public void createUser(User user) {
        // Generate unique user ID based on rules
        String userId = generateUserId(user.getFirstName(), user.getLastName());
        user.setId(userId);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        String sql = "INSERT INTO user (id, first_name, last_name, email, password, created_at, created_by, role_id, is_pwd_reset_req) "
                + "VALUES (?, ?, ?, ?, ?, ?, NULL, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setTimestamp(6, user.getCreatedAt());
            pstmt.setInt(7, user.getRoleId());
            pstmt.setBoolean(8, user.getIsPwdResetReq());

            pstmt.executeUpdate();
            System.out.println("User created successfully with ID: " + userId);

        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
    }

    public User getUserById(String userId) {
        String sql = "SELECT id, first_name, last_name, email, password, created_at, created_by, role_id, is_pwd_reset_req " +
                "FROM user WHERE id = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getString("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setCreatedBy(rs.getString("created_by"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setIsPwdResetReq(rs.getBoolean("is_pwd_reset_req"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    public User getUserByEmail(String emailId) {
        String sql = "SELECT id, first_name, last_name, email, password, created_at, created_by, role_id, is_pwd_reset_req " +
                "FROM user WHERE email = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getString("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setCreatedBy(rs.getString("created_by"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setIsPwdResetReq(rs.getBoolean("is_pwd_reset_req"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    private String generateUserId(String firstName, String lastName) {
        LocalDateTime now = LocalDateTime.now();
        String monthYear = String.format("%02d%02d", now.getMonthValue(), now.getYear() % 100);
        return firstName.substring(0, 2).toLowerCase() + lastName.substring(0, 2).toLowerCase() + monthYear;
    }
}
