package Repository;

import Entity.TaCourseMap;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaCourseMapRepository {
    public void createTaCourseMap (TaCourseMap ta_course) {
        String sql = "INSERT INTO TaCourseMap (course_token, ta_user_id)" +
                "VALUES (?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, ta_course.getCourse_token());
            pstmt.setString(2, ta_course.getTa_user_id());

            pstmt.executeUpdate();
            System.out.println("TA "+ ta_course.getTa_user_id()+ " added to course "+ ta_course.getCourse_token());
        } catch (SQLException e) {
            System.out.println("Could not add TA-Course.");
        }
    }

    public List<String> findCourseByTa (String ta_user_id) {
        List<String> course_tokens = new ArrayList<>();

        String sql = "SELECT course_token FROM ta_course_map" +
                "WHERE ta_user_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, ta_user_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                course_tokens.add(rs.getString("course_token"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find courses.");
        }

        return course_tokens;
    }

    public List<String> findTasByCourse (String course_token) {
        List<String> ta_user_ids = new ArrayList<>();

        String sql = "SELECT ta_user_id FROM ta_course_map" +
                "WHERE course_token = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course_token);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ta_user_ids.add(rs.getString("ta_user_id"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find TAs.");
        }

        return ta_user_ids;
    }
}
