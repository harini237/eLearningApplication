package Menu;

import Entity.User;
import Service.CourseService;
import Service.EtextbookService;
import Service.UserService;

import java.util.Scanner;
import java.util.Stack;

public class FacultyMenu {

    private final UserService userService = new UserService();
    private final EtextbookService etextbookService = new EtextbookService();
    private final CourseService courseService = new CourseService();
    private final Stack<Runnable> navigationStack = new Stack<>();
    private User loggedUser;

    public void displayFacultyMenu(User user) {
        this.loggedUser = user;
        goToLandingPage();
        while (!navigationStack.isEmpty()) {
            navigationStack.peek().run();
        }
    }

    private void goToLandingPage() {
        navigationStack.clear();
        navigationStack.push(this::facultyLandingMenu);
    }

    private void facultyLandingMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Faculty Landing Menu ---");
        System.out.println("1. Go to Active Course");
        System.out.println("2. Go to Evaluation Course");
        System.out.println("3. View Courses");
        System.out.println("4. Change Password");
        System.out.println("5. Logout");
        System.out.print("Enter choice (1-5): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(this::activeCourseMenu);
            case 2 -> navigationStack.push(this::evaluationCourseMenu);
//            case 3 -> facultyService.viewCourses();
            case 4 -> changePassword();
            case 5 -> {
                navigationStack.pop();
                System.out.println("Logging out...");
            }
            default -> System.out.println("Invalid choice. Returning to Faculty Landing Menu.");
        }
    }

    // Active Course Menu
    private void activeCourseMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Active Course ID: ");
        String courseId = scanner.next();

        System.out.print("Enter Textbook ID: ");
        int textbookId = scanner.nextInt();

        System.out.println("\n--- Active Course Menu ---");
        System.out.println("1. View Worklist");
        System.out.println("2. Approve Enrollment");
        System.out.println("3. View Students");
        System.out.println("4. Add New Chapter");
        System.out.println("5. Modify Chapters");
        System.out.println("6. Add TA");
        System.out.println("7. Go Back");
        System.out.print("Enter choice (1-7): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> courseService.viewWorklist(courseId);
            case 2 -> approveEnrollment(courseId);
            case 3 -> courseService.viewStudentsEnrolled(courseId);
            case 4 -> addNewChapter(textbookId);
            case 5 -> modifyChapter(textbookId);
            case 6 -> addTA(courseId);
            case 7 -> navigationStack.pop();
            default -> System.out.println("Invalid choice. Returning to Active Course Menu.");
        }
    }

    // Evaluation Course Menu
    private void evaluationCourseMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Evaluation Course ID: ");
        String courseId = scanner.next();

        System.out.print("Enter Textbook  ID: ");
        int textbookId = scanner.nextInt();

        System.out.println("\n--- Evaluation Course Menu ---");
        System.out.println("1. Add New Chapter");
        System.out.println("2. Modify Chapters");
        System.out.println("3. Go Back");
        System.out.print("Enter choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addNewChapter(textbookId);
            case 2 -> modifyChapter(textbookId);
            case 3 -> navigationStack.pop();
            default -> System.out.println("Invalid choice. Returning to Evaluation Course Menu.");
        }
    }

    // View Courses
    private void viewCourses() {
//        facultyService.viewCourses();
    }

    // Change Password
    private void changePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter current password: ");
        String currentPassword = scanner.next();
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.next();

        if (newPassword.equals(confirmPassword)) {
            userService.resetPassword(loggedUser.getId(), currentPassword, newPassword);
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Passwords do not match. Returning to Faculty Landing Menu.");
        }
    }

    // Approve Enrollment
    private void approveEnrollment(String courseId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student ID to approve: ");
        String studentId = scanner.next();

//        facultyService.approveEnrollment(courseId, studentId);
        System.out.println("Student enrollment approved.");
    }

    // Add New Chapter
    private void addNewChapter(int textbookId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Unique Chapter ID: ");
        String chapterId = scanner.next();
        System.out.print("Enter Chapter Title: ");
        String chapterTitle = scanner.next();

        etextbookService.addChapter(chapterId, textbookId, chapterTitle);
        System.out.println("New chapter added successfully.");
    }

    // Modify Chapter Menu
    private void modifyChapter(int textbookId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Chapter ID to modify: ");
        String chapterId = scanner.next();

        System.out.println("\n--- Modify Chapter Menu ---");
        System.out.println("1. Hide Chapter");
        System.out.println("2. Delete Chapter");
        System.out.println("3. Add New Section");
        System.out.println("4. Modify Section");
        System.out.println("5. Go Back");
        System.out.print("Enter choice (1-5): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> etextbookService.hideChapter(textbookId, chapterId);
            case 2 -> etextbookService.deleteChapter(textbookId, chapterId);
            case 3 -> addNewSection(textbookId, chapterId);
            case 4 -> modifySection(textbookId, chapterId);
            case 5 -> navigationStack.pop();
            default -> System.out.println("Invalid choice. Returning to Modify Chapter Menu.");
        }
        navigationStack.push(() -> modifyChapter(textbookId));
    }

    // Add TA
    private void addTA(String courseId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter TA First Name: ");
        String firstName = scanner.next();
        System.out.print("Enter TA Last Name: ");
        String lastName = scanner.next();
        System.out.print("Enter TA Email: ");
        String email = scanner.next();
        System.out.print("Enter TA Default Password: ");
        String password = scanner.next();

//        facultyService.addTA(courseId, firstName, lastName, email, password);
        System.out.println("TA added successfully.");
    }

    // Add New Section
    private void addNewSection(int textbookId, String chapterId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.next();
        System.out.print("Enter Section Title: ");
        String sectionTitle = scanner.next();

        etextbookService.addSection(textbookId, chapterId, sectionId, sectionTitle);
        System.out.println("New section added successfully.");
    }

    // Modify Section Menu
    private void modifySection(int textbookId, String chapterId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Section ID to modify: ");
        String sectionId = scanner.next();

        System.out.println("\n--- Modify Section Menu ---");
        System.out.println("1. Hide Section");
        System.out.println("2. Delete Section");
        System.out.println("3. Add New Content Block");
        System.out.println("4. Modify Content Block");
        System.out.println("5. Go Back");
        System.out.print("Enter choice (1-5): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> etextbookService.hideSection(textbookId, chapterId, sectionId);
            case 2 -> etextbookService.deleteSection(textbookId, chapterId, sectionId);
            case 3 -> addContentBlock(textbookId, chapterId, sectionId);
            case 4 -> addModifyBlock(textbookId, chapterId, sectionId);
            case 5 -> navigationStack.push(() -> modifyChapter(textbookId));
            default -> System.out.println("Invalid choice. Returning to Modify Section Menu.");
        }
        navigationStack.push(() -> modifySection(textbookId, chapterId));
    }

    // Add Content Block
    private void addContentBlock(int textbookId, String chapterId, String sectionId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Content Block ID: ");
        String blockId = scanner.next();

        System.out.println("\n--- Add Content Block Menu ---");
        System.out.println("1. Add Text");
        System.out.println("2. Add Picture");
        System.out.println("3. Add Activity");
        System.out.println("4. Go Back");
        System.out.print("Enter choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addTextBlock(scanner, textbookId, chapterId, sectionId, blockId);
            case 2 -> addPictureBlock(scanner, textbookId, chapterId, sectionId, blockId);
            case 3 -> addActivity(scanner, textbookId, chapterId, sectionId, blockId);
            case 4 -> navigationStack.push(() -> modifySection(textbookId, chapterId));
            default -> System.out.println("Invalid choice. Returning to Add Content Block Menu.");
        }
        navigationStack.push(() -> addContentBlock(textbookId, chapterId, sectionId));
    }

    private void addModifyBlock(int textbookId, String chapterId, String sectionId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Content Block ID: ");
        String blockId = scanner.next();

        System.out.println("\n--- Add Content Block Menu ---");
        System.out.println("1. Hide Content Block");
        System.out.println("2. Delete Content Block");
        System.out.println("1. Add Text");
        System.out.println("2. Add Picture");
        System.out.println("3. Add Activity");
        System.out.println("4. Go Back");
        System.out.print("Enter choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> hideContentBlock(scanner, textbookId, chapterId, sectionId, blockId);
            case 2 -> deleteContentBlock(scanner, textbookId, chapterId, sectionId, blockId);
            case 3 -> addTextBlock(scanner, textbookId, chapterId, sectionId, blockId);
            case 4 -> addPictureBlock(scanner, textbookId, chapterId, sectionId, blockId);
            case 5 -> addActivity(scanner, textbookId, chapterId, sectionId, blockId);
            case 6 -> navigationStack.push(() -> modifySection(textbookId, chapterId));
            default -> System.out.println("Invalid choice. Returning to Add Content Block Menu.");
        }
        navigationStack.push(() -> addContentBlock(textbookId, chapterId, sectionId));
    }

    private void hideContentBlock(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId) {
        System.out.println("\n1. Save");
        System.out.println("2. Cancel");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();
        
        if (choice == 1) {
            etextbookService.hideContentBlock(contentBlockId, sectionNumber, chapterId, textbookId);
            navigationStack.pop();
        } else {
            navigationStack.pop();  // Go back to Content Block
        }
    }   

    private void deleteContentBlock(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId) {
        System.out.println("\n1. Save");
        System.out.println("2. Cancel");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();
        
        if (choice == 1) {
            etextbookService.deleteContentBlock(contentBlockId, sectionNumber, chapterId, textbookId);
            navigationStack.pop();
        } else {
            navigationStack.pop();  // Go back to Content Block
        }
    }


    private void addTextBlock(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId) {
        System.out.println("\n--- Add New Text ---");
        System.out.print("Enter Text: ");
        scanner.nextLine(); // Consume the newline left by nextInt()
        String content = scanner.nextLine();  // The content input
    
        System.out.println("\n1. Add");
        System.out.println("2. Go Back");
        System.out.println("3. Landing Page");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();
        
        if (choice == 1) {
            etextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, content, "text", this.loggedUser.getId(), this.loggedUser.getId()); 
            navigationStack.pop();
        } else {
            navigationStack.pop();  // Go back to Content Block
        }
    }
    private void addPictureBlock(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId) {
        System.out.println("\n--- Add New Picture ---");
        System.out.print("Enter Picture Path: ");
        scanner.nextLine(); // Consume newline
        String picturePath = scanner.nextLine();

        System.out.println("\n1. Add");
        System.out.println("2. Go Back");
        System.out.println("3. Landing Page");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();
    
        if (choice == 1) {
            etextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, picturePath, "picture",this.loggedUser.getId(), this.loggedUser.getId());
            navigationStack.pop();
        } else {
            navigationStack.pop();  // Go back to Content Block
        }   
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
            etextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, activityId, "activity", this.loggedUser.getId(), this.loggedUser.getId());
            etextbookService.addActivity(activityId, sectionNumber, chapterId, textbookId, contentBlockId);
    
            // Attempt to add the question
            etextbookService.addQuestion(activityId, textbookId, sectionNumber, chapterId, contentBlockId, questionId, questionText, option1, explanation1, option2, explanation2, option3, explanation3, option4, explanation4, correctOption);
            
            System.out.println("Question added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding question: " + e.getMessage());
    
            // Rollback: delete the associated activity and content block if question creation fails
            System.out.println("Rolling back changes due to error.");
            etextbookService.deleteActivity(contentBlockId, sectionNumber, chapterId, textbookId, activityId); // Implement deleteActivity method in EtextbookService
            etextbookService.deleteContentBlock(contentBlockId, sectionNumber, chapterId, textbookId); // Implement deleteContentBlock method in EtextbookService
    
            System.out.println("Associated activity and content block deleted successfully.");
        }
    
        navigationStack.pop();  // Go back to Activity page
    }
}
