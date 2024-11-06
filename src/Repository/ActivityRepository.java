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
            System.out.println("Activity created successfully with Unique Activity ID: " + activity.getUniqueActivityId());

        } catch (SQLException e) {
            System.err.println("Error creating Activity: " + e.getMessage());
        }
    }

    public Activity getActivityById(String uniqueActivityId) {
        String sql = "SELECT * FROM activity WHERE unique_activity_id = ?";
        Activity activity = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, uniqueActivityId);

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

