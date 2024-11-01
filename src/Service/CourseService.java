package Service;

import Entity.ActiveCourse;
import Entity.Course;
import Repository.ActiveCourseRepository;
import Repository.CourseRepository;

import java.sql.Date;

public class CourseService {
    private CourseRepository courseRepository;
    private ActiveCourseRepository activeCourseRepository;

    public CourseService () {
        this.courseRepository = new CourseRepository();
        this.activeCourseRepository = new ActiveCourseRepository();
    }

    public void createCourse(String courseId, String courseTitle, String facultyId,
                                   Date startDate, Date endDate, String type,
                             String token, int capacity) {
        Course course = new Course(courseId, courseTitle, facultyId, startDate, endDate, type, null, null);
        courseRepository.createCourse(course);

        if (type.equals("active")) {
            ActiveCourse activeCourse = new ActiveCourse(token, capacity, courseId);
            activeCourseRepository.createActiveCourse(activeCourse);
        }

        System.out.println("Created course!");
    }

}
