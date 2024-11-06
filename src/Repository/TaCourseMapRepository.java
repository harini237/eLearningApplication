package Repository;

import Entity.Course;
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

    public List<String> findCourseTokenByTa (String ta_user_id) {
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

    public List<String> findTasByCourseToken (String course_token) {
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

    public List<String> findTasByCourseId (String course_id) {
        List<String> ta_user_ids = new ArrayList<>();

        String sql = "SELECT T.ta_user_id" +
                "FROM ta_course_map T" +
                "JOIN active_course A" +
                "ON T.course_token = A.token" +
                "WHERE A.course_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ta_user_ids.add(rs.getString("ta_user_id"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find TAs.");
        }

        return ta_user_ids;
    }

    public List<Course> findCourseIdByTa (String ta_user_id) {
        List<Course> courses = new ArrayList<>();

        String sql = "SELECT C.id, C.title, C.faculty_id, C.textbook_id" +
                "FROM course C" +
                "JOIN active_course A" +
                "ON C.id = A.course_id" +
                "JOIN ta_course_map T" +
                "ON A.token = T.course_token" +
                "WHERE T.ta_user_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, ta_user_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getString("id"));
                course.setTextbook_id(rs.getInt("textbook_id"));
                course.setTitle(rs.getString("title"));
                course.setFaculty_id(rs.getString("faculty_id"));
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println("Could not find TAs.");
        }

        return courses;
    }
}
