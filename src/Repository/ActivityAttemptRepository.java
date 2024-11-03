package Repository;

import Entity.ActivityAttempt;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityAttemptRepository {

    public void createActivityAttempt(ActivityAttempt activityAttempt) {
        String sql = "INSERT INTO activity_attempt (activity_id, section_id, chapter_id, textbook_id, content_block_id, " +
                "student_id, selected_option_id, attempt_score, attempt_timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, activityAttempt.getActivityId());
            pstmt.setInt(2, activityAttempt.getSectionId());
            pstmt.setInt(3, activityAttempt.getChapterId());
            pstmt.setInt(4, activityAttempt.getTextbookId());
            pstmt.setInt(5, activityAttempt.getContentBlockId());
            pstmt.setInt(6, activityAttempt.getStudentId());
            pstmt.setInt(7, activityAttempt.getSelectedOptionId());
            pstmt.setInt(8, activityAttempt.getAttemptScore());
            pstmt.setTimestamp(9, activityAttempt.getAttemptTimestamp());

            pstmt.executeUpdate();

            System.out.println("ActivityAttempt created successfully for Activity ID: " + activityAttempt.getActivityId());

        } catch (SQLException e) {
            System.err.println("Error creating ActivityAttempt: " + e.getMessage());
        }
    }

    public ActivityAttempt getActivityAttemptById(int activityId, int studentId) {
        String sql = "SELECT * FROM activity_attempt WHERE activity_id = ? AND student_id = ?";
        ActivityAttempt activityAttempt = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, activityId);
            pstmt.setInt(2, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    activityAttempt = new ActivityAttempt();
                    activityAttempt.setActivityId(rs.getInt("activity_id"));
                    activityAttempt.setSectionId(rs.getInt("section_id"));
                    activityAttempt.setChapterId(rs.getInt("chapter_id"));
                    activityAttempt.setTextbookId(rs.getInt("textbook_id"));
                    activityAttempt.setContentBlockId(rs.getInt("content_block_id"));
                    activityAttempt.setStudentId(rs.getInt("student_id"));
                    activityAttempt.setSelectedOptionId(rs.getInt("selected_option_id"));
                    activityAttempt.setAttemptScore(rs.getInt("attempt_score"));
                    activityAttempt.setAttemptTimestamp(rs.getTimestamp("attempt_timestamp"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving ActivityAttempt: " + e.getMessage());
        }

        return activityAttempt;
    }

    public List<ActivityAttempt> getAllActivityAttemptsByStudentId(int studentId) {
        String sql = "SELECT * FROM activity_attempt WHERE student_id = ?";
        List<ActivityAttempt> activityAttempts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ActivityAttempt activityAttempt = new ActivityAttempt();
                    activityAttempt.setActivityId(rs.getInt("activity_id"));
                    activityAttempt.setSectionId(rs.getInt("section_id"));
                    activityAttempt.setChapterId(rs.getInt("chapter_id"));
                    activityAttempt.setTextbookId(rs.getInt("textbook_id"));
                    activityAttempt.setContentBlockId(rs.getInt("content_block_id"));
                    activityAttempt.setStudentId(rs.getInt("student_id"));
                    activityAttempt.setSelectedOptionId(rs.getInt("selected_option_id"));
                    activityAttempt.setAttemptScore(rs.getInt("attempt_score"));
                    activityAttempt.setAttemptTimestamp(rs.getTimestamp("attempt_timestamp"));
                    activityAttempts.add(activityAttempt);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving ActivityAttempts: " + e.getMessage());
        }

        return activityAttempts;
    }
}
