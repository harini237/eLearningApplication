package Service;

import java.sql.SQLException;
import java.util.Scanner;

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

    public void modifyChapter(int textbookId, String chapterId, String newTitle) {
        try {
            etextbookRepository.modifyChapter(textbookId, chapterId, newTitle);
        } catch (Exception e) {
            System.err.println("Error modifying chapter: " + e.getMessage());
        }
    }

    public void deleteChapter(int textbookId, String chapterId) {
        try {
            etextbookRepository.deleteChapter(chapterId, textbookId);
        } catch (Exception e) {
            System.err.println("Error deleting chapter: " + e.getMessage());
        }
    }

    public void hideChapter(int textbookId, String chapterId) {
        try {
            etextbookRepository.deleteChapter(chapterId, textbookId);
        } catch (Exception e) {
            System.err.println("Error hiding chapter: " + e.getMessage());
        }
    }

    public void addSection(int textbookId, String chapterId, String sectionNumber, String title) {
        try {
            etextbookRepository.addSection(textbookId, chapterId, sectionNumber, title);
        } catch (Exception e) {
            System.err.println("Error adding section: " + e.getMessage());
        }
    }

    public void addContentBlock(String contentBlockId, String sectionNumber, String chapterId, int textbookId, String content, String createdBy, String modifiedBy) {
        try {
            etextbookRepository.addContentBlock(textbookId, chapterId, sectionNumber, contentBlockId, content, createdBy, modifiedBy);
        } catch (Exception e) {
            System.err.println("Error adding content block: " + e.getMessage());
        }
    }

    public void hideContentBlock(String contentBlockId, String sectionNumber, String chapterId, int textbookId) {
        try {
            etextbookRepository.hideContentBlock(contentBlockId, sectionNumber, chapterId, textbookId);
        } catch (Exception e) {
            System.err.println("Error hiding content block: " + e.getMessage());
        }
    }
    

    public void addActivity(String activityId, String sectionNumber, String chapterId, int textbookId, String contentBlockId) {
        try {
            etextbookRepository.addActivity(activityId, sectionNumber, chapterId, textbookId, contentBlockId);
            System.out.println("Activity added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding activity: " + e.getMessage());
        }
    }

    public void addQuestion(String activityId, int textbookId, String sectionNumber, String chapterId, String contentBlockId, String questionId, String questionText, String option1, String explanation1, String option2, String explanation2, String option3, String explanation3, String option4, String explanation4, int correctOption) {
        try {
            etextbookRepository.addQuestion(activityId, textbookId, sectionNumber, chapterId, contentBlockId, questionId, questionText, option1, explanation1, option2, explanation2, option3, explanation3, option4, explanation4, correctOption);
            System.out.println("Question added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding question: " + e.getMessage());
        }
    }
    public void deleteActivity(String activityId) {
        try {
            etextbookRepository.deleteActivity(activityId);
            System.out.println("Activity deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting activity: " + e.getMessage());
        }
    }
    
    public void deleteContentBlock(String contentBlockId, String sectionId, String chapterId, int textbookId) {
        try {
            etextbookRepository.deleteContentBlock(contentBlockId, sectionId, chapterId, textbookId);
            System.out.println("Content block deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting content block: " + e.getMessage());
        }
    }

    public void listAllEtextbooks() {
        try {
            etextbookRepository.listAllEtextbooks();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    public void deleteSection(int textbookId, String chapterId, String sectionId) {
        try {
            etextbookRepository.deleteSection(textbookId, chapterId, sectionId);
        } catch (Exception e) {
            System.err.println("Error deleting section: " + e.getMessage());
        }
    }

    public void hideSection(int textbookId, String chapterId, String sectionId) {
        try {
            etextbookRepository.hideSection(textbookId, chapterId, sectionId);
        } catch (Exception e) {
            System.err.println("Error hiding section: " + e.getMessage());
        }
    }

}
    
