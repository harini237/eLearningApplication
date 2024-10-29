package Repository;

import Entity.Chapter;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterRepository {

    public void createChapter(Chapter chapter) {
        String sql = "INSERT INTO chapter (textbook_id, title, visibility) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, chapter.getTextbookId());
            pstmt.setString(2, chapter.getTitle());
            pstmt.setBoolean(3, chapter.getVisibility());

            pstmt.executeUpdate();

            // Get generated keys to set chapter ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    chapter.setId(rs.getInt(1));
                }
            }

            System.out.println("Chapter created successfully with ID: " + chapter.getId());

        } catch (SQLException e) {
            System.err.println("Error creating chapter: " + e.getMessage());
        }
    }

    public Chapter getChapterById(int id, int textbookId) {
        String sql = "SELECT id, textbook_id, title, visibility FROM chapter WHERE id = ? AND textbook_id = ?";
        Chapter chapter = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, textbookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    chapter = new Chapter();
                    chapter.setId(rs.getInt("id"));
                    chapter.setTextbookId(rs.getInt("textbook_id"));
                    chapter.setTitle(rs.getString("title"));
                    chapter.setVisibility(rs.getBoolean("visibility"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving chapter: " + e.getMessage());
        }

        return chapter;
    }

    public List<Chapter> getAllChaptersByTextbookId(int textbookId) {
        String sql = "SELECT id, textbook_id, title, visibility FROM chapter WHERE textbook_id = ?";
        List<Chapter> chapters = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, textbookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Chapter chapter = new Chapter();
                    chapter.setId(rs.getInt("id"));
                    chapter.setTextbookId(rs.getInt("textbook_id"));
                    chapter.setTitle(rs.getString("title"));
                    chapter.setVisibility(rs.getBoolean("visibility"));
                    chapters.add(chapter);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving chapters: " + e.getMessage());
        }

        return chapters;
    }
}
