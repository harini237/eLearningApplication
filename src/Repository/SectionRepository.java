package Repository;

import Entity.Section;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionRepository {

    public void createSection(Section section) {
        String sql = "INSERT INTO section (chapter_id, textbook_id, title, visibility) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, section.getChapterId());
            pstmt.setInt(2, section.getTextbookId());
            pstmt.setString(3, section.getTitle());
            pstmt.setBoolean(4, section.getVisibility());

            pstmt.executeUpdate();

            // Retrieve and set generated ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    section.setId(rs.getInt(1));
                }
            }

            System.out.println("Section created successfully with ID: " + section.getId());

        } catch (SQLException e) {
            System.err.println("Error creating section: " + e.getMessage());
        }
    }

    public Section getSectionById(int id, int chapterId, int textbookId) {
        String sql = "SELECT id, chapter_id, textbook_id, title, visibility FROM section WHERE id = ? AND chapter_id = ? AND textbook_id = ?";
        Section section = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, chapterId);
            pstmt.setInt(3, textbookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    section = new Section();
                    section.setId(rs.getInt("id"));
                    section.setChapterId(rs.getInt("chapter_id"));
                    section.setTextbookId(rs.getInt("textbook_id"));
                    section.setTitle(rs.getString("title"));
                    section.setVisibility(rs.getBoolean("visibility"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving section: " + e.getMessage());
        }

        return section;
    }

    public List<Section> getAllSectionsByChapterId(int chapterId, int textbookId) {
        String sql = "SELECT id, chapter_id, textbook_id, title, visibility FROM section WHERE chapter_id = ? AND textbook_id = ?";
        List<Section> sections = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chapterId);
            pstmt.setInt(2, textbookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Section section = new Section();
                    section.setId(rs.getInt("id"));
                    section.setChapterId(rs.getInt("chapter_id"));
                    section.setTextbookId(rs.getInt("textbook_id"));
                    section.setTitle(rs.getString("title"));
                    section.setVisibility(rs.getBoolean("visibility"));
                    sections.add(section);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving sections: " + e.getMessage());
        }

        return sections;
    }
}
