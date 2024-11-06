package Repository;

import Entity.Course;
import Util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    public void createCourse (Course course) {
        course.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        String sql = "INSERT INTO Course (id, title, faculty_id, start_date, end_date, type, createdAt, createdBy, textbook_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NULL, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course.getId());
            pstmt.setString(2, course.getTitle());
            pstmt.setString(3, course.getFaculty_id());
            pstmt.setDate(4, course.getStart_date());
            pstmt.setDate(5, course.getEnd_date());
            pstmt.setString(6, course.getType());
            pstmt.setTimestamp(7, course.getCreatedAt());
            pstmt.setString(8, course.getCreatedBy());
            pstmt.setInt(9, course.getTextbook_id());

            pstmt.executeUpdate();
            System.out.println("Created course: "+ course.getId());
        } catch (SQLException e) {
            System.out.println("Could not create course.");
        }
    }

    public Course findCourseById (String id) {
        String sql = "SELECT id, title, textbook_id, faculty_id, start_date, end_date, type, createdAt, createdBy " +
                "FROM course WHERE id = ?";

        Course course = null;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, id);
            try {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    course = new Course();

                    course.setId(id);
                    course.setTitle(rs.getString("title"));
                    course.setTextbook_id(rs.getInt("textbook_id"));
                    course.setFaculty_id(rs.getString("faculty_id"));
                    course.setStart_date(rs.getDate("start_date"));
                    course.setEnd_date(rs.getDate("end_date"));
                    course.setType(rs.getString("type"));
                    course.setCreatedAt(rs.getTimestamp("createdAt"));
                    course.setCreatedBy(rs.getString("createdBy"));
                }
            } catch (SQLException e) {
                System.out.println("Could not get course details.");
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch course details.");
        }

        return course;
    }

    public Integer findTextbookByCourseId (String course_id) {
        Integer textbook = 0;

        String sql = "SELECT textbook_id " +
                "FROM course " +
                "WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course_id);
            try {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    textbook = rs.getInt("textbook_id");
                }
            } catch (SQLException e) {
                System.out.println("Could not get courses.");
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch courses.");
        }
        return textbook;
    }

    public List<Course> findCoursesByFaculty (String faculty_id) {
        String sql = "SELECT id, title, textbook_id, faculty_id, start_date, end_date, type, createdAt, createdBy " +
                "FROM course WHERE faculty_id = ?";

        List<Course> courses = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, faculty_id);
            try {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Course course = new Course();

                    course.setId(rs.getString("id"));
                    course.setTitle(rs.getString("title"));
                    course.setTextbook_id(rs.getInt("textbook_id"));
                    course.setFaculty_id(faculty_id);
                    course.setStart_date(rs.getDate("start_date"));
                    course.setEnd_date(rs.getDate("end_date"));
                    course.setType(rs.getString("type"));
                    course.setCreatedAt(rs.getTimestamp("createdAt"));
                    course.setCreatedBy(rs.getString("createdBy"));

                    courses.add(course);
                }
            } catch (SQLException e) {
                System.out.println("Could not get courses.");
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch courses.");
        }

        return courses;
    }
}
