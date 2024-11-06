package Repository;

import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyReportRepository {

    private Connection connection;

    public FacultyReportRepository() {
        try {
            this.connection = DatabaseConnection.getConnection();
        }catch (Exception e) {
            System.out.println("Faculty Report Didn't Load Error: "+e.getMessage());
        }
    }

    // Query 1: Count sections of the first chapter of a textbook
    public void countSectionsInFirstChapter() {
        String sql = "SELECT COUNT(*) AS total_number_of_sections FROM `section` AS sec WHERE sec.chapter_id = 'chap01' AND sec.textbook_id = 101";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("Total number of sections in the first chapter: " + rs.getInt("total_number_of_sections"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query 2: Print names of faculty and TAs with roles for all courses
    public void printFacultyAndTAsWithRoles() {
        String sql = """
                SELECT user.first_name, user.last_name, role.name AS role_name, course.title
                FROM course
                INNER JOIN user ON course.faculty_id = user.id
                INNER JOIN role ON user.role_id = role.id
                UNION
                SELECT user.first_name, user.last_name, "TA" AS role_name, course.title
                FROM ta_course_map tcm
                INNER JOIN user ON tcm.ta_user_id = user.id
                INNER JOIN active_course ac ON ac.token = tcm.course_token
                INNER JOIN course ON ac.course_id = course.id
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("first_name") + " " + rs.getString("last_name") + " - " +
                        rs.getString("role_name") + " - " + rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query 3: Print each active course with faculty and student count
    public void printActiveCoursesWithStudentCount() {
        String sql = """
                SELECT c.id AS course_id, CONCAT(u.first_name, ' ', u.last_name) AS faculty_member, COUNT(*) AS total_number_of_students
                FROM `user` su
                JOIN enrollment e ON e.student_id = su.id
                JOIN active_course ac ON ac.token = e.course_token
                JOIN course c ON c.id = ac.course_id
                JOIN `user` u ON u.id = c.faculty_id
                GROUP BY c.id, u.id, u.first_name, u.last_name
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Course ID: " + rs.getString("course_id") + ", Faculty: " +
                        rs.getString("faculty_member") + ", Total Students: " + rs.getInt("total_number_of_students"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query 4: Print course with the largest waiting list
    public void printCourseWithLargestWaitingList() {
        String sql = """
                WITH temp AS (
                    SELECT course_token, COUNT(*) AS waitlist_size
                    FROM pending_approval
                    GROUP BY course_token
                    HAVING COUNT(*) = (SELECT MAX(wl_size) FROM (SELECT COUNT(*) AS wl_size FROM pending_approval GROUP BY course_token) AS temp_table)
                )
                SELECT c.course_id, t.waitlist_size
                FROM active_course c
                INNER JOIN temp t ON c.token = t.course_token
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Course ID: " + rs.getString("course_id") + ", Waitlist Size: " + rs.getInt("waitlist_size"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query 5: Print contents of Chapter 02 of textbook 101 in proper sequence
    public void printChapter02Contents() {
        String sql = """
                SELECT c.section_id, c.content_type, c.content AS content_text, a.unique_activity_id, q.question_text,
                       q.option_1, q.explanation_1, q.option_2, q.explanation_2, q.option_3, q.explanation_3,
                       q.option_4, q.explanation_4, q.correct_option
                FROM content_block AS c
                LEFT JOIN activity AS a ON c.block_id = a.content_block_id
                                        AND c.section_id = a.section_id
                                        AND c.chapter_id = a.chapter_id
                                        AND c.textbook_id = a.textbook_id
                LEFT JOIN question AS q ON a.id = q.activity_id
                                        AND a.section_id = q.section_id
                                        AND a.chapter_id = q.chapter_id
                                        AND a.textbook_id = q.textbook_id
                WHERE c.chapter_id = 'chap02' AND c.textbook_id = 101 AND c.hidden = 'no'
                ORDER BY c.section_id,
                         CASE WHEN c.content_type = 'text' THEN 1
                              WHEN c.content_type = 'picture' THEN 2
                              WHEN c.content_type = 'activity' THEN 3 END
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Section ID: " + rs.getString("section_id") + ", Content Type: " + rs.getString("content_type") +
                        ", Content: " + rs.getString("content_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query 6: Print incorrect answers and explanations for Q2 of Activity0
    public void printIncorrectAnswersForQ2() {
        String sql = """
                SELECT q.question_id, q.question_text,
                       CASE WHEN correct_option != 1 THEN option_1 END AS incorrect_option_1,
                       CASE WHEN correct_option != 1 THEN explanation_1 END AS explanation_1,
                       CASE WHEN correct_option != 2 THEN option_2 END AS incorrect_option_2,
                       CASE WHEN correct_option != 2 THEN explanation_2 END AS explanation_2,
                       CASE WHEN correct_option != 3 THEN option_3 END AS incorrect_option_3,
                       CASE WHEN correct_option != 3 THEN explanation_3 END AS explanation_3,
                       CASE WHEN correct_option != 4 THEN option_4 END AS incorrect_option_4,
                       CASE WHEN correct_option != 4 THEN explanation_4 END AS explanation_4
                FROM question q
                JOIN activity a ON a.id = q.activity_id
                WHERE a.unique_activity_id = 'ACT0' AND a.textbook_id = 101
                      AND a.chapter_id = 'chap01' AND q.question_id = 'Q2'
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Question ID: " + rs.getString("question_id") + ", Question: " + rs.getString("question_text"));
                for (int i = 1; i <= 4; i++) {
                    String incorrectOption = rs.getString("incorrect_option_" + i);
                    String explanation = rs.getString("explanation_" + i);
                    if (incorrectOption != null) {
                        System.out.println("Incorrect Option: " + incorrectOption + ", Explanation: " + explanation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query 7: Find books in both active and evaluation status by different instructors
    public void printBooksInActiveAndEvalStatus() {
        String sql = """
                SELECT DISTINCT c1.textbook_id
                FROM course c1
                JOIN course c2 ON c1.textbook_id = c2.textbook_id
                WHERE c1.faculty_id != c2.faculty_id AND c1.type != c2.type
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Textbook ID in both active and evaluation by different instructors: " + rs.getInt("textbook_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
