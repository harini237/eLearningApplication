package Repository;

import Entity.Question;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    public void createQuestion(Question question) {
        String sql = "INSERT INTO question (activity_id, section_id, chapter_id, textbook_id, content_block_id, question_text, " +
                "option1, option2, option3, option4, correct_option, explanation1, explanation2, explanation3, explanation4) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, question.getActivityId());
            pstmt.setInt(2, question.getSectionId());
            pstmt.setInt(3, question.getChapterId());
            pstmt.setInt(4, question.getTextbookId());
            pstmt.setInt(5, question.getContentBlockId());
            pstmt.setString(6, question.getQuestionText());
            pstmt.setString(7, question.getOption1());
            pstmt.setString(8, question.getOption2());
            pstmt.setString(9, question.getOption3());
            pstmt.setString(10, question.getOption4());
            pstmt.setInt(11, question.getCorrectOption());
            pstmt.setString(12, question.getExplanation1());
            pstmt.setString(13, question.getExplanation2());
            pstmt.setString(14, question.getExplanation3());
            pstmt.setString(15, question.getExplanation4());

            pstmt.executeUpdate();

            // Retrieve and set generated ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    question.setId(rs.getInt(1));
                }
            }

            System.out.println("Question created successfully with ID: " + question.getId());

        } catch (SQLException e) {
            System.err.println("Error creating Question: " + e.getMessage());
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
                    question.setId(rs.getInt("id"));
                    question.setActivityId(rs.getInt("activity_id"));
                    question.setSectionId(rs.getInt("section_id"));
                    question.setChapterId(rs.getInt("chapter_id"));
                    question.setTextbookId(rs.getInt("textbook_id"));
                    question.setContentBlockId(rs.getInt("content_block_id"));
                    question.setQuestionText(rs.getString("question_text"));
                    question.setOption1(rs.getString("option1"));
                    question.setOption2(rs.getString("option2"));
                    question.setOption3(rs.getString("option3"));
                    question.setOption4(rs.getString("option4"));
                    question.setCorrectOption(rs.getInt("correct_option"));
                    question.setExplanation1(rs.getString("explanation1"));
                    question.setExplanation2(rs.getString("explanation2"));
                    question.setExplanation3(rs.getString("explanation3"));
                    question.setExplanation4(rs.getString("explanation4"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving Question: " + e.getMessage());
        }

        return question;
    }

    public List<Question> getAllQuestionsByActivityId(int activityId) {
        String sql = "SELECT * FROM question WHERE activity_id = ?";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, activityId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question();
                    question.setId(rs.getInt("id"));
                    question.setActivityId(rs.getInt("activity_id"));
                    question.setSectionId(rs.getInt("section_id"));
                    question.setChapterId(rs.getInt("chapter_id"));
                    question.setTextbookId(rs.getInt("textbook_id"));
                    question.setContentBlockId(rs.getInt("content_block_id"));
                    question.setQuestionText(rs.getString("question_text"));
                    question.setOption1(rs.getString("option1"));
                    question.setOption2(rs.getString("option2"));
                    question.setOption3(rs.getString("option3"));
                    question.setOption4(rs.getString("option4"));
                    question.setCorrectOption(rs.getInt("correct_option"));
                    question.setExplanation1(rs.getString("explanation1"));
                    question.setExplanation2(rs.getString("explanation2"));
                    question.setExplanation3(rs.getString("explanation3"));
                    question.setExplanation4(rs.getString("explanation4"));
                    questions.add(question);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving questions for activity ID: " + activityId + " - " + e.getMessage());
        }

        return questions;
    }
    // Method to update an existing question
    public void updateQuestion(Question question) {
        String sql = "UPDATE question SET question_text = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, " +
                "correct_option = ?, explanation1 = ?, explanation2 = ?, explanation3 = ?, explanation4 = ? " +
                "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getOption1());
            pstmt.setString(3, question.getOption2());
            pstmt.setString(4, question.getOption3());
            pstmt.setString(5, question.getOption4());
            pstmt.setInt(6, question.getCorrectOption());
            pstmt.setString(7, question.getExplanation1());
            pstmt.setString(8, question.getExplanation2());
            pstmt.setString(9, question.getExplanation3());
            pstmt.setString(10, question.getExplanation4());
            pstmt.setInt(11, question.getId());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Question updated successfully with ID: " + question.getId());
            }

        } catch (SQLException e) {
            System.err.println("Error updating Question: " + e.getMessage());
        }
    }

    // Method to delete a question by its ID
    public void deleteQuestion(int id) {
        String sql = "DELETE FROM question WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Question deleted successfully with ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting Question: " + e.getMessage());
        }
    }
}