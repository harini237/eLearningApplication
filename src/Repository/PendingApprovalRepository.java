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

    public List<String> findPendingApprovalTokensByFaculty (String faculty_id) {
        String sql = "SELECT A.token" +
                "FROM active_course A" +
                "JOIN course C" +
                "ON A.course_id = C.id" +
                "JOIN pending_approval P" +
                "ON A.token = P.course_token" +
                "WHERE C.faculty_id = ?";

        List<String> course_tokens = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, faculty_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                course_tokens.add(rs.getString("A.token"));
            }
        } catch (SQLException e) {
            System.out.println("Could not get course token by faculty.");
        }
        return course_tokens;
    }

    public List<PendingApproval> findPendingApprovalsByFaculty (String faculty_id) {

        List<PendingApproval> pendingApprovals = new ArrayList<>();
        String sql2 = "SELECT course_token, student_id" +
                "FROM pending_approval" +
                "WHERE course_token = ?";

        List<String> course_tokens = this.findPendingApprovalTokensByFaculty(faculty_id);

        if (course_tokens.isEmpty()) {
            System.out.println("No active courses to fetch pending approvals for faculty "+ faculty_id);
        } else {
            try {
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql2);

                for(String token : course_tokens) {
                    pstmt.setString(1, token);
                    ResultSet rs = pstmt.executeQuery();

                    while(rs.next()) {
                        PendingApproval pendingApproval = new PendingApproval(rs.getString("student_id"),
                                rs.getString("course_token"));
                        pendingApprovals.add(pendingApproval);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Could not get pending approvals for active courses.");
            }
        }

        return pendingApprovals;
    }

    public List<PendingApproval> findApprovalsByStudentAndFaculty(String student_id, String faculty_id) {
        List<PendingApproval> approvals = new ArrayList<>();

        String sql = "SELECT student_id, course_token FROM pending_approval" +
                "WHERE student_id = ? AND course_token in " +
                "(SELECT A.token" +
                "FROM active_course A" +
                "JOIN course C" +
                "ON A.course_id = C.id" +
                "JOIN pending_approval P" +
                "ON A.token = P.course_token" +
                "WHERE C.faculty_id = ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, student_id);
            pstmt.setString(2, faculty_id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                PendingApproval pendingApproval = new PendingApproval(rs.getString("student_id"),
                        rs.getString("course_token"));
                approvals.add(pendingApproval);
            }

        } catch (SQLException e) {
            System.out.println("Could not get pending approvals for student & faculty combination.");
        }

        return approvals;
    }
}
