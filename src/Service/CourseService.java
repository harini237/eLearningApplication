package Service;

import Entity.*;
import Repository.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseService {
    private final CourseRepository courseRepository;
    private final ActiveCourseRepository activeCourseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PendingApprovalRepository pendingApprovalRepository;
    private final TaCourseMapRepository taCourseMapRepository;
    private final UserService userService;

    public CourseService () {
        this.courseRepository = new CourseRepository();
        this.activeCourseRepository = new ActiveCourseRepository();
        this.enrollmentRepository = new EnrollmentRepository();
        this.pendingApprovalRepository = new PendingApprovalRepository();
        this.taCourseMapRepository = new TaCourseMapRepository();
        this.userService = new UserService();
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
        List<String> students = this.enrollmentRepository.findStudentsByCourseToken(course_token);
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

    public void viewAssignedCoursesByTa (String ta_user_id) {
        List<Course> coursesAssigned = new ArrayList<>();
        coursesAssigned = this.taCourseMapRepository.findCourseIdByTa(ta_user_id);
        if (coursesAssigned.isEmpty()) {
            System.out.println("No courses assigned!");
        } else {
            System.out.println("Courses assigned to "+ ta_user_id+ ":");
            for (Course course : coursesAssigned) {
                System.out.println(course.getId());
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

    public void addTA (String courseId, String firstName, String lastName, String email, String password) {
        ActiveCourse ac = this.activeCourseRepository.findActiveCourseById(courseId);
        String course_token = ac.getToken();
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Integer roleId = user.getRoleId();
            if (roleId == 3) {
                this.addTaToActiveCourse(user.getId(), course_token);
                return;
            } else {
                this.userService.updateUserRoleById(user.getId(), roleId);
            }
        } else {
            this.userService.createFirstTimeUser(firstName, lastName, email, password, 3);
            user = this.userService.getUserByEmail(email);
        }
        this.addTaToActiveCourse(user.getId(), course_token);
    }

    public void approveEnrollment (String student_id, String course_id) {
        ActiveCourse ac = this.activeCourseRepository.findActiveCourseById(course_id);
        String course_token = ac.getToken();

        PendingApproval pa = this.pendingApprovalRepository.findApprovalByKey(student_id, course_token);
        if (pa != null) {
            if (ac.getCapacity() > 0) {
                Enrollment e = new Enrollment(student_id, course_token);
                this.enrollmentRepository.createEnrollment(e);
                this.pendingApprovalRepository.deletePendingApproval(pa.getStudent_id(), pa.getCourse_token());
                this.activeCourseRepository.updateCapacity(ac.getToken(), ac.getCapacity() - 1);
            } else {
                System.out.println("Could not approve enrollment. Class capacity exceeded!");
            }
        } else {
            System.out.println("There is no pending approval for student "+student_id+" in course "+course_id);
        }
    }

    public void viewStudentsByFaculty (String faculty_id) {
        Map<String,List<String>> courses = new HashMap<>();
        courses = this.enrollmentRepository.findEnrolledStudentsByFaculty(faculty_id);

        for (String course: courses.keySet()) {
            List<String> students = courses.get(course);
            for (String sid: students) {
                System.out.println("<"+course+", "+sid+">");
            }
        }
    }

    public void viewStudentsByCourse (String course_id) {
        List<User> students = new ArrayList<>();
        students = this.enrollmentRepository.findStudentsByCourseId(course_id);

        for (User stu: students) {
            System.out.println(stu.getId() + " | " + stu.getFirstName() + " | " + stu.getLastName());
        }
    }

    public void enrollStudent (Map<String, String> enroll, User user) {
        String email = enroll.get("email");
        String student_id = user.getId();
        String course_token = enroll.get("courseToken");
        this.requestEnrollment(student_id, course_token);
    }

    public boolean checkTaByCourseId (String ta_user_id, String course_id) {
        List<String> courseTas = this.taCourseMapRepository.findTasByCourseId(course_id);
        if (courseTas.contains(ta_user_id)) {
            System.out.println("Ta is present in list");
            return true;
        }
        System.out.println("Ta not present in list");
        return false;
    }

    public Integer getTextbookByCourse (String course_id) {
        Integer textbook = this.courseRepository.findTextbookByCourseId(course_id);
        return textbook;
    }

}
