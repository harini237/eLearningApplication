package Entity;

public class Chapter {
    private int id;
    private int textbookId;
    private String title;
    private Boolean visibility;
    private String createdBy;

    // Constructors
    public Chapter() {}

    public Chapter(int id, int textbookId, String title, Boolean visibility) {
        this.id = id;
        this.textbookId = textbookId;
        this.title = title;
        this.visibility = visibility;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
