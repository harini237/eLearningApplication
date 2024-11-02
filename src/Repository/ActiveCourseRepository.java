package Repository;

import Entity.ActiveCourse;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveCourseRepository {
    public void createActiveCourse (ActiveCourse activeCourse) {
        String sql = "INSERT INTO active_course (token, capacity, course_id)" +
                "VALUES (?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, activeCourse.getToken());
            pstmt.setInt(2, activeCourse.getCapacity());
            pstmt.setString(3, activeCourse.getCourse_id());

            pstmt.executeUpdate();
            System.out.println("Active course created with token: "+ activeCourse.getToken());
        } catch (SQLException e) {
            System.out.println("Could not create course.");
        }
    }

    public ActiveCourse findActiveCourseByToken (String token) {
        ActiveCourse course = null;

        String sql = "SELECT token, capacity, course_id FROM active_course" +
                "WHERE token = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, token);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                course = new ActiveCourse(rs.getString("token"),
                        rs.getInt("capacity"),
                        rs.getString("course_id"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find active course.");
        }

        return course;
    }

    public void updateCapacity (String token, int capacity) {
        String sql = "UPDATE active_course" +
                "SET capacity = ?" +
                "WHERE token = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, capacity);
            pstmt.setString(2, token);

            pstmt.executeQuery();
            System.out.println("Updated capacity for course: "+token);
        } catch (SQLException e) {
            System.out.println("Could not update capacity.");
        }
    }

    public ActiveCourse findActiveCourseById (String course_id) {
        ActiveCourse course = null;

        String sql = "SELECT token, capacity, course_id FROM active_course" +
                "WHERE course_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, course_id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                course = new ActiveCourse(rs.getString("token"),
                        rs.getInt("capacity"),
                        rs.getString("course_id"));
            }
        } catch (SQLException e) {
            System.out.println("Could not find active course.");
        }

        return course;
    }
}
