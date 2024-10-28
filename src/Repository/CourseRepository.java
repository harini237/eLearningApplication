package Repository;

import Entity.Course;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CourseRepository {
    public void createCourse (Course course) {
        course.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        String sql = "INSERT INTO Course (id, title, faculty_id, start_date, end_date, type, createdAt, createdBy)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course.getId());
            pstmt.setString(2, course.getTitle());
            pstmt.setString(3, course.getFaculty_id());
            pstmt.setString(4, course.getStart_date());
            pstmt.setString(5, course.getEnd_date());
            pstmt.setString(6, course.getType());
            pstmt.setTimestamp(7, course.getCreatedAt());
            pstmt.setString(8, course.getCreatedBy());

            pstmt.executeUpdate();
            System.out.println("Created course: "+ course.getId());
        } catch (SQLException e) {
            System.out.println("Could not create course.");
        }
    }
}
