package Repository;

import Entity.Permission;
import Entity.Role;
import Entity.User;
import Util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final RoleRepository roleRepository = new RoleRepository();
    private final RolePermissionMapRepository rolePermissionMapRepository = new RolePermissionMapRepository();


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
        String sql = "SELECT user.id, first_name, last_name, email, password, created_at, created_by, role_id, is_pwd_reset_req, r.name as role " +
                "FROM user JOIN role r on r.id = user.role_id  WHERE user.id = ?";
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
                    user.setRole(rs.getString("role"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    public User getUserByEmail(String emailId) {
        String sql = "SELECT user.id, first_name, last_name, email, password, created_at, created_by, role_id, is_pwd_reset_req, r.name as role " +
                "FROM user JOIN role r on r.id = user.role_id  WHERE user.email = ?";
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
                    user.setRole(rs.getString("role"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    public Role getUserRoles(int userId) {
        String sql = "SELECT r.id, r.name, r.description FROM role r " +
                "JOIN user ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return new Role(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserWithRoleUsingEmail(String emailId){
        String sql = "SELECT user.id, first_name, last_name, email, password, created_at, created_by, role_id, is_pwd_reset_req, r.name as \"role\" " +
                "FROM user JOIN role r on r.id = user.role_id WHERE email = ? ";
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
                    user.setRole(rs.getString("role"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    public boolean updatePassword(String userId, String hashedPassword) {
        String sql = "UPDATE user SET password = ?, is_pwd_reset_req = 0 WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, userId);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful

        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }


    public List<Permission> getUserPermissions(int userId) {
        Role role = getUserRoles(userId);
        return rolePermissionMapRepository.getPermissionsForRole(role.getId());
    }

    private String generateUserId(String firstName, String lastName) {
        LocalDateTime now = LocalDateTime.now();
        String monthYear = String.format("%02d%02d", now.getMonthValue(), now.getYear() % 100);
        return firstName.substring(0, 2).toLowerCase() + lastName.substring(0, 2).toLowerCase() + monthYear;
    }
}
