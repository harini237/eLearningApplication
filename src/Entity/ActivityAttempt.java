package Entity;

import java.sql.Timestamp;

public class ActivityAttempt {
    private String activityId;
    private String sectionId;
    private String chapterId;
    private int textbookId;
    private String studentId;
    private String contentBlockId;
    private int selectedOptionId;
    private int attemptScore;
    private Timestamp attemptedAt;

    // Constructors
    public ActivityAttempt() {}

    public ActivityAttempt(String activityId, String sectionId, String chapterId, int textbookId, String studentId,
                           String contentBlockId, int selectedOptionId, int attemptScore, Timestamp attemptedAt) {
        this.activityId = activityId;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.studentId = studentId;
        this.contentBlockId = contentBlockId;
        this.selectedOptionId = selectedOptionId;
        this.attemptScore = attemptScore;
        this.attemptedAt = attemptedAt;
    }

    // Getters and Setters
    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }

    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }

    public String getChapterId() { return chapterId; }
    public void setChapterId(String chapterId) { this.chapterId = chapterId; }

    public int getTextbookId() { return textbookId; }
    public void setTextbookId(int textbookId) { this.textbookId = textbookId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getContentBlockId() { return contentBlockId; }
    public void setContentBlockId(String contentBlockId) { this.contentBlockId = contentBlockId; }

    public int getSelectedOptionId() { return selectedOptionId; }
    public void setSelectedOptionId(int selectedOptionId) { this.selectedOptionId = selectedOptionId; }

    public int getAttemptScore() { return attemptScore; }
    public void setAttemptScore(int attemptScore) { this.attemptScore = attemptScore; }

    public Timestamp getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(Timestamp attemptedAt) { this.attemptedAt = attemptedAt; }

    @Override
    public String toString() {
        return "ActivityAttempt{" +
                "activityId='" + activityId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", textbookId=" + textbookId +
                ", studentId='" + studentId + '\'' +
                ", contentBlockId='" + contentBlockId + '\'' +
                ", selectedOptionId=" + selectedOptionId +
                ", attemptScore=" + attemptScore +
                ", attemptedAt=" + attemptedAt +
                '}';
    }
}

