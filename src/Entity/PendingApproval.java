package Entity;

public class PendingApproval {
    private String course_token;
    private String student_id;

    public PendingApproval () {
    }

    public PendingApproval(String student_id, String course_token) {
        this.student_id = student_id;
        this.course_token = course_token;
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
        return "PendingApproval{" +
                "course_token='" + course_token + '\'' +
                ", student_id='" + student_id + '\'' +
                '}';
    }
}
