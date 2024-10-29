package Entity;

public class Section {
    private Integer id;
    private Integer chapterId;
    private Integer textbookId;
    private String title;
    private Boolean visibility;

    // Constructors
    public Section() {}

    public Section(Integer id, Integer chapterId, Integer textbookId, String title, Boolean visibility) {
        this.id = id;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.title = title;
        this.visibility = visibility;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
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
}
