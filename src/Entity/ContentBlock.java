package Entity;

import java.sql.Timestamp;

public class ContentBlock {
    private Integer id;
    private Integer sectionId;
    private Integer chapterId;
    private Integer textbookId;
    private String content;
    private String contentType;
    private String createdBy;
    private String modifiedBy;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Boolean visibility;

    // Constructors
    public ContentBlock() {}

    public ContentBlock(Integer id, Integer sectionId, Integer chapterId, Integer textbookId, String content,
                        String contentType, String createdBy, String modifiedBy, Timestamp createdAt,
                        Timestamp modifiedAt, Boolean visibility) {
        this.id = id;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.content = content;
        this.contentType = contentType;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.visibility = visibility;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getSectionId() { return sectionId; }
    public void setSectionId(Integer sectionId) { this.sectionId = sectionId; }

    public Integer getChapterId() { return chapterId; }
    public void setChapterId(Integer chapterId) { this.chapterId = chapterId; }

    public Integer getTextbookId() { return textbookId; }
    public void setTextbookId(Integer textbookId) { this.textbookId = textbookId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(Timestamp modifiedAt) { this.modifiedAt = modifiedAt; }

    public Boolean getVisibility() { return visibility; }
    public void setVisibility(Boolean visibility) { this.visibility = visibility; }

    @Override
    public String toString() {
        return "ContentBlock{" +
                "id=" + id +
                ", sectionId=" + sectionId +
                ", chapterId=" + chapterId +
                ", textbookId=" + textbookId +
                ", content='" + content + '\'' +
                ", contentType='" + contentType + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", visibility=" + visibility +
                '}';
    }
}
