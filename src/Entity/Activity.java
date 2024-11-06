
package Entity;

public class Activity {
    private int id;
    private String uniqueActivityId; // primary key, unique activity identifier
    private String sectionId;
    private String chapterId;
    private Integer textbookId;
    private String contentBlockId;
    private String hidden; // ENUM type, values 'yes' or 'no'

    // Constructors
    public Activity() {}

    public Activity(Integer id, String uniqueActivityId, String sectionId, String chapterId, Integer textbookId, String contentBlockId, String hidden) {
        this.id = id;
        this.uniqueActivityId = uniqueActivityId;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.contentBlockId = contentBlockId;
        this.hidden = hidden;
    }

    // Getters and Setters
      // New auto-incremented primary key field

    public int getActivityId() {
    return id;
    }

    public void setActivityId(int id) {
    this.id = id;
    }

    public String getUniqueActivityId() { return uniqueActivityId; }
    public void setUniqueActivityId(String uniqueActivityId) { this.uniqueActivityId = uniqueActivityId; }

    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }

    public String getChapterId() { return chapterId; }
    public void setChapterId(String chapterId) { this.chapterId = chapterId; }

    public Integer getTextbookId() { return textbookId; }
    public void setTextbookId(Integer textbookId) { this.textbookId = textbookId; }

    public String getContentBlockId() { return contentBlockId; }
    public void setContentBlockId(String contentBlockId) { this.contentBlockId = contentBlockId; }

    public String getHidden() { return hidden; }
    public void setHidden(String hidden) { this.hidden = hidden; }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId='" + id + '\'' +
                "uniqueActivityId='" + uniqueActivityId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", textbookId=" + textbookId +
                ", contentBlockId='" + contentBlockId + '\'' +
                ", hidden='" + hidden + '\'' +
                '}';
    }
}