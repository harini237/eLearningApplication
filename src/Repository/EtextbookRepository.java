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
        String sql = "INSERT INTO chapter (chapter_id, textbook_id, title, visibility) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, chapterId); // Use the user-provided chapter ID
            pstmt.setInt(2, textbookId);   // Set textbook ID
            pstmt.setString(3, title);     // Set chapter title
            pstmt.setInt(4, 1);            // Set visibility to 1 (true)
    
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

    public void hideChapter(String chapterId, int textbookId) {
        String sql = "UPDATE chapter SET visibility = 0 WHERE chapter_id = ? AND textbook_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, chapterId); // Set the chapter ID
            pstmt.setInt(2, textbookId);   // Set the textbook ID
    
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Chapter with ID: " + chapterId + " is now hidden.");
            } else {
                System.err.println("Failed to hide chapter with ID: " + chapterId);
            }
    
        } catch (SQLException e) {
            System.err.println("Error hiding chapter: " + e.getMessage());
        }
    }

public void deleteChapter(String chapterId, int textbookId) {
    String deleteContentBlockSql = "DELETE FROM content_block WHERE chapter_id = ? AND textbook_id = ?";
    String deleteSectionSql = "DELETE FROM section WHERE chapter_id = ? AND textbook_id = ?";
    String deleteChapterSql = "DELETE FROM chapter WHERE chapter_id = ? AND textbook_id = ?";

    try (Connection conn = DatabaseConnection.getConnection()) {
        // Start a transaction
        conn.setAutoCommit(false);

        // Step 1: Delete associated content blocks
        try (PreparedStatement deleteContentBlockStmt = conn.prepareStatement(deleteContentBlockSql)) {
            deleteContentBlockStmt.setString(1, chapterId);
            deleteContentBlockStmt.setInt(2, textbookId);
            deleteContentBlockStmt.executeUpdate();
        }

        // Step 2: Delete associated sections
        try (PreparedStatement deleteSectionStmt = conn.prepareStatement(deleteSectionSql)) {
            deleteSectionStmt.setString(1, chapterId);
            deleteSectionStmt.setInt(2, textbookId);
            deleteSectionStmt.executeUpdate();
        }

        // Step 3: Delete the chapter
        try (PreparedStatement deleteChapterStmt = conn.prepareStatement(deleteChapterSql)) {
            deleteChapterStmt.setString(1, chapterId);
            deleteChapterStmt.setInt(2, textbookId);

            int rowsAffected = deleteChapterStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Chapter with ID: " + chapterId + " deleted successfully.");
            } else {
                System.err.println("No chapter found to delete with ID: " + chapterId);
            }
        }

        // Commit transaction
        conn.commit();
    } catch (SQLException e) {
        System.err.println("Error deleting chapter: " + e.getMessage());
    }
}
 

    public void addContentBlock(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String contentType, String content, String createdBy, String modifiedBy) {
        String sql = "INSERT INTO content_block (block_id, section_id, chapter_id, textbook_id, content, content_type, hidden, created_by, modified_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'no', ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, contentBlockId); // Set content block ID
            pstmt.setString(2, sectionNumber);  // Set section number
            pstmt.setString(3, chapterId);      // Set chapter ID
            pstmt.setInt(4, textbookId);        // Set textbook ID
            pstmt.setString(5, content);        // Set content
            pstmt.setString(6, contentType);
            // Set the seventh parameter for 'hidden', which is already 'no' in the query, so no need to set explicitly
            pstmt.setString(7, createdBy);      // Set createdBy
            pstmt.setString(8, modifiedBy);     // Set modifiedBy
    
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
    

    public void hideContentBlock(String blockId, String sectionId, String chapterId, int textbookId) {
        String sql = "UPDATE content_block SET hidden = 'yes' WHERE block_id = ? AND section_id = ? AND chapter_id = ? AND textbook_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Set the parameters for the prepared statement
            pstmt.setString(1, blockId);          // Set block_id
            pstmt.setString(2, sectionId);        // Set section_id
            pstmt.setString(3, chapterId);        // Set chapter_id
            pstmt.setInt(4, textbookId);          // Set textbook_id
    
            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();
    
            // Check if the update was successful
            if (rowsAffected > 0) {
                System.out.println("Content block with ID " + blockId + " is now hidden.");
            } else {
                System.err.println("Content block with ID " + blockId + " not found or already hidden.");
            }
    
        } catch (SQLException e) {
            System.err.println("Error hiding content block: " + e.getMessage());
        }
    }
     
   
    public void addSection(int textbookId, String chapterId, String sectionNumber, String title) {
        String sql = "INSERT INTO section (section_id, chapter_id, textbook_id, title, visibility) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, sectionNumber); // Set section number
            pstmt.setString(2, chapterId);     // Set chapter ID
            pstmt.setInt(3, textbookId);       // Set textbook ID
            pstmt.setString(4, title);         // Set section title
            pstmt.setInt(5, 1);                // Set visibility to 1 (true)
    
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
    

    public void hideSection(int textbookId, String chapterId, String sectionId) {
        String sql = "UPDATE section SET visibility = 0 WHERE section_id = ? AND chapter_id = ? AND textbook_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sectionId);
            pstmt.setString(2, chapterId);
            pstmt.setInt(3, textbookId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Section visibility updated to hidden.");
            } else {
                System.err.println("No section found to hide.");
            }

        } catch (SQLException e) {
            System.err.println("Error hiding section: " + e.getMessage());
        }
    }

    public void deleteSection(int textbookId, String chapterId, String sectionId) {
        String deleteContentBlockSql = "DELETE FROM content_block WHERE section_id = ? AND chapter_id = ? AND textbook_id = ?";
        String deleteSectionSql = "DELETE FROM section WHERE section_id = ? AND chapter_id = ? AND textbook_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false);
    
            // Step 1: Delete associated content blocks
            try (PreparedStatement deleteContentBlockStmt = conn.prepareStatement(deleteContentBlockSql)) {
                deleteContentBlockStmt.setString(1, sectionId);
                deleteContentBlockStmt.setString(2, chapterId);
                deleteContentBlockStmt.setInt(3, textbookId);
    
                deleteContentBlockStmt.executeUpdate();
            }
    
            // Step 2: Delete the section
            try (PreparedStatement deleteSectionStmt = conn.prepareStatement(deleteSectionSql)) {
                deleteSectionStmt.setString(1, sectionId);
                deleteSectionStmt.setString(2, chapterId);
                deleteSectionStmt.setInt(3, textbookId);
    
                int rowsAffected = deleteSectionStmt.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Section deleted successfully.");
                } else {
                    System.err.println("No section found to delete.");
                }
            }
    
            // Commit transaction
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Error deleting section: " + e.getMessage());
        }
    }
    
    
    public void addActivity(String activityId, String sectionNumber, String chapterId, int textbookId, String contentBlockId) {
        String sql = "INSERT INTO activity (unique_activity_id, section_id, chapter_id, textbook_id, content_block_id, hidden) VALUES (?, ?, ?, ?, ?, 'no')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activityId);
            pstmt.setString(2, sectionNumber);
            pstmt.setString(3, chapterId);
            pstmt.setInt(4, textbookId);
            pstmt.setString(5, contentBlockId);
            

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Activity added successfully with Activity ID: " + activityId);
            } else {
                System.err.println("Failed to add activity.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding activity: " + e.getMessage());
        }
    }
    
    public void addQuestion(String activityId, int textbookId, String sectionNumber, String chapterId,  String contentBlockId, String questionId, String questionText, String option1, String explanation1, String option2, String explanation2, String option3, String explanation3, String option4, String explanation4, int correctOption) {
        String sql = "INSERT INTO question (activity_id, textbook_id, section_id ,chapter_id, content_block_id, id, question_text, option_1, explanation_1, option_2, explanation_2, option_3, explanation_3, option_4, explanation_4, correct_option) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activityId);
            pstmt.setInt(2, textbookId);
            pstmt.setString(3, sectionNumber);
            pstmt.setString(4, chapterId);
            pstmt.setString(5, contentBlockId);
            pstmt.setString(6, questionId);
            pstmt.setString(7, questionText);
            pstmt.setString(8, option1);
            pstmt.setString(9, explanation1);
            pstmt.setString(10, option2);
            pstmt.setString(11, explanation2);
            pstmt.setString(12, option3);
            pstmt.setString(13, explanation3);
            pstmt.setString(14, option4);
            pstmt.setString(15, explanation4);
            pstmt.setInt(16, correctOption);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Question added successfully with Question ID: " + questionId);
            } else {
                System.err.println("Failed to add question.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding question: " + e.getMessage());
        }
    }
    // public void deleteActivity(String contentBlockId, String sectionId, String chapterId, int textbookId) {
    //     String sql = "DELETE FROM activity WHERE content_block_id = ? AND section_id = ? AND chapter_id = ? AND textbook_id = ?";
    
    //     try (Connection conn = DatabaseConnection.getConnection();
    //          PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
    //         pstmt.setString(1, contentBlockId);
    //         pstmt.setString(2, sectionId);
    //         pstmt.setString(3, chapterId);
    //         pstmt.setInt(4, textbookId);
    
    //         int rowsAffected = pstmt.executeUpdate();
    //         if (rowsAffected > 0) {
    //             System.out.println("Activity deleted successfully.");
    //         } else {
    //             System.out.println("No activity found with the specified details.");
    //         }
    
    //     } catch (SQLException e) {
    //         System.err.println("Error deleting activity: " + e.getMessage());
    //     }
    // }
    
    public void deleteContentBlock(String contentBlockId, String sectionId, String chapterId, int textbookId) {
        String sql = "DELETE FROM content_block WHERE block_id = ? AND section_id = ? AND chapter_id = ? AND textbook_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, contentBlockId);
            pstmt.setString(2, sectionId);
            pstmt.setString(3, chapterId);
            pstmt.setInt(4, textbookId);
            pstmt.executeUpdate();
    
        } catch (SQLException e) {
            System.err.println("Error deleting content block: " + e.getMessage());
        }
    }

    public void listAllEtextbooks() {
        // SQL query to join e-textbooks with their chapters, sections, and content blocks
        String sql = "SELECT " +
                     "et.title AS 'E-Book', " +
                     "ch.chapter_id AS 'Chapter', " +
                     "sec.section_id AS 'Section', " +
                     "cb.block_id AS 'Block' " +
                     "FROM e_textbook et " +
                     "LEFT JOIN chapter ch ON et.id = ch.textbook_id " +
                     "LEFT JOIN section sec ON ch.chapter_id = sec.chapter_id AND ch.textbook_id = sec.textbook_id " +
                     "LEFT JOIN content_block cb ON sec.section_id = cb.section_id AND sec.chapter_id = cb.chapter_id AND sec.textbook_id = cb.textbook_id " +
                     "ORDER BY et.id, ch.chapter_id, sec.section_id, cb.block_id";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
    
            String currentEbook = null;
            String currentChapter = null;
            String currentSection = null;
    
            while (rs.next()) {
                String eBook = rs.getString("E-Book");
                String chapter = rs.getString("Chapter");
                String section = rs.getString("Section");
                String block = rs.getString("Block");
    
                // Skip rows with null Section or Block values
                if (section == null || block == null) {
                    continue;
                }
    
                // Print E-Book title if it's new
                if (!eBook.equals(currentEbook)) {
                    currentEbook = eBook;
                    currentChapter = null;
                    currentSection = null;
                    System.out.println("E-Book: " + currentEbook);
                }
    
                // Print Chapter if it's new
                if (!chapter.equals(currentChapter)) {
                    currentChapter = chapter;
                    currentSection = null;
                    System.out.println("  Chapter " + currentChapter);
                }
    
                // Print Section if it's new
                if (!section.equals(currentSection)) {
                    currentSection = section;
                    System.out.println("    Section " + currentSection);
                }
    
                // Print Block
                System.out.println("      Block " + block);
            }
    
        } catch (SQLException e) {
            System.err.println("Error retrieving e-textbook data: " + e.getMessage());
        }
    }
    
    
    
}
    

