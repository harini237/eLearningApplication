package Entity;

public class Section {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(Integer textbookId) {
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

    private String id;
    private String chapterId;
    private Integer  textbookId;
    private String title;
    private Boolean visibility;
    private String createdBy;

    // Constructors
    public Section() {}

    public Section(String id, String chapterId, Integer textbookId, String title, Boolean visibility, String createdBy) {
        this.id = id;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.title = title;
        this.visibility = visibility;
        this.createdBy = createdBy;
    }


    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", chapterId=" + chapterId +
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
