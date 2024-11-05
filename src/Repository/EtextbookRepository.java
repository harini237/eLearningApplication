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

    public void modifyChapter(int textbookId, String chapterId, String newTitle) {
        String sql = "UPDATE chapter SET title = ? WHERE textbook_id = ? AND chapter_id = ?";

        // Use try-with-resources for connection and prepared statement
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, textbookId);
            preparedStatement.setString(3, chapterId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Chapter modified successfully.");
            } else {
                System.out.println("No chapter found with the given details.");
            }
        } catch (SQLException e) {
            System.err.println("Error modifying chapter: " + e.getMessage());
        }
    }

    public void addContentBlock(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String content, String createdBy, String modifiedBy) {
        String sql = "INSERT INTO content_block (block_id, section_id, chapter_id, textbook_id, content, content_type, hidden, created_by, modified_by) " +
                     "VALUES (?, ?, ?, ?, ?, 'text', 'no')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contentBlockId); // Set content block ID
            pstmt.setString(2, sectionNumber);  // Set section number
            pstmt.setString(3, chapterId);      // Set chapter ID
            pstmt.setInt(4, textbookId);        // Set textbook ID
            pstmt.setString(5, content);        // Set content
            pstmt.setString(6, createdBy);        // Set createdby
            pstmt.setString(7, modifiedBy);        // Set modifiedby


            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Content block added successfully with Content Block ID: " + contentBlockId);
            } else {
                System.err.println("Failed to add content block.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding content block: " + e.getMessage());
        }
    }
    

    public void modifyChapter(int textbookId, String chapterId, String newTitle) {
        String sql = "UPDATE chapter SET title = ? WHERE textbook_id = ? AND chapter_id = ?";

        // Use try-with-resources for connection and prepared statement
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, textbookId);
            preparedStatement.setString(3, chapterId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Chapter modified successfully.");
            } else {
                System.out.println("No chapter found with the given details.");
            }
        } catch (SQLException e) {
            System.err.println("Error modifying chapter: " + e.getMessage());
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
    
}
