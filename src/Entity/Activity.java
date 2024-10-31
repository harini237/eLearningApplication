package Entity;

public class Activity {
    private Integer activityId;
    private Integer sectionId;
    private Integer chapterId;
    private Integer textbookId;
    private Integer contentBlockId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctOption;
    private String explanation1;
    private String explanation2;
    private String explanation3;
    private String explanation4;

    // Constructors
    public Activity() {}

    public Activity(Integer activityId, Integer sectionId, Integer chapterId, Integer textbookId, Integer contentBlockId,
                    String question, String option1, String option2, String option3, String option4,
                    String correctOption, String explanation1, String explanation2,
                    String explanation3, String explanation4) {
        this.activityId = activityId;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.contentBlockId = contentBlockId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
        this.explanation1 = explanation1;
        this.explanation2 = explanation2;
        this.explanation3 = explanation3;
        this.explanation4 = explanation4;
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

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getOption1() { return option1; }
    public void setOption1(String option1) { this.option1 = option1; }

    public String getOption2() { return option2; }
    public void setOption2(String option2) { this.option2 = option2; }

    public String getOption3() { return option3; }
    public void setOption3(String option3) { this.option3 = option3; }

    public String getOption4() { return option4; }
    public void setOption4(String option4) { this.option4 = option4; }

    public String getCorrectOption() { return correctOption; }
    public void setCorrectOption(String correctOption) { this.correctOption = correctOption; }

    public String getExplanation1() { return explanation1; }
    public void setExplanation1(String explanation1) { this.explanation1 = explanation1; }

    public String getExplanation2() { return explanation2; }
    public void setExplanation2(String explanation2) { this.explanation2 = explanation2; }

    public String getExplanation3() { return explanation3; }
    public void setExplanation3(String explanation3) { this.explanation3 = explanation3; }

    public String getExplanation4() { return explanation4; }
    public void setExplanation4(String explanation4) { this.explanation4 = explanation4; }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", sectionId=" + sectionId +
                ", chapterId=" + chapterId +
                ", textbookId=" + textbookId +
                ", contentBlockId=" + contentBlockId +
                ", question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", correctOption='" + correctOption + '\'' +
                ", explanation1='" + explanation1 + '\'' +
                ", explanation2='" + explanation2 + '\'' +
                ", explanation3='" + explanation3 + '\'' +
                ", explanation4='" + explanation4 + '\'' +
                '}';
    }
}
