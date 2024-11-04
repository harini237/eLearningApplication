package Repository;

import Entity.Etextbook;
import Util.DatabaseConnection;

import java.sql.*;

public class EtextbookRepository {

    public void createEtextbook(Etextbook etextbook) {
        // Adjust SQL to include the id field for manual input
        String sql = "INSERT INTO e_textbook (id, title) VALUES (?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Set the id and title from the Etextbook object
            pstmt.setInt(1, etextbook.getId()); // Assuming etextbook has an id field
            pstmt.setString(2, etextbook.getTitle());
    
            // Execute the insert operation
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("E-textbook created successfully with ID: " + etextbook.getId());
            } else {
                System.err.println("Failed to create e-textbook.");
            }
    
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Error creating e-textbook: " + e.getMessage());
        }
    }
    
    public void addChapter(String chapterId, int textbookId, String title) {
        String sql = "INSERT INTO chapter (chapter_id, textbook_id, title) VALUES (?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, chapterId); // Use the user-provided chapter ID
            pstmt.setInt(2, textbookId);
            pstmt.setString(3, title);
            //pstmt.setBoolean(4, visibility);
    
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Chapter added successfully with ID: " + chapterId);
            } else {
                System.err.println("Failed to add chapter.");
            }
    
        } catch (SQLException e) {
            System.err.println("Error adding chapter: " + e.getMessage());
        }
    }
    public void addSection(int textbookId, String chapterId, String sectionNumber, String title) {
        String sql = "INSERT INTO section (section_number, chapter_id, textbook_id, title) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sectionNumber); // Set section number
            pstmt.setString(2, chapterId);     // Set chapter ID
            pstmt.setInt(3, textbookId);       // Set textbook ID
            pstmt.setString(4, title);         // Set section title

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Section added successfully with Section Number: " + sectionNumber);
            } else {
                System.err.println("Failed to add section.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding section: " + e.getMessage());
        }
    }
    public void addContentBlock(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String contentType, String contentData) {
        String sql = "INSERT INTO content_block (content_block_id, textbook_id, chapter_id, section_number, content_type, content_data) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contentBlockId);
            pstmt.setInt(2, textbookId);
            pstmt.setString(3, chapterId);
            pstmt.setString(4, sectionNumber);
            pstmt.setString(5, contentType);
            pstmt.setString(6, contentData);
            pstmt.executeUpdate();
            System.out.println("Content block added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding content block: " + e.getMessage());
        }
    }

    // Method to add an activity
    public void addActivity(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String activityId, String description) {
        String sql = "INSERT INTO activity (activity_id, textbook_id, chapter_id, section_number, content_block_id, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activityId);
            pstmt.setInt(2, textbookId);
            pstmt.setString(3, chapterId);
            pstmt.setString(4, sectionNumber);
            pstmt.setString(5, contentBlockId);
            pstmt.setString(6, description);
            pstmt.executeUpdate();
            System.out.println("Activity added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding activity: " + e.getMessage());
        }
    }

    // Method to add a question with options, explanations, and labels
    public void addQuestion(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String activityId, String questionId, String questionText, String[] options, String[] explanations, String[] labels) {
        String sql = "INSERT INTO question (question_id, textbook_id, chapter_id, section_number, content_block_id, activity_id, question_text, option1_text, option1_explanation, option1_label, option2_text, option2_explanation, option2_label) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, questionId);
            pstmt.setInt(2, textbookId);
            pstmt.setString(3, chapterId);
            pstmt.setString(4, sectionNumber);
            pstmt.setString(5, contentBlockId);
            pstmt.setString(6, activityId);
            pstmt.setString(7, questionText);
            pstmt.setString(8, options[0]);
            pstmt.setString(9, explanations[0]);
            pstmt.setString(10, labels[0]);
            pstmt.setString(11, options[1]);
            pstmt.setString(12, explanations[1]);
            pstmt.setString(13, labels[1]);
            pstmt.executeUpdate();
            System.out.println("Question added successfully.");

        } catch (SQLException e) {
            System.err.println("Error adding question: " + e.getMessage());
        }
    }
    
}
