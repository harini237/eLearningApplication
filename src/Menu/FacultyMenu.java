package Menu;

import java.util.Scanner;
import java.util.Stack;
import Service.EtextbookService;
import Service.CourseService;

public class FacultyMenu {

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
