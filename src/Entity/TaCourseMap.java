package Entity;

public class TaCourseMap {
    private String ta_user_id;
    private String course_token;

    public TaCourseMap () {
    }

    public TaCourseMap(String ta_user_id, String course_token) {
        this.ta_user_id = ta_user_id;
        this.course_token = course_token;
    }

    public String getTa_user_id() {
        return ta_user_id;
    }

    public void setTa_user_id(String ta_user_id) {
        this.ta_user_id = ta_user_id;
    }

    public String getCourse_token() {
        return course_token;
    }

    public void setCourse_token(String course_token) {
        this.course_token = course_token;
    }

    @Override
    public String toString() {
        return "TaCourseMap{" +
                "ta_user_id='" + ta_user_id + '\'' +
                ", course_token='" + course_token + '\'' +
                '}';
    }
}
