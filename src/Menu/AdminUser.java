package Menu;

import Entity.User;
import Service.UserService;
import Entity.Etextbook;
import Service.EtextbookService;

import java.util.Scanner;

public class AdminUser {

    private final UserService userService = new UserService();
     private final EtextbookService etextbookService = new EtextbookService();
//    private final CourseService courseService = new CourseService();

    public void displayAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Create Faculty Account");
            System.out.println("2. Create E-textbook");
            System.out.println("3. Modify E-textbooks");
            System.out.println("4. Create New Active Course");
            System.out.println("5. Create New Evaluation Course");
            System.out.println("6. Logout");
            System.out.print("Enter choice (1-6): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createFacultyAccount(scanner);
                case 2 -> createEtextbook(scanner);
                case 3 -> modifyEtextbook(scanner);
                case 4 -> createActiveCourse(scanner);
                case 5 -> createEvaluationCourse(scanner);
                case 6 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    public void displayChapterMenu(int textbookId) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Chapter Menu ---");
            System.out.println("1. Add New Chapter");
            System.out.println("2. Go Back");
            System.out.print("Enter choice (1-2): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addChapter(scanner, textbookId);
                case 2 -> displayAdminMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    private void createFacultyAccount(Scanner scanner) {
        System.out.println("Creating Faculty Account");
        System.out.print("Enter First Name: ");
        String firstName = scanner.next();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.next();
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);  // The password should be hashed in UserService
        newUser.setRoleId(2);  // Assuming 2 is the role ID for Faculty

        userService.createUser(newUser);
        System.out.println("Faculty account created successfully.");
    }

    private void createEtextbook(Scanner scanner) {
        System.out.println("Creating E-textbook");
        System.out.print("Enter E-textbook Title: ");
        String title = scanner.next();
        System.out.print("Enter Unique E-textbook ID: ");
        int textbookId = scanner.nextInt();

        etextbookService.createEtextbook(textbookId, title);

        // Automatically proceed to add a chapter
        displayChapterMenu(textbookId);
        
    }

    private void modifyEtextbook(Scanner scanner) {
        System.out.print("Enter Unique E-textbook ID to Modify: ");
        int textbookId = scanner.nextInt();

        int choice;
        do {
            System.out.println("\n--- Modify E-textbook ---");
            System.out.println("1. Add New Chapter");
            System.out.println("2. Modify Existing Chapter");
            System.out.println("3. Go Back");
            System.out.print("Enter choice (1-3): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addChapter(scanner, textbookId);
                case 2 -> modifyChapter(scanner, textbookId);
                case 3 -> System.out.println("Returning to Admin Menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void addChapter(Scanner scanner, int textbookId) {
        System.out.print("Enter Chapter Number: ");
        String chapterNumber = scanner.next();
        
    // Validate chapter number to ensure it follows the "chapXX" format
    if (!chapterNumber.matches("chap\\d{2}")) {
        System.out.println("Invalid chapter number. Please enter a value in the format 'chapXX' (e.g., 'chap01', 'chap12').");
        return;
    }
    
        System.out.print("Enter Chapter Title: ");
        String title = scanner.next();
        
        // Check if title is empty
        if (title.trim().isEmpty()) {
            System.out.println("Chapter title cannot be empty. Please try again.");
            return;
        }
    
        // Proceed with adding the chapter
        etextbookService.addChapter(chapterNumber, textbookId, title);
    }
    

    private void modifyChapter(Scanner scanner, int textbookId) {
        System.out.print("Enter Chapter Number to Modify: ");
        String chapterNumber = scanner.next();
        System.out.print("Enter New Chapter Title: ");
        String title = scanner.next();

//        etextbookService.modifyChapter(textbookId, chapterNumber, title);
        System.out.println("Chapter modified successfully.");
    }

    private void createActiveCourse(Scanner scanner) {
        System.out.println("Creating Active Course");
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        System.out.print("Enter Course Title: ");
        String courseTitle = scanner.next();
        System.out.print("Enter Faculty ID: ");
        int facultyId = scanner.nextInt();
        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        String startDate = scanner.next();
        System.out.print("Enter End Date (yyyy-mm-dd): ");
        String endDate = scanner.next();
        System.out.print("Enter Course Capacity: ");
        int capacity = scanner.nextInt();

//        courseService.createActiveCourse(courseId, courseTitle, facultyId, startDate, endDate, capacity);
        System.out.println("Active course created successfully.");
    }

    private void createEvaluationCourse(Scanner scanner) {
        System.out.println("Creating Evaluation Course");
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        System.out.print("Enter Course Title: ");
        String courseTitle = scanner.next();
        System.out.print("Enter Faculty ID: ");
        int facultyId = scanner.nextInt();
        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        String startDate = scanner.next();
        System.out.print("Enter End Date (yyyy-mm-dd): ");
        String endDate = scanner.next();

//        courseService.createEvaluationCourse(courseId, courseTitle, facultyId, startDate, endDate);
        System.out.println("Evaluation course created successfully.");
    }
}
