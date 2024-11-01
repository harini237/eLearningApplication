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
    

    public Etextbook getEtextbookById(Integer etextbookId) {
        String sql = "SELECT id, title FROM e_textbook WHERE id = ?";
        Etextbook etextbook = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, etextbookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    etextbook = new Etextbook();
                    etextbook.setId(rs.getInt("id"));
                    etextbook.setTitle(rs.getString("title"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving e-textbook: " + e.getMessage());
        }

        return etextbook;
    }
}
