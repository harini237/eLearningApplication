package Repository;

import Entity.PendingApproval;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PendingApprovalRepository {
    public void createApproval (PendingApproval approval) {
        String sql = "INSERT INTO pending_approval (course_token, student_id)" +
                "VALUES (?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, approval.getCourse_token());
            pstmt.setString(2, approval.getStudent_id());

            pstmt.executeUpdate();
            System.out.println("Student "+ approval.getStudent_id()+ " pending approval for "+ approval.getCourse_token());
        } catch (SQLException e) {
            System.out.println("Could not create approval request.");
        }
    }

    public List<PendingApproval> findApprovalsByCourse (String course_token) {
        List<PendingApproval> approvals = new ArrayList<>();

        String sql = "SELECT course_token, student_id FROM pending_approval" +
                "WHERE course_token = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course_token);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PendingApproval approval = new PendingApproval(rs.getString("student_id"),rs.getString("course_token"));
                approvals.add(approval);
            }
        } catch (SQLException e) {
            System.out.println("Could not find pending approvals.");
        }

        return approvals;
    }

    public List<PendingApproval> findApprovalsByStudent (String student_id) {
        List<PendingApproval> approvals = new ArrayList<>();

        String sql = "SELECT course_token, student_id FROM pending_approval" +
                "WHERE student_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, student_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PendingApproval approval = new PendingApproval(rs.getString("student_id"),rs.getString("course_token"));
                approvals.add(approval);
            }
        } catch (SQLException e) {
            System.out.println("Could not find pending approvals.");
        }

        return approvals;
    }

    public void deletePendingApproval (String student_id, String course_token) {
        String sql = "DELETE FROM pending_approval" +
                "WHERE student_id = ?" +
                "AND course_token = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, student_id);
            pstmt.setString(2, course_token);

            pstmt.executeUpdate();
            System.out.println(student_id+ " deleted approval for "+ course_token);
        } catch (SQLException e) {
            System.out.println("Could not delete pending approval.");
        }
    }
}
