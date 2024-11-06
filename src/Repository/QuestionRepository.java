package Repository;

import Entity.Question;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    public void createQuestion(Question question) {
        String sql = "INSERT INTO question (activity_id, section_id, chapter_id, textbook_id, content_block_id, " +
                     "id, question_text, option_1, explanation_1, option_2, explanation_2, option_3, " +
                     "explanation_3, option_4, explanation_4, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, question.getActivityId());
            pstmt.setString(2, question.getSectionId());
            pstmt.setString(3, question.getChapterId());
            pstmt.setInt(4, question.getTextbookId());
            pstmt.setString(5, question.getContentBlockId());
            pstmt.setString(6, question.getId());
            pstmt.setString(7, question.getQuestionText());
            pstmt.setString(8, question.getOption1());
            pstmt.setString(9, question.getExplanation1());
            pstmt.setString(10, question.getOption2());
            pstmt.setString(11, question.getExplanation2());
            pstmt.setString(12, question.getOption3());
            pstmt.setString(13, question.getExplanation3());
            pstmt.setString(14, question.getOption4());
            pstmt.setString(15, question.getExplanation4());
            pstmt.setInt(16, question.getCorrectOption());

            pstmt.executeUpdate();

            // Get generated key (ID) to set question ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    question.setId(rs.getString(1));
                }
            }

            System.out.println("Question created successfully with ID: " + question.getId());

        } catch (SQLException e) {
            System.err.println("Error creating question: " + e.getMessage());
        }
    }

    public Question getQuestionById(int id) {
        String sql = "SELECT * FROM question WHERE id = ?";
        Question question = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    question = new Question();
                    question.setId(rs.getString("id"));
                    question.setActivityId(rs.getString("activity_id"));
                    question.setSectionId(rs.getString("section_id"));
                    question.setChapterId(rs.getString("chapter_id"));
                    question.setTextbookId(rs.getInt("textbook_id"));
                    question.setContentBlockId(rs.getString("content_block_id"));
                    question.setQuestionText(rs.getString("question_text"));
                    question.setOption1(rs.getString("option_1"));
                    question.setExplanation1(rs.getString("explanation_1"));
                    question.setOption2(rs.getString("option_2"));
                    question.setExplanation2(rs.getString("explanation_2"));
                    question.setOption3(rs.getString("option_3"));
                    question.setExplanation3(rs.getString("explanation_3"));
                    question.setOption4(rs.getString("option_4"));
                    question.setExplanation4(rs.getString("explanation_4"));
                    question.setCorrectOption(rs.getInt("correct_option"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving question: " + e.getMessage());
        }

        return question;
    }
    public void deleteQuestionsByActivityKeys(String contentBlockId, String sectionId, String chapterId, int textbookId) {
        String sql = "DELETE FROM question WHERE content_block_id = ? AND section_id = ? AND chapter_id = ? AND textbook_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contentBlockId);
            pstmt.setString(2, sectionId);
            pstmt.setString(3, chapterId);
            pstmt.setInt(4, textbookId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Associated questions deleted successfully.");
            } else {
                System.out.println("No questions found with the specified details.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting questions: " + e.getMessage());
        }
    }
    

    public List<Question> getAllQuestionsByActivityId(String activityId) {
        String sql = "SELECT * FROM question WHERE activity_id = ?";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activityId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question();
                    question.setId(rs.getString("id"));
                    question.setActivityId(rs.getString("activity_id"));
                    question.setSectionId(rs.getString("section_id"));
                    question.setChapterId(rs.getString("chapter_id"));
                    question.setTextbookId(rs.getInt("textbook_id"));
                    question.setContentBlockId(rs.getString("content_block_id"));
                    question.setQuestionText(rs.getString("question_text"));
                    question.setOption1(rs.getString("option_1"));
                    question.setExplanation1(rs.getString("explanation_1"));
                    question.setOption2(rs.getString("option_2"));
                    question.setExplanation2(rs.getString("explanation_2"));
                    question.setOption3(rs.getString("option_3"));
                    question.setExplanation3(rs.getString("explanation_3"));
                    question.setOption4(rs.getString("option_4"));
                    question.setExplanation4(rs.getString("explanation_4"));
                    question.setCorrectOption(rs.getInt("correct_option"));
                    questions.add(question);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving questions: " + e.getMessage());
        }

        return questions;
    }
}
