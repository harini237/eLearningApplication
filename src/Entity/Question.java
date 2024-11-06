package Entity;

public class Question {
    private String id;
    private String activityId;
    private String sectionId;
    private String chapterId;
    private int textbookId;
    private String contentBlockId;
    private String questionText;
    private String option1;
    private String explanation1;
    private String option2;
    private String explanation2;
    private String option3;
    private String explanation3;
    private String option4;
    private String explanation4;
    private int correctOption;

    // Constructors
    public Question() {}

    public Question(String id, String activityId, String sectionId, String chapterId, int textbookId, String contentBlockId,
                    String questionText, String option1, String explanation1, String option2, 
                    String explanation2, String option3, String explanation3, String option4, String explanation4, 
                    int correctOption) {
        this.id = id;
        this.activityId = activityId;
        this.sectionId = sectionId;
        this.chapterId = chapterId;
        this.textbookId = textbookId;
        this.contentBlockId = contentBlockId;
        this.questionText = questionText;
        this.option1 = option1;
        this.explanation1 = explanation1;
        this.option2 = option2;
        this.explanation2 = explanation2;
        this.option3 = option3;
        this.explanation3 = explanation3;
        this.option4 = option4;
        this.explanation4 = explanation4;
        this.correctOption = correctOption;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }

    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }

    public String getChapterId() { return chapterId; }
    public void setChapterId(String chapterId) { this.chapterId = chapterId; }

    public int getTextbookId() { return textbookId; }
    public void setTextbookId(int textbookId) { this.textbookId = textbookId; }

    public String getContentBlockId() { return contentBlockId; }
    public void setContentBlockId(String contentBlockId) { this.contentBlockId = contentBlockId; }



    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getOption1() { return option1; }
    public void setOption1(String option1) { this.option1 = option1; }

    public String getExplanation1() { return explanation1; }
    public void setExplanation1(String explanation1) { this.explanation1 = explanation1; }

    public String getOption2() { return option2; }
    public void setOption2(String option2) { this.option2 = option2; }

    public String getExplanation2() { return explanation2; }
    public void setExplanation2(String explanation2) { this.explanation2 = explanation2; }

    public String getOption3() { return option3; }
    public void setOption3(String option3) { this.option3 = option3; }

    public String getExplanation3() { return explanation3; }
    public void setExplanation3(String explanation3) { this.explanation3 = explanation3; }

    public String getOption4() { return option4; }
    public void setOption4(String option4) { this.option4 = option4; }

    public String getExplanation4() { return explanation4; }
    public void setExplanation4(String explanation4) { this.explanation4 = explanation4; }

    public int getCorrectOption() { return correctOption; }
    public void setCorrectOption(int correctOption) { this.correctOption = correctOption; }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", activityId='" + activityId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", textbookId=" + textbookId +
                ", contentBlockId='" + contentBlockId + '\'' +
                
                ", questionText='" + questionText + '\'' +
                ", option1='" + option1 + '\'' +
                ", explanation1='" + explanation1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", explanation2='" + explanation2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", explanation3='" + explanation3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", explanation4='" + explanation4 + '\'' +
                ", correctOption=" + correctOption +
                '}';
    }
}
