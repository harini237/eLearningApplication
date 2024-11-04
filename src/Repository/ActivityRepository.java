package Repository;

import Entity.Activity;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepository {

    public void createActivity(Activity activity) {
        String sql = "INSERT INTO activity (section_id, chapter_id, textbook_id, content_block_id, question, option1, option2, " +
                "option3, option4, correct_option, explanation1, explanation2, explanation3, explanation4) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, activity.getSectionId());
            pstmt.setInt(2, activity.getChapterId());
            pstmt.setInt(3, activity.getTextbookId());
            pstmt.setInt(4, activity.getContentBlockId());
            pstmt.setString(5, activity.getQuestion());
            pstmt.setString(6, activity.getOption1());
            pstmt.setString(7, activity.getOption2());
            pstmt.setString(8, activity.getOption3());
            pstmt.setString(9, activity.getOption4());
            pstmt.setString(10, activity.getCorrectOption());
            pstmt.setString(11, activity.getExplanation1());
            pstmt.setString(12, activity.getExplanation2());
            pstmt.setString(13, activity.getExplanation3());
            pstmt.setString(14, activity.getExplanation4());

            pstmt.executeUpdate();

            // Retrieve and set generated ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    activity.setActivityId(rs.getInt(1));
                }
            }

            System.out.println("Activity created successfully with ID: " + activity.getActivityId());

        } catch (SQLException e) {
            System.err.println("Error creating Activity: " + e.getMessage());
        }
    }

    public Activity getActivityById(int activityId) {
        String sql = "SELECT * FROM activity WHERE activity_id = ?";
        Activity activity = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, activityId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    activity = new Activity();
                    activity.setActivityId(rs.getInt("activity_id"));
                    activity.setSectionId(rs.getInt("section_id"));
                    activity.setChapterId(rs.getInt("chapter_id"));
                    activity.setTextbookId(rs.getInt("textbook_id"));
                    activity.setContentBlockId(rs.getInt("content_block_id"));
                    activity.setQuestion(rs.getString("question"));
                    activity.setOption1(rs.getString("option1"));
                    activity.setOption2(rs.getString("option2"));
                    activity.setOption3(rs.getString("option3"));
                    activity.setOption4(rs.getString("option4"));
                    activity.setCorrectOption(rs.getString("correct_option"));
                    activity.setExplanation1(rs.getString("explanation1"));
                    activity.setExplanation2(rs.getString("explanation2"));
                    activity.setExplanation3(rs.getString("explanation3"));
                    activity.setExplanation4(rs.getString("explanation4"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving Activity: " + e.getMessage());
        }

        return activity;
    }

    public List<Activity> getAllActivitiesByChapterId(int chapterId) {
        String sql = "SELECT * FROM activity WHERE chapter_id = ?";
        List<Activity> activities = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chapterId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Activity activity = new Activity();
                    activity.setActivityId(rs.getInt("activity_id"));
                    activity.setSectionId(rs.getInt("section_id"));
                    activity.setChapterId(rs.getInt("chapter_id"));
                    activity.setTextbookId(rs.getInt("textbook_id"));
                    activity.setContentBlockId(rs.getInt("content_block_id"));
                    activity.setQuestion(rs.getString("question"));
                    activity.setOption1(rs.getString("option1"));
                    activity.setOption2(rs.getString("option2"));
                    activity.setOption3(rs.getString("option3"));
                    activity.setOption4(rs.getString("option4"));
                    activity.setCorrectOption(rs.getString("correct_option"));
                    activity.setExplanation1(rs.getString("explanation1"));
                    activity.setExplanation2(rs.getString("explanation2"));
                    activity.setExplanation3(rs.getString("explanation3"));
                    activity.setExplanation4(rs.getString("explanation4"));
                    activities.add(activity);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving Activities: " + e.getMessage());
        }

        return activities;
    }
}