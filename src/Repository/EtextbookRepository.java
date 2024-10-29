package Repository;

import Entity.Etextbook;
import Util.DatabaseConnection;

import java.sql.*;

public class EtextbookRepository {

    public void createEtextbook(Etextbook etextbook) {
        String sql = "INSERT INTO e_textbook (title) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, etextbook.getTitle());
            pstmt.executeUpdate();

            // Get the generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    etextbook.setId(generatedKeys.getInt(1));
                    System.out.println("E-textbook created successfully with ID: " + etextbook.getId());
                } else {
                    throw new SQLException("Creating e-textbook failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
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
