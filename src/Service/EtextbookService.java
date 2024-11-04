package Service;

import Entity.Etextbook;
import Repository.EtextbookRepository;

public class EtextbookService {

    private final EtextbookRepository etextbookRepository;

    // Constructor
    public EtextbookService() {
        this.etextbookRepository = new EtextbookRepository();
    }

    public void createEtextbook(int textbookId, String title) {
        // Create an Etextbook object using the provided id and title
        Etextbook newEtextbook = new Etextbook(textbookId, title);

        // Use the repository to save the new e-textbook
        try {
            etextbookRepository.createEtextbook(newEtextbook);
        } catch (Exception e) {
            System.err.println("Error creating e-textbook: " + e.getMessage());
        }
    }


    public void addChapter(String chapterId, int textbookId, String title) {
        try {
            etextbookRepository.addChapter(chapterId, textbookId, title);
        } catch (Exception e) {
            System.err.println("Error adding chapter: " + e.getMessage());
        }
    }

    public void addSection(int textbookId, String chapterId, String sectionNumber, String title) {
        try {
            etextbookRepository.addSection(textbookId, chapterId, sectionNumber, title);
        } catch (Exception e) {
            System.err.println("Error adding section: " + e.getMessage());
        }

    }

    public void addContentBlock(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String contentType, String contentData) {
        etextbookRepository.addContentBlock(textbookId, chapterId, sectionNumber, contentBlockId, contentType, contentData);
    }

    // Add an activity within a content block
    public void addActivity(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String activityId, String description) {
        etextbookRepository.addActivity(textbookId, chapterId, sectionNumber, contentBlockId, activityId, description);
    }

    // Add a question to an activity
    public void addQuestion(int textbookId, String chapterId, String sectionNumber, String contentBlockId, String activityId, String questionId, String questionText, String[] options, String[] explanations, String[] labels) {
        etextbookRepository.addQuestion(textbookId, chapterId, sectionNumber, contentBlockId, activityId, questionId, questionText, options, explanations, labels);
    }


    public void addText(int textbookId, String chapterId, String sectionNumber, String contentBlockId) {
    }
}
