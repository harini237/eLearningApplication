package Repository;

import Entity.Activity;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepository {

    public void createActivity(Activity activity) {
        String sql = "INSERT INTO activity (unique_activity_id, section_id, chapter_id, textbook_id, content_block_id, hidden) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activity.getUniqueActivityId());
            pstmt.setString(2, activity.getSectionId());
            pstmt.setString(3, activity.getChapterId());
            pstmt.setInt(4, activity.getTextbookId());
            pstmt.setString(5, activity.getContentBlockId());
            pstmt.setString(6, activity.getHidden());

            pstmt.executeUpdate();
            System.out.println("Activity created successfully with  Activity ID: " + activity.getActivityId());

        } catch (SQLException e) {
            System.err.println("Error creating Activity: " + e.getMessage());
        }
    }

    public Activity getActivityById(Integer activityId) {
        String sql = "SELECT * FROM activity WHERE id = ?";
        Activity activity = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, activityId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    activity = new Activity();
                    activity.setUniqueActivityId(rs.getString("unique_activity_id"));
                    activity.setSectionId(rs.getString("section_id"));
                    activity.setChapterId(rs.getString("chapter_id"));
                    activity.setTextbookId(rs.getInt("textbook_id"));
                    activity.setContentBlockId(rs.getString("content_block_id"));
                    activity.setHidden(rs.getString("hidden"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving Activity: " + e.getMessage());
        }

        return activity;
    }
    private final QuestionRepository questionRepository = new QuestionRepository();

    public void deleteActivity(String contentBlockId, String sectionId, String chapterId, int textbookId) {
        // Delete associated questions first
        questionRepository.deleteQuestionsByActivityKeys(contentBlockId, sectionId, chapterId, textbookId);

        // Now, delete the activity
        String sql = "DELETE FROM activity WHERE content_block_id = ? AND section_id = ? AND chapter_id = ? AND textbook_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contentBlockId);
            pstmt.setString(2, sectionId);
            pstmt.setString(3, chapterId);
            pstmt.setInt(4, textbookId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Activity and associated questions deleted successfully.");
            } else {
                System.out.println("No activity found with the specified details.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting activity: " + e.getMessage());
        }
    }
    public void hideActivity(String contentBlockId, String sectionId, String chapterId, int textbookId) {
        String sql = "UPDATE activity SET hidden = 'yes' WHERE content_block_id = ? AND section_id = ? AND chapter_id = ? AND textbook_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contentBlockId);
            pstmt.setString(2, sectionId);
            pstmt.setString(3, chapterId);
            pstmt.setInt(4, textbookId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Activity hidden successfully.");
            } else {
                System.out.println("No activity found with the specified details.");
            }

        } catch (SQLException e) {
            System.err.println("Error hiding activity: " + e.getMessage());
        }
    }
    

    public List<Activity> getAllActivitiesBySectionId(String SectionId) {
        String sql = "SELECT * FROM activity WHERE section_id = ?";
        List<Activity> activities = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, SectionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Activity activity = new Activity();
                    activity.setUniqueActivityId(rs.getString("unique_activity_id"));
                    activity.setSectionId(rs.getString("section_id"));
                    activity.setChapterId(rs.getString("chapter_id"));
                    activity.setTextbookId(rs.getInt("textbook_id"));
                    activity.setContentBlockId(rs.getString("content_block_id"));
                    activity.setHidden(rs.getString("hidden"));
                    activities.add(activity);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving Activities: " + e.getMessage());
        }

        return activities;
    }
}

