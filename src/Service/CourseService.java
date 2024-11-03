package Service;

import Entity.*;
import Repository.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;
    private final ActiveCourseRepository activeCourseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PendingApprovalRepository pendingApprovalRepository;
    private final TaCourseMapRepository taCourseMapRepository;

    public CourseService () {
        this.courseRepository = new CourseRepository();
        this.activeCourseRepository = new ActiveCourseRepository();
        this.enrollmentRepository = new EnrollmentRepository();
        this.pendingApprovalRepository = new PendingApprovalRepository();
        this.taCourseMapRepository = new TaCourseMapRepository();
    }

    public void createCourse (String courseId, String courseTitle, Integer textbookId, String facultyId,
                                   Date startDate, Date endDate, String type,
                             String token, int capacity) {
        Course course = new Course(courseId, courseTitle, textbookId, facultyId, startDate, endDate, type, null, null);
        this.courseRepository.createCourse(course);

        if (type.equals("active")) {
            ActiveCourse activeCourse = new ActiveCourse(token, capacity, courseId);
            this.activeCourseRepository.createActiveCourse(activeCourse);
        }

        System.out.println("Created course!");
    }

    public void updateEnrollment (String student_id, String course_token) {
        Enrollment enrollment = new Enrollment(course_token, student_id);
        this.enrollmentRepository.createEnrollment(enrollment);
        System.out.println("Enrollment Status: Approved");

        this.pendingApprovalRepository.deletePendingApproval(student_id, course_token);
    }

    public void requestEnrollment (String student_id, String course_token) {
        PendingApproval pendingApproval = new PendingApproval(student_id, course_token);
        this.pendingApprovalRepository.createApproval(pendingApproval);
        System.out.println("Added enrollment request.\nEnrollment Status: Pending Approval");
    }

    public void addTaToActiveCourse (String ta_user_id, String course_token) {
        TaCourseMap taCourseMap = new TaCourseMap(ta_user_id, course_token);
        this.taCourseMapRepository.createTaCourseMap(taCourseMap);
        System.out.println("Added ta to course!");
    }

    public void viewStudentsEnrolled (String course_token) {
        List<String> students = this.enrollmentRepository.findStudentsByCourse(course_token);
        if (students.isEmpty()) {
            System.out.println("No students enrolled!");
        } else {
            System.out.println("Students enrolled in "+ course_token+ ":");
            for (String sid : students) {
                System.out.println(sid);
            }
        }
    }

    public void viewAssignedCourses (String faculty_id) {
        List<Course> coursesAssigned = new ArrayList<>();
        coursesAssigned = this.courseRepository.findCoursesByFaculty(faculty_id);
        if (coursesAssigned.isEmpty()) {
            System.out.println("No courses assigned!");
        } else {
            System.out.println("Courses assigned to "+ faculty_id+ ":");
            for (Course course : coursesAssigned) {
                System.out.println(course.toString());
            }
        }
    }

    public void viewWorklist (String faculty_id) {
        List<PendingApproval> pendingApprovals = new ArrayList<>();
        pendingApprovals = this.pendingApprovalRepository.findPendingApprovalsByFaculty(faculty_id);
        if (pendingApprovals.isEmpty()) {
            System.out.println("No pending approvals for faculty "+ faculty_id);
        } else {
            System.out.println("Pending approvals for faculty "+ faculty_id+ ":");
            for(PendingApproval p : pendingApprovals) {
                System.out.println(p.toString());
            }
        }
    }
/*
    public void approveEnrollment (String student_id, String faculty_id) {

    }*/
}
