package Repository;

import Entity.Notification;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRepository {
    public void createNotification (Notification notif) {
        String sql = "INSERT INTO notification (id, user_id, message, status) " +
                "VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, notif.getId());
            pstmt.setString(2, notif.getUser_id());
            pstmt.setString(3, notif.getMessage());
            pstmt.setString(4, notif.getStatus());

            pstmt.executeUpdate();
            System.out.println("Created notification with id: "+ notif.getId());
        } catch (SQLException e) {
            System.out.println("Could not create notification.");
        }
    }

    public void updateStatus (String id) {
        String sql = "UPDATE notification " +
                "SET status = ? " +
                "WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "read");
            pstmt.setString(2, id);

            pstmt.executeUpdate();
            System.out.println("Updated notification status for: "+ id);
        } catch (SQLException e) {
            System.out.println("Could not update notification status.");
        }
    }

    public Notification findNotificationById (String id) {
        String sql = "SELECT id, user_id, message, status " +
                "FROM notification " +
                "WHERE id = ?";

        Notification notif = null;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                notif = new Notification(rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("message"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find notification.");
        }

        return notif;
    }
}
