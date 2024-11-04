package Repository;

import Entity.Enrollment;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollmentRepository {
    public void createEnrollment (Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (course_token, student_id)" +
                "VALUES (?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, enrollment.getCourse_token());
            pstmt.setString(2, enrollment.getStudent_id());

            pstmt.executeUpdate();
            System.out.println("Student "+ enrollment.getStudent_id()+ " enrolled in active course "+ enrollment.getCourse_token());
        } catch (SQLException e) {
            System.out.println("Could not enroll.");
        }
    }

    public List<String> findCourseTokenByStudent (String student_id) {
        List<String> course_tokens = new ArrayList<>();

        String sql = "SELECT course_token FROM enrollment" +
                "WHERE student_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, student_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                course_tokens.add(rs.getString("course_token"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find enrolled courses.");
        }

        return course_tokens;
    }

    public List<String> findStudentsByCourseToken (String course_token) {
        List<String> students = new ArrayList<>();

        String sql = "SELECT student_id FROM enrollment" +
                "WHERE course_token = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course_token);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(rs.getString("student_id"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find enrolled students.");
        }

        return students;
    }

    public Map<String,List<String>> findEnrolledStudentsByFaculty(String faculty_id) {
        String sql = "SELECT E.student_id, C.id" +
                "FROM enrollment E" +
                "JOIN active_course A" +
                "ON E.course_token = A.token" +
                "JOIN course C" +
                "ON A.course_id = C.id" +
                "WHERE faculty_id = ?";

        Map<String, List<String>> map = new HashMap<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, faculty_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String stu = rs.getString("student_id");
                String course = rs.getString("course_id");
                if (map.containsKey(course)) {
                    List<String> stu_list = map.get(course);
                    stu_list.add(stu);
                    map.replace(course, stu_list);
                } else {
                    List<String> stu_list = new ArrayList<>();
                    stu_list.add(stu);
                    map.put(course,stu_list);
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not find enrolled students.");
        }

        return map;
    }

}
