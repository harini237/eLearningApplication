package Entity;

import java.sql.Timestamp;

public class ContentBlock {
    private String blockId;
    private String sectionId;
    private String chapterId;
    private Integer textbookId;
    private String content;
    private String contentType;
    private String createdBy;
    private String modifiedBy;
    private Timestamp createdAt;

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(Integer textbookId) {
        this.textbookId = textbookId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    private Timestamp modifiedAt;
    private Boolean visibility;

    // Constructors
    public ContentBlock() {}

    public ContentBlock(String id, String sectionId, String chapterId, Integer textbookId, String content,
                        String contentType, String createdBy, String modifiedBy, Timestamp createdAt,
                        Timestamp modifiedAt, Boolean visibility) {
        this.blockId = id;
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

    public ContentBlock(String id, String sectionId, String chapterId, Integer textbookId, String content,
                        String contentType, String createdBy, String modifiedBy, Boolean visibility) {
        this.blockId = id;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.content = content;
        this.contentType = contentType;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.visibility = visibility;
    }


    @Override
    public String toString() {
        return "ContentBlock{" +
                "id=" + blockId +
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
