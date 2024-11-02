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
    
}
