package Entity;

public class ActiveCourse {
    private String token;
    private int capacity;
    private String course_id;

    public ActiveCourse () {
    }

    public ActiveCourse(String token, int capacity, String course_id) {
        this.token = token;
        this.capacity = capacity;
        this.course_id = course_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return "ActiveCourse{" +
                "token='" + token + '\'' +
                ", capacity=" + capacity +
                ", course_id='" + course_id + '\'' +
                '}';
    }
}
