package Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Entity.*;
import Repository.ActivityRepository;
import Repository.EtextbookRepository;

public class EtextbookService {

    private final EtextbookRepository etextbookRepository;
    private final ActivityRepository activityRepository;
    private final PermissionService permissionService;

    // Constructor
    public EtextbookService() {

        this.etextbookRepository = new EtextbookRepository();
        this.activityRepository = new ActivityRepository();
        this.permissionService = new PermissionService();
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
    

    public void addChapter(String chapterId, int textbookId, String title, String createdBy) {    
        try {
            etextbookRepository.addChapter(chapterId, textbookId, title, createdBy);
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

    public void deleteChapter(int textbookId, String chapterId, User user) {
        try {
            Chapter chapter = etextbookRepository.getChapter(textbookId, chapterId);
            if(permissionService.canDeleteChapter(user, chapter)){
                etextbookRepository.deleteChapter(chapterId, textbookId);
            } else {
                System.out.println("Sorry Restricted Access, You can't delete this section!!!");
            }
        } catch (Exception e) {
            System.err.println("Error deleting chapter: " + e.getMessage());
        }
    }

    public void hideChapter(int textbookId, String chapterId) {
        try {
            etextbookRepository.hideChapter(chapterId, textbookId);
        } catch (Exception e) {
            System.err.println("Error hiding chapter: " + e.getMessage());
        }
    }

    public void addSection(int textbookId, String chapterId, String sectionNumber, String title, String createdBy) {
        try {
            etextbookRepository.addSection(textbookId, chapterId, sectionNumber, title, createdBy);
        } catch (Exception e) {
            System.err.println("Error adding section: " + e.getMessage());
        }
    }

    public void addContentBlock(String contentBlockId, String sectionNumber, String chapterId, int textbookId, String content, String contentType, String createdBy, String modifiedBy) {
        try {
            etextbookRepository.addContentBlock(textbookId, chapterId, sectionNumber, contentBlockId, contentType, content, createdBy, modifiedBy);
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
    public void deleteActivity(String contentBlockId, String sectionId, String chapterId, int textbookId, String uniqueActivityId)  {
        try {
            activityRepository.deleteActivity(contentBlockId, sectionId, chapterId, textbookId, uniqueActivityId) ;
            System.out.println("Activity deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting activity: " + e.getMessage());
        }
    }
    public void hideActivity(String contentBlockId, String sectionId, String chapterId, int textbookId, String uniqueActivityId) {
        try {
            activityRepository.hideActivity(contentBlockId, sectionId, chapterId, textbookId, uniqueActivityId);
            System.out.println("Activity hidden successfully.");
        } catch (Exception e) {
            System.err.println("Error hiding activity: " + e.getMessage());
        }
    }
    
    public void deleteContentBlock(String contentBlockId, String sectionId, String chapterId, int textbookId, User user) {
        try {
            ContentBlock content = etextbookRepository.getContentBlock(contentBlockId, sectionId, chapterId, textbookId);
            if(permissionService.canDeleteContent(user, content)){
                etextbookRepository.deleteContentBlock(contentBlockId, sectionId, chapterId, textbookId);
                System.out.println("Content block deleted successfully.");

            } else {
                System.out.println("Sorry Restricted Access, You can't delete this section!!!");
            }
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
    
    public void deleteSection(int textbookId, String chapterId, String sectionId, User user) {
        try {
            // Get Section
            Section section = etextbookRepository.getSection(textbookId, chapterId, sectionId);
            if(permissionService.canDeleteSection(user, section)){
                etextbookRepository.deleteSection(textbookId, chapterId, sectionId);
            } else {
                System.out.println("Sorry Restricted Access, You can't delete this section!!!");
            }

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

    public  void displayContentBlock(String sectionId, String chapterId, int textbookId, String studentId) {
        try {
            etextbookRepository.displaySectionContent(sectionId, chapterId, textbookId, studentId);

        } catch (Exception e) {
            System.err.println("Error hiding section: " + e.getMessage());
        }
    }

}
    
