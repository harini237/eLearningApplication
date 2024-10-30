package Entity;

public class Enrollment {
    private String course_token;
    private String student_id;

    public Enrollment () {
    }

    public Enrollment(String course_token, String student_id) {
        this.course_token = course_token;
        this.student_id = student_id;
    }

    public String getCourse_token() {
        return course_token;
    }

    public void setCourse_token(String course_token) {
        this.course_token = course_token;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "course_token='" + course_token + '\'' +
                ", student_id='" + student_id + '\'' +
                '}';
    }
}
