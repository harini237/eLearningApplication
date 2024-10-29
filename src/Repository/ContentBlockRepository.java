package Repository;

import Entity.ContentBlock;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContentBlockRepository {

    public void createContentBlock(ContentBlock contentBlock) {
        String sql = "INSERT INTO content_block (section_id, chapter_id, textbook_id, content, content_type, " +
                     "created_by, modified_by, visibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, contentBlock.getSectionId());
            pstmt.setInt(2, contentBlock.getChapterId());
            pstmt.setInt(3, contentBlock.getTextbookId());
            pstmt.setString(4, contentBlock.getContent());
            pstmt.setString(5, contentBlock.getContentType());
            pstmt.setString(6, contentBlock.getCreatedBy());
            pstmt.setString(7, contentBlock.getModifiedBy());
            pstmt.setBoolean(8, contentBlock.getVisibility());

            pstmt.executeUpdate();

            // Retrieve and set generated ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    contentBlock.setId(rs.getInt(1));
                }
            }

            System.out.println("ContentBlock created successfully with ID: " + contentBlock.getId());

        } catch (SQLException e) {
            System.err.println("Error creating ContentBlock: " + e.getMessage());
        }
    }

    public ContentBlock getContentBlockById(int id, int sectionId, int chapterId, int textbookId) {
        String sql = "SELECT id, section_id, chapter_id, textbook_id, content, content_type, created_by, modified_by, " +
                     "created_at, modified_at, visibility FROM content_block WHERE id = ? AND section_id = ? " +
                     "AND chapter_id = ? AND textbook_id = ?";
        ContentBlock contentBlock = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, sectionId);
            pstmt.setInt(3, chapterId);
            pstmt.setInt(4, textbookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    contentBlock = new ContentBlock();
                    contentBlock.setId(rs.getInt("id"));
                    contentBlock.setSectionId(rs.getInt("section_id"));
                    contentBlock.setChapterId(rs.getInt("chapter_id"));
                    contentBlock.setTextbookId(rs.getInt("textbook_id"));
                    contentBlock.setContent(rs.getString("content"));
                    contentBlock.setContentType(rs.getString("content_type"));
                    contentBlock.setCreatedBy(rs.getString("created_by"));
                    contentBlock.setModifiedBy(rs.getString("modified_by"));
                    contentBlock.setCreatedAt(rs.getTimestamp("created_at"));
                    contentBlock.setModifiedAt(rs.getTimestamp("modified_at"));
                    contentBlock.setVisibility(rs.getBoolean("visibility"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving ContentBlock: " + e.getMessage());
        }

        return contentBlock;
    }

    public List<ContentBlock> getAllContentBlocksBySectionId(int sectionId, int chapterId, int textbookId) {
        String sql = "SELECT id, section_id, chapter_id, textbook_id, content, content_type, created_by, modified_by, " +
                     "created_at, modified_at, visibility FROM content_block WHERE section_id = ? AND chapter_id = ? AND textbook_id = ?";
        List<ContentBlock> contentBlocks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sectionId);
            pstmt.setInt(2, chapterId);
            pstmt.setInt(3, textbookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ContentBlock contentBlock = new ContentBlock();
                    contentBlock.setId(rs.getInt("id"));
                    contentBlock.setSectionId(rs.getInt("section_id"));
                    contentBlock.setChapterId(rs.getInt("chapter_id"));
                    contentBlock.setTextbookId(rs.getInt("textbook_id"));
                    contentBlock.setContent(rs.getString("content"));
                    contentBlock.setContentType(rs.getString("content_type"));
                    contentBlock.setCreatedBy(rs.getString("created_by"));
                    contentBlock.setModifiedBy(rs.getString("modified_by"));
                    contentBlock.setCreatedAt(rs.getTimestamp("created_at"));
                    contentBlock.setModifiedAt(rs.getTimestamp("modified_at"));
                    contentBlock.setVisibility(rs.getBoolean("visibility"));
                    contentBlocks.add(contentBlock);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving ContentBlocks: " + e.getMessage());
        }

        return contentBlocks;
    }
}
