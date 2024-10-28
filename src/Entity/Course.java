package Entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Course {
    private String id;
    private String title;
    private String faculty_id;
    private Date start_date;
    private Date end_date;
    private String type;
    private Timestamp createdAt;
    private String createdBy;

    public Course() {
    }

    public Course(String id, String title, String faculty_id, Date start_date, Date end_date, String type, Timestamp createdAt, String createdBy) {
        this.id = id;
        this.title = title;
        this.faculty_id = faculty_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", faculty_id='" + faculty_id + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
