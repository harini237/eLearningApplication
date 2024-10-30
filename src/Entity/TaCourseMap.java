package Entity;

public class TaCourseMap {
    private String ta_user_id;
    private String course_id;

    public TaCourseMap () {
    }

    public TaCourseMap(String ta_user_id, String course_id) {
        this.ta_user_id = ta_user_id;
        this.course_id = course_id;
    }

    public String getTa_user_id() {
        return ta_user_id;
    }

    public void setTa_user_id(String ta_user_id) {
        this.ta_user_id = ta_user_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return "TaCourseMap{" +
                "ta_user_id='" + ta_user_id + '\'' +
                ", course_id='" + course_id + '\'' +
                '}';
    }
}
