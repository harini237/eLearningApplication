package Entity;

public class Chapter {
    private String chapterId;
    private int textbookId;
    private String title;
    private Boolean visibility;
    private String createdBy;

    // Constructors
    public Chapter() {}

    public Chapter(String id, int textbookId, String title, Boolean visibility, String createdBy) {
        this.chapterId = id;
        this.textbookId = textbookId;
        this.title = title;
        this.visibility = visibility;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public String getId() {
        return chapterId;
    }

    public void setId(String id) {
        this.chapterId = id;
    }

    public int getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(int textbookId) {
        this.textbookId = textbookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", textbookId=" + textbookId +
                ", title='" + title + '\'' +
                ", visibility=" + visibility +
                '}';
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
