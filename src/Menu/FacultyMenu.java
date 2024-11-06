package Menu;

import Entity.User;
import Service.UserService;

import java.util.Scanner;
import java.util.Stack;

public class FacultyMenu {

    private final UserService userService = new UserService();
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
            case 3 -> facultyService.viewCourses();
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
            case 1 -> facultyService.viewWorklist(courseId);
            case 2 -> approveEnrollment(courseId);
            case 3 -> facultyService.viewStudents(courseId);
            case 4 -> addNewChapter(courseId);
            case 5 -> modifyChapter(courseId);
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

        System.out.println("\n--- Evaluation Course Menu ---");
        System.out.println("1. Add New Chapter");
        System.out.println("2. Modify Chapters");
        System.out.println("3. Go Back");
        System.out.print("Enter choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addNewChapter(courseId);
            case 2 -> modifyChapter(courseId);
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
    private void addNewChapter(String courseId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Unique Chapter ID: ");
        String chapterId = scanner.next();
        System.out.print("Enter Chapter Title: ");
        String chapterTitle = scanner.next();

        facultyService.addNewChapter(courseId, chapterId, chapterTitle);
        System.out.println("New chapter added successfully.");
    }

    // Modify Chapter Menu
    private void modifyChapter(String courseId) {
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
            case 1 -> facultyService.hideChapter(courseId, chapterId);
            case 2 -> facultyService.deleteChapter(courseId, chapterId);
            case 3 -> addNewSection(courseId, chapterId);
            case 4 -> modifySection(courseId, chapterId);
            case 5 -> navigationStack.pop();
            default -> System.out.println("Invalid choice. Returning to Modify Chapter Menu.");
        }
        navigationStack.push(() -> modifyChapter(courseId));
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
    private void addNewSection(String courseId, String chapterId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Section ID: ");
        String sectionId = scanner.next();
        System.out.print("Enter Section Title: ");
        String sectionTitle = scanner.next();

//        facultyService.addNewSection(courseId, chapterId, sectionId, sectionTitle);
        System.out.println("New section added successfully.");
    }

    // Modify Section Menu
    private void modifySection(String courseId, String chapterId) {
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
//            case 1 -> facultyService.hideSection(courseId, chapterId, sectionId);
//            case 2 -> facultyService.deleteSection(courseId, chapterId, sectionId);
            case 3 -> addContentBlock(courseId, chapterId, sectionId);
//            case 4 -> modifyContentBlock(courseId, chapterId, sectionId);
            case 5 -> navigationStack.push(() -> modifyChapter(courseId));
            default -> System.out.println("Invalid choice. Returning to Modify Section Menu.");
        }
        navigationStack.push(() -> modifySection(courseId, chapterId));
    }

    // Add Content Block
    private void addContentBlock(String courseId, String chapterId, String sectionId) {
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
//            case 1 -> facultyService.addTextContent(courseId, chapterId, sectionId, blockId);
//            case 2 -> facultyService.addPictureContent(courseId, chapterId, sectionId, blockId);
//            case 3 -> facultyService.addActivityContent(courseId, chapterId, sectionId, blockId);
            case 4 -> navigationStack.push(() -> modifySection(courseId, chapterId));
            default -> System.out.println("Invalid choice. Returning to Add Content Block Menu.");
        }
        navigationStack.push(() -> addContentBlock(courseId, chapterId, sectionId));
    }
}
