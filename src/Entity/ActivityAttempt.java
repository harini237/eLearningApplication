package Entity;


import java.sql.Timestamp;

public class ActivityAttempt {
    private Integer activityId;
    private Integer sectionId;
    private Integer chapterId;
    private Integer textbookId;
    private Integer contentBlockId;
    private Integer studentId;
    private Integer selectedOptionId;
    private Integer attemptScore;
    private Timestamp attemptTimestamp;

    // Constructors
    public ActivityAttempt() {}

    public ActivityAttempt(Integer activityId, Integer sectionId, Integer chapterId, Integer textbookId,
                           Integer contentBlockId, Integer studentId, Integer selectedOptionId,
                           Integer attemptScore, Timestamp attemptTimestamp) {
        this.activityId = activityId;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.contentBlockId = contentBlockId;
        this.studentId = studentId;
        this.selectedOptionId = selectedOptionId;
        this.attemptScore = attemptScore;
        this.attemptTimestamp = attemptTimestamp;
    }

    // Getters and Setters
    public Integer getActivityId() { return activityId; }
    public void setActivityId(Integer activityId) { this.activityId = activityId; }

    public Integer getSectionId() { return sectionId; }
    public void setSectionId(Integer sectionId) { this.sectionId = sectionId; }

    public Integer getChapterId() { return chapterId; }
    public void setChapterId(Integer chapterId) { this.chapterId = chapterId; }

    public Integer getTextbookId() { return textbookId; }
    public void setTextbookId(Integer textbookId) { this.textbookId = textbookId; }

    public Integer getContentBlockId() { return contentBlockId; }
    public void setContentBlockId(Integer contentBlockId) { this.contentBlockId = contentBlockId; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public Integer getSelectedOptionId() { return selectedOptionId; }
    public void setSelectedOptionId(Integer selectedOptionId) { this.selectedOptionId = selectedOptionId; }

    public Integer getAttemptScore() { return attemptScore; }
    public void setAttemptScore(Integer attemptScore) { this.attemptScore = attemptScore; }

    public Timestamp getAttemptTimestamp() { return attemptTimestamp; }
    public void setAttemptTimestamp(Timestamp attemptTimestamp) { this.attemptTimestamp = attemptTimestamp; }

    @Override
    public String toString() {
        return "ActivityAttempt{" +
                "activityId=" + activityId +
                ", sectionId=" + sectionId +
                ", chapterId=" + chapterId +
                ", textbookId=" + textbookId +
                ", contentBlockId=" + contentBlockId +
                ", studentId=" + studentId +
                ", selectedOptionId=" + selectedOptionId +
                ", attemptScore=" + attemptScore +
                ", attemptTimestamp=" + attemptTimestamp +
                '}';
    }
}
