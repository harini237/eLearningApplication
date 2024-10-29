package Repository;

import Entity.Permission;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolePermissionMapRepository {
    public List<Permission> getPermissionsForRole(int roleId) {
        List<Permission> permissions = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.description FROM permission p " +
                "JOIN role_permission_map rpm ON p.id = rpm.permission_id " +
                "WHERE rpm.role_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roleId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                permissions.add(new Permission(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissions;
    }
}
