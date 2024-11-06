package Menu;

import java.util.Scanner;
import java.util.Stack;

import Entity.User;
import Service.EtextbookService;
import Service.CourseService;

public class FacultyMenu {
    User loggedUser;

    private final EtextbookService eTextbookService = new EtextbookService();
    private final CourseService courseService = new CourseService();
    private final Stack<Runnable> navigationStack = new Stack<>(); // Stack to manage previous menus

    public void displayFacultyMenu() {
        goToLandingPage();  // Initialize stack with the main Faculty menu

        while (!navigationStack.isEmpty()) {
            navigationStack.peek().run(); // Execute the current menu state
        }
    }

    // Initialize the landing page for the Faculty menu
    private void goToLandingPage() {
        navigationStack.clear(); // Clear stack and push only the main faculty menu
        navigationStack.push(this::mainFacultyMenu);
    }

    // Main Faculty menu options
    private void mainFacultyMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Faculty Menu ---");
        System.out.println("1. Modify E-Textbooks");
        System.out.println("2. Add Chapters or Sections");
        System.out.println("3. Manage Course Materials");
        System.out.println("4. View/Approve Menu.Student Enrollment");
        System.out.println("5. Logout");
        System.out.print("Enter choice (1-5): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(this::modifyETextbook);
            case 2 -> navigationStack.push(this::addChapterOrSection);
            case 3 -> navigationStack.push(this::manageCourseMaterials);
            case 4 -> navigationStack.push(this::viewOrApproveEnrollment);
            case 5 -> {
                System.out.println("Logging out...");
                navigationStack.clear(); // End the menu loop by clearing the stack
            }
            default -> {
                System.out.println("Invalid choice. Returning to Main Faculty Menu.");
            }
        }
    }

    // Option 1: Modify E-Textbooks
    private void modifyETextbook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Modify E-Textbooks ---");
        System.out.print("Enter E-textbook ID: ");
        int textbookId = scanner.nextInt();

        System.out.println("\n1. Add New Chapter");
        System.out.println("2. Modify Existing Chapter");
        System.out.println("3. Go Back");
        System.out.print("Enter choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(() -> addChapter(scanner, textbookId));
            case 2 -> navigationStack.push(() -> modifyChapter(scanner, textbookId));
            case 3 -> navigationStack.pop();  // Go back to Main Faculty Menu
            default -> System.out.println("Invalid choice. Returning to Modify E-Textbooks.");
        }
    }

    // Option 1.1: Add New Chapter to an E-Textbook
    private void addChapter(Scanner scanner, int textbookId) {
        System.out.println("\n--- Add New Chapter ---");
        System.out.print("Enter Chapter ID: ");
        String chapterId = scanner.next();
        System.out.print("Enter Chapter Title: ");
        String title = scanner.next();

        eTextbookService.addChapter(chapterId, textbookId, title);
        System.out.println("Chapter added successfully.");
        navigationStack.push(this::modifyETextbook); // Return to Modify E-Textbooks menu
    }
    

    // Option 1.2: Modify an Existing Chapter
    private void modifyChapter(Scanner scanner, int textbookId) {
        System.out.println("\n--- Modify Chapter ---");
        System.out.print("Enter Chapter ID to modify: ");
        String chapterId = scanner.next();

        System.out.println("\n1. Add New Section");
        System.out.println("2. Modify Section");
        System.out.println("3. Delete Section");
        System.out.print("Enter choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(() -> addSection(scanner, textbookId, chapterId));
            case 2 -> navigationStack.push(() -> modifySection(scanner, textbookId, chapterId));
            case 3 -> deleteSection(scanner, textbookId, chapterId);
            default -> System.out.println("Invalid choice. Returning to Modify Chapter.");
        }
    }

    //TO DO : add calls for modifying content block
    
    

    // Option to hide an activity
    private void hideActivity(Scanner scanner, String contentBlockID, String sectionId, String chapterId, int textbookId) {
        System.out.println("\n--- Hide Activity ---");
        System.out.print("Enter Unique Activity ID to hide: ");
        String uniqueActivityId = scanner.next();

        eTextbookService.hideActivity(contentBlockID, sectionId, chapterId, textbookId, uniqueActivityId);
        System.out.println("Activity hidden successfully.");
    }
    private void addActivity(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId) {
        System.out.println("\n--- Add New Activity ---");
        System.out.print("Enter Activity ID: ");
        String activityId = scanner.next();

        System.out.println("\n1. Add Question");
        System.out.println("2. Go Back");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();

        if (choice == 1) { 
            navigationStack.push(() -> addQuestion(scanner, textbookId, chapterId, sectionNumber, contentBlockId, activityId));
        } else {
            navigationStack.pop();  // Go back to Content Block
        }
    }
    private void addQuestion(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId, String activityId) {
        System.out.println("\n--- Add Question ---");
        System.out.print("Enter Question ID: ");
        String questionId = scanner.next();
        System.out.print("Enter Question Text: ");
        scanner.nextLine(); // Consume newline
        String questionText = scanner.nextLine();
    
        System.out.print("Enter Option 1: ");
        String option1 = scanner.nextLine();
        System.out.print("Enter Option 2: ");
        String option2 = scanner.nextLine();
        System.out.print("Enter Option 3: ");
        String option3 = scanner.nextLine();
        System.out.print("Enter Option 4: ");
        String option4 = scanner.nextLine();
    
        System.out.print("Enter Correct Option Number (1-4): ");
        int correctOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        System.out.print("Enter Explanation for Option 1: ");
        String explanation1 = scanner.nextLine();
        System.out.print("Enter Explanation for Option 2: ");
        String explanation2 = scanner.nextLine();
        System.out.print("Enter Explanation for Option 3: ");
        String explanation3 = scanner.nextLine();
        System.out.print("Enter Explanation for Option 4: ");
        String explanation4 = scanner.nextLine();
    
        try {
            // First, add the content block and activity
            eTextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, activityId, "activity", this.loggedUser.getId(), this.loggedUser.getId());
            eTextbookService.addActivity(activityId, sectionNumber, chapterId, textbookId, contentBlockId);
    
            // Attempt to add the question
            eTextbookService.addQuestion(activityId, textbookId, sectionNumber, chapterId, contentBlockId, questionId, questionText, option1, explanation1, option2, explanation2, option3, explanation3, option4, explanation4, correctOption);
            
            System.out.println("Question added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding question: " + e.getMessage());
    
            // Rollback: delete the associated activity and content block if question creation fails
            System.out.println("Rolling back changes due to error.");
            eTextbookService.deleteActivity(contentBlockId,sectionNumber,chapterId, textbookId,  activityId); // Implement deleteActivity method in EtextbookService
            eTextbookService.deleteContentBlock(contentBlockId, sectionNumber, chapterId, textbookId); // Implement deleteContentBlock method in EtextbookService
    
            System.out.println("Associated activity and content block deleted successfully.");
        }
    
        navigationStack.pop();  // Go back to Activity page
    }
    // Option 2: Add Chapters or Sections
    private void addChapterOrSection() {
        System.out.println("\n--- Add Chapters or Sections ---");
        navigationStack.pop(); // Return to Main Faculty Menu after this action
    }

    // Option 1.2.1: Add New Section to a Chapter
    private void addSection(Scanner scanner, int textbookId, String chapterId) {
        System.out.println("\n--- Add New Section ---");
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.next();
        System.out.print("Enter Section Title: ");
        String title = scanner.next();

        eTextbookService.addSection(textbookId, chapterId, sectionId, title);
        System.out.println("Section added successfully.");
        navigationStack.push(() -> modifyChapter(scanner, textbookId)); // Return to Modify Chapter
    }

    // Option 1.2.2: Modify an Existing Section
    private void modifySection(Scanner scanner, int textbookId, String chapterId) {
        System.out.println("\n--- Modify Section ---");
        System.out.print("Enter Section ID to modify: ");
        String sectionId = scanner.next();

        // Additional modification logic here
        System.out.println("Section modified successfully.");
        navigationStack.push(() -> modifyChapter(scanner, textbookId)); // Return to Modify Chapter
    }

    // Option 1.2.3: Delete a Section
    private void deleteSection(Scanner scanner, int textbookId, String chapterId) {
        System.out.print("Enter Section ID to delete: ");
        String sectionId = scanner.next();

        eTextbookService.deleteSection(textbookId, chapterId, sectionId);
        System.out.println("Section deleted successfully.");
        navigationStack.push(() -> modifyChapter(scanner, textbookId)); // Return to Modify Chapter
    }

    // Option 3: Manage Course Materials
    private void manageCourseMaterials() {
        System.out.println("\n--- Manage Course Materials ---");
        // Logic to set content as hidden, view materials, etc.
        navigationStack.pop();
    }

    // Option 4: View or Approve Menu.Student Enrollment
    private void viewOrApproveEnrollment() {
        System.out.println("\n--- View or Approve Menu.Student Enrollment ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Course ID to view enrollment list: ");
        String courseId = scanner.next();

        System.out.println("\n1. Approve Enrollment");
        System.out.println("2. Go Back");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> approveEnrollment(scanner, courseId);
            case 2 -> navigationStack.pop();  // Go back to Main Faculty Menu
            default -> System.out.println("Invalid choice. Returning to View Enrollment.");
        }
    }

    // Sub-option for approving enrollment
    private void approveEnrollment(Scanner scanner, String courseId) {
        System.out.print("Enter Menu.Student ID to approve: ");
        String studentId = scanner.next();

        courseService.approveEnrollment(courseId, studentId);
        System.out.println("Menu.Student enrollment approved.");
        navigationStack.pop(); // Return to View/Approve Enrollment
    }
}
