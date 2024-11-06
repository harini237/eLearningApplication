package Repository;

import Entity.ActivityAttempt;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityAttemptRepository {

    // Method to create a new activity attempt record
    public void createActivityAttempt(ActivityAttempt attempt) {
        String sql = "INSERT INTO activity_attempt (activity_id, section_id, chapter_id, textbook_id, student_id, content_block_id, selected_option_ID, attempt_score, attempted_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, attempt.getActivityId());
            pstmt.setString(2, attempt.getSectionId());
            pstmt.setString(3, attempt.getChapterId());
            pstmt.setInt(4, attempt.getTextbookId());
            pstmt.setString(5, attempt.getStudentId());
            pstmt.setString(6, attempt.getContentBlockId());
            pstmt.setInt(7, attempt.getSelectedOptionId());
            pstmt.setInt(8, attempt.getAttemptScore());
            pstmt.setTimestamp(9, attempt.getAttemptedAt());

            pstmt.executeUpdate();
            System.out.println("Activity attempt recorded successfully.");

        } catch (SQLException e) {
            System.err.println("Error creating activity attempt: " + e.getMessage());
        }
    }

    // Method to retrieve attempts by student ID
    public List<ActivityAttempt> getAttemptsByStudentId(String studentId) {
        String sql = "SELECT * FROM activity_attempt WHERE student_id = ?";
        List<ActivityAttempt> attempts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ActivityAttempt attempt = new ActivityAttempt(
                        rs.getString("activity_id"),
                        rs.getString("section_id"),
                        rs.getString("chapter_id"),
                        rs.getInt("textbook_id"),
                        rs.getString("student_id"),
                        rs.getString("content_block_id"),
                        rs.getInt("selected_option_ID"),
                        rs.getInt("attempt_score"),
                        rs.getTimestamp("attempted_at")
                );
                attempts.add(attempt);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving attempts by student ID: " + e.getMessage());
        }

        return attempts;
    }

    // Method to calculate total participation points for a student
    public int calculateTotalPoints(String studentId) {
        String sql = "SELECT SUM(attempt_score) AS total_points FROM activity_attempt WHERE student_id = ?";
        int totalPoints = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalPoints = rs.getInt("total_points");
            }

        } catch (SQLException e) {
            System.err.println("Error calculating total participation points: " + e.getMessage());
        }

        return totalPoints;
    }
}

