package Menu;
import Service.CourseService;
import Service.UserService;
import Entity.User;
import Service.EtextbookService;

import java.sql.Date;
import java.util.Scanner;
import java.util.Stack;

public class AdminMenu {

    User loggedUser;

    private final UserService userService = new UserService();
    private final EtextbookService etextbookService = new EtextbookService();
    private final CourseService courseService = new CourseService();
    private final Stack<Runnable> navigationStack = new Stack<>();  // Stack to track previous menus

    public void displayAdminMenu(User loggingUser) {
        goToLandingPage();  // Initialize stack with the landing page
        if(this.loggedUser == null){
            if(loggingUser.getId().isEmpty()){
                throw new SecurityException("User Not Logged In");
            }
            this.loggedUser = loggingUser;
        }
        while (!navigationStack.isEmpty()) {
            navigationStack.peek().run();  // Execute the current menu state
        }
    }

    public void displayAdminMenu() {
        goToLandingPage();  // Initialize stack with the landing page

        while (!navigationStack.isEmpty()) {
            navigationStack.peek().run();  // Execute the current menu state
        }
    }

    private void goToLandingPage() {
        navigationStack.clear();
        navigationStack.push(this::mainAdminMenu);  // Only the main admin menu in the stack
    }

    private void mainAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Create Faculty Account");
        System.out.println("2. Create E-textbook");
        System.out.println("3. Modify E-textbooks");
        System.out.println("4. Create New Active Course");
        System.out.println("5. Create New Evaluation Course");
        System.out.println("6. Logout");
        System.out.print("Enter choice (1-6): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(() -> createFacultyAccount(scanner));
            case 2 -> navigationStack.push(() -> createEtextbook(scanner));
            case 3 -> navigationStack.push(() -> modifyEtextbook(scanner));
            case 4 -> navigationStack.push(() -> createActiveCourse(scanner));
            case 5 -> navigationStack.push(() -> createEvaluationCourse(scanner));
            case 6 -> {
                System.out.println("Logging out...");
                navigationStack.clear();  // Clear the stack to end the menu loop
            }
            default -> {
                System.out.println("Invalid choice. Returning to Main Admin Menu...");
                navigationStack.push(this::mainAdminMenu);
            }
        }
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

        System.out.println("\n1. Add User");
        System.out.println("2. Go Back");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPassword(password);  // The password should be hashed in UserService
            newUser.setRoleId(2);  // Assuming 2 is the role ID for Faculty
            try {
                userService.createUser(newUser);
                System.out.println("Faculty account created successfully.");
            }catch (Exception ignored){
                System.out.println("Error While creating user account Error: " + ignored.getMessage());
            }
        }
        // Go back to Main Admin Menu by popping the last menu state
        navigationStack.pop();
    }

    private void createEtextbook(Scanner scanner) {
        System.out.println("\n--- Create E-textbook ---");
        System.out.print("Enter E-textbook Title: ");
        String title = scanner.next();
        System.out.print("Enter Unique E-textbook ID: ");
        int textbookId = scanner.nextInt();

        etextbookService.createEtextbook(textbookId, title);

        System.out.println("\n1. Add New Chapter");
        System.out.println("2. Go Back");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();
        
        if (choice == 1) {
            navigationStack.push(() -> addChapter(scanner, textbookId));
        } else {
            navigationStack.pop();  // Go back to Main Admin Menu
        }
    }

    private void addChapter(Scanner scanner, int textbookId) {
        System.out.println("\n--- Add New Chapter ---");
        System.out.print("Enter Chapter ID: ");
        String chapterId = scanner.next();
        // Validate chapter number to ensure it follows the "chapXX" format
        if (!chapterId.matches("chap\\d{2}")) {
            System.out.println("Invalid chapter number. Please enter a value in the format.");
            return;
        }
        
        System.out.print("Enter Chapter Title: ");
        String title = scanner.next();
        // Check if title is empty
        if (title.trim().isEmpty()) {
            System.out.println("Chapter title cannot be empty. Please try again.");
            return;
        }
        
        etextbookService.addChapter(chapterId, textbookId, title);


        System.out.println("\n1. Add New Section");
        System.out.println("2. Go Back");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            navigationStack.push(() -> addSection(scanner, textbookId, chapterId));
        } else {
            navigationStack.pop();  // Go back to E-textbook creation
        }

    }

    private void addSection(Scanner scanner, int textbookId, String chapterId) {
        System.out.println("\n--- Add New Section ---");
        System.out.print("Enter Section Number: ");
        String sectionNumber = scanner.next();
        // Validate section id to ensure it follows the "chapXX" format
        if (!sectionNumber.trim().matches("(?i)Sec\\d{2}")) { // '(?i)' makes the regex case-insensitive
        System.out.println("Invalid section number. Please enter a value in the format.");
        return;
    }
    
        System.out.print("Enter Section Title: ");
        String title = scanner.next();
        // Check if title is empty
        if (title.trim().isEmpty()) {
            System.out.println("Section title cannot be empty. Please try again.");
            return;
        }

        etextbookService.addSection(textbookId, chapterId, sectionNumber, title);

        System.out.println("\n1. Add New Content Block");
        System.out.println("2. Go Back");
        System.out.print("Enter choice (1-2): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            navigationStack.push(() -> addContentBlock(scanner, textbookId, chapterId, sectionNumber));
        } else {
            navigationStack.pop();  // Go back to Chapter creation
        }
    }

    private void addContentBlock(Scanner scanner, int textbookId, String chapterId, String sectionNumber) {
        System.out.println("\n--- Add New Content Block ---");
        System.out.print("Enter Content Block ID: ");
        String contentBlockId = scanner.next();

        System.out.println("\n1. Add Text");
        System.out.println("2. Add Picture");
        System.out.println("3. Add Activity");
        System.out.println("4. Go Back");
        System.out.print("Enter choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
//            case 1 -> etextbookService.addTextBlock(textbookId, chapterId, sectionNumber, contentBlockId);
//            case 2 -> etextbookService.addPictureBlock(textbookId, chapterId, sectionNumber, contentBlockId);
            case 1 -> navigationStack.push(() -> addTextBlock(scanner, textbookId, chapterId, sectionNumber, contentBlockId));
            case 2 -> navigationStack.push(() -> addPictureBlock(scanner, textbookId, chapterId, sectionNumber, contentBlockId));
            case 3 -> navigationStack.push(() -> addActivity(scanner, textbookId, chapterId, sectionNumber, contentBlockId));
            case 4 -> navigationStack.pop();  // Go back to Section creation
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
            etextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, content,  this.loggedUser.getId(), this.loggedUser.getId()); // Pass the content directly
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
            etextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, picturePath, this.loggedUser.getId(), this.loggedUser.getId());
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
            etextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, activityId, this.loggedUser.getId(), this.loggedUser.getId());
            etextbookService.addActivity(activityId, sectionNumber, chapterId, textbookId, contentBlockId);
    
            // Attempt to add the question
            etextbookService.addQuestion(activityId, textbookId, sectionNumber, chapterId, contentBlockId, questionId, questionText, option1, explanation1, option2, explanation2, option3, explanation3, option4, explanation4, correctOption);
            
            System.out.println("Question added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding question: " + e.getMessage());
    
            // Rollback: delete the associated activity and content block if question creation fails
            System.out.println("Rolling back changes due to error.");
            etextbookService.deleteActivity(activityId); // Implement deleteActivity method in EtextbookService
            etextbookService.deleteContentBlock(contentBlockId, sectionNumber, chapterId, textbookId); // Implement deleteContentBlock method in EtextbookService
    
            System.out.println("Associated activity and content block deleted successfully.");
        }
    
        navigationStack.pop();  // Go back to Activity page
    }
    
    
    // private void addQuestion(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId, String activityId) {
    //     System.out.println("\n--- Add Question ---");
    //     System.out.print("Enter Question ID: ");
    //     String questionId = scanner.next();
    //     System.out.print("Enter Question Text: ");
    //     scanner.nextLine(); // Consume newline
    //     String questionText = scanner.nextLine();

    //     System.out.print("Enter Option 1: ");
    //     String option1 = scanner.nextLine();
    //     System.out.print("Enter Option 2: ");
    //     String option2 = scanner.nextLine();
    //     System.out.print("Enter Option 3: ");
    //     String option3 = scanner.nextLine();
    //     System.out.print("Enter Option 4: ");
    //     String option4 = scanner.nextLine();

    //     System.out.print("Enter Correct Option Number (1-4): ");
    //     int correctOption = scanner.nextInt();
    //     scanner.nextLine(); // Consume newline

    //     System.out.print("Enter Explanation for Option 1: ");
    //     String explanation1 = scanner.nextLine();
    //     System.out.print("Enter Explanation for Option 2: ");
    //     String explanation2 = scanner.nextLine();
    //     System.out.print("Enter Explanation for Option 3: ");
    //     String explanation3 = scanner.nextLine();
    //     System.out.print("Enter Explanation for Option 4: ");
    //     String explanation4 = scanner.nextLine();
    //     etextbookService.addContentBlock(contentBlockId, sectionNumber, chapterId, textbookId, activityId, this.loggedUser.getId(), this.loggedUser.getId());

    //     etextbookService.addActivity(activityId, sectionNumber, chapterId, textbookId, contentBlockId);


    //     etextbookService.addQuestion(activityId, textbookId, sectionNumber, chapterId, contentBlockId, questionId, questionText, option1, explanation1, option2, explanation2, option3, explanation3, option4, explanation4, correctOption);

    //     System.out.println("Question added successfully.");
    //     navigationStack.pop();  // Go back to Activity page
    // }


//     private void addQuestion(Scanner scanner, int textbookId, String chapterId, String sectionNumber, String contentBlockId, String activityId) {
//         System.out.println("\n--- Add Question ---");
//         System.out.print("Enter Question ID: ");
//         String questionId = scanner.next();
//         System.out.print("Enter Question Text: ");
//         String questionText = scanner.next();

// //        etextbookService.addActivity(textbookId, chapterId, sectionNumber, contentBlockId, activityId, questionId, questionText);
//         System.out.println("Question and Activity added successfully.");

//         navigationStack.pop();  // Go back to Activity page
//     }

    private void modifyEtextbook(Scanner scanner) {
        System.out.println("\n--- Modify E-textbook ---");
        System.out.print("Enter E-textbook ID: ");
        int textbookId = scanner.nextInt();

        System.out.println("\n1. Add New Chapter");
        System.out.println("2. Modify Chapter");
        System.out.println("3. Go Back");
        System.out.println("4. Landing Page");
        System.out.print("Enter choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(() -> addChapter(scanner, textbookId));
            case 2 -> navigationStack.push(() -> modifyChapter(scanner, textbookId));
            case 3 -> navigationStack.pop();
            case 4 -> goToLandingPage();
            default -> System.out.println("Invalid choice. Returning to Admin Menu.");
        }
    }

    private void modifyChapter(Scanner scanner, int textbookId) {
        System.out.println("\n--- Modify Chapter ---");
        System.out.print("Enter Chapter ID to Modify: ");
        String chapterId = scanner.next();

        // TODO: Add modify chapter logic
        System.out.println("\n1. Add New Section");
        System.out.println("2. Modify Section");
        System.out.println("3. Go Back");
        System.out.println("4. Landing Page");
        System.out.print("Enter choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(() -> addSection(scanner, textbookId, chapterId));
            case 2 -> navigationStack.push(() -> modifySection(scanner, textbookId, chapterId));            
            case 3 -> navigationStack.pop();
            case 4 -> goToLandingPage();
            default -> System.out.println("Invalid choice. Returning to Admin Menu.");
        }
        //navigationStack.pop();  // Go back to E-textbook modification
    }

    private void modifySection(Scanner scanner, int textbookId, String chapterId) {
        System.out.println("\n--- Modify Section ---");
        System.out.print("Enter Section ID to Modify: ");
        String sectionNumber = scanner.next();

        System.out.println("\n1. Add New Content Block");
        System.out.println("2. Modify Content Block");
        System.out.println("3. Go Back");
        System.out.println("4. Landing Page");
        System.out.print("Enter choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> navigationStack.push(() -> addContentBlock(scanner, textbookId, chapterId, sectionNumber));
           // case 2 -> navigationStack.push(() -> modifyContentBlock(scanner, textbookId));
            case 3 -> navigationStack.pop();
            case 4 -> goToLandingPage();
            default -> System.out.println("Invalid choice. Returning to Admin Menu.");
        }
        System.out.println("Chapter modified successfully.");
        //navigationStack.pop();  // Go back to E-textbook modification
    }

    private void createActiveCourse(Scanner scanner) {
        System.out.println("\n--- Create New Active Course ---");
        System.out.print("Enter Course ID: ");
        String courseId = scanner.next();
        System.out.print("Enter Course Title: ");
        String courseTitle = scanner.next();
        System.out.print("Enter textbook ID: ");
        Integer textbookId = scanner.nextInt();
        System.out.print("Enter Faculty ID: ");
        String facultyId = scanner.next();
        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        String startDate = scanner.next();
        System.out.print("Enter End Date (yyyy-mm-dd): ");
        String endDate = scanner.next();
        System.out.print("Enter unique course token: ");
        String token = scanner.next();
        System.out.print("Enter Course Capacity: ");
        int capacity = scanner.nextInt();

        String type = "active";

        courseService.createCourse(courseId, courseTitle, textbookId, facultyId, Date.valueOf(startDate), Date.valueOf(endDate), type, token, capacity);

        System.out.println("Active Course created successfully.");
        navigationStack.pop();  // Go back to Main Admin Menu
    }

    private void createEvaluationCourse(Scanner scanner) {
        System.out.println("\n--- Create New Evaluation Course ---");
        System.out.print("Enter Course ID: ");
        String courseId = scanner.next();
        System.out.print("Enter Course Title: ");
        String courseTitle = scanner.next();
        System.out.print("Enter textbook ID: ");
        Integer textbookId = scanner.nextInt();
        System.out.print("Enter Faculty ID: ");
        String facultyId = scanner.next();
        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        String startDate = scanner.next();
        System.out.print("Enter End Date (yyyy-mm-dd): ");
        String endDate = scanner.next();
        String type = "eval";

        this.courseService.createCourse(courseId, courseTitle, textbookId, facultyId, Date.valueOf(startDate), Date.valueOf(endDate), type, null, 0);

        System.out.println("Evaluation Course created successfully.");
        navigationStack.pop();  // Go back to Main Admin Menu
    }
}

//
//import Entity.User;
//import Service.UserService;
//import Entity.Etextbook;
//import Service.EtextbookService;
//
//import java.util.Scanner;
//
//public class AdminUser {
//
//    private final UserService userService = new UserService();
//     private final EtextbookService etextbookService = new EtextbookService();
////    private final CourseService courseService = new CourseService();
//
//    public void displayAdminMenu() {
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        do {
//            System.out.println("\n--- Admin Menu ---");
//            System.out.println("1. Create Faculty Account");
//            System.out.println("2. Create E-textbook");
//            System.out.println("3. Modify E-textbooks");
//            System.out.println("4. Create New Active Course");
//            System.out.println("5. Create New Evaluation Course");
//            System.out.println("6. Logout");
//            System.out.print("Enter choice (1-6): ");
//            choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1 -> createFacultyAccount(scanner);
//                case 2 -> createEtextbook(scanner);
//                case 3 -> modifyEtextbook(scanner);
//                case 4 -> createActiveCourse(scanner);
//                case 5 -> createEvaluationCourse(scanner);
//                case 6 -> System.out.println("Logging out...");
//                default -> System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 6);
//    }
//
//    public void displayChapterMenu(int textbookId) {
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//
//        do {
//            System.out.println("\n--- Chapter Menu ---");
//            System.out.println("1. Add New Chapter");
//            System.out.println("2. Go Back");
//            System.out.print("Enter choice (1-2): ");
//            choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1 -> addChapter(scanner, textbookId);
//                case 2 -> displayAdminMenu();
//                default -> System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 2);
//    }
//
//    private void createFacultyAccount(Scanner scanner) {
//        System.out.println("Creating Faculty Account");
//        System.out.print("Enter First Name: ");
//        String firstName = scanner.next();
//        System.out.print("Enter Last Name: ");
//        String lastName = scanner.next();
//        System.out.print("Enter Email: ");
//        String email = scanner.next();
//        System.out.print("Enter Password: ");
//        String password = scanner.next();
//
//        User newUser = new User();
//        newUser.setFirstName(firstName);
//        newUser.setLastName(lastName);
//        newUser.setEmail(email);
//        newUser.setPassword(password);  // The password should be hashed in UserService
//        newUser.setRoleId(2);  // Assuming 2 is the role ID for Faculty
//
//        System.out.println("Please select an option");
//        System.out.print("1.Add User ");
//        System.out.print("2.Go Back");
//        int option = scanner.nextInt();
//        if(option == 1){
//            userService.createUser(newUser);
//            System.out.println("Faculty account created successfully.");
//        }
//    }
//
//    private void createEtextbook(Scanner scanner) {
//        System.out.println("Creating E-textbook");
//        System.out.print("Enter E-textbook Title: ");
//        String title = scanner.next();
//        System.out.print("Enter Unique E-textbook ID: ");
//        int textbookId = scanner.nextInt();
//        int choice;
//
//        do {
//            System.out.println("\n--- Chapter Menu ---");
//            System.out.println("1. Add New Chapter");
//            System.out.println("2. Go Back");
//            System.out.print("Enter choice (1-2): ");
//            choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1 -> {
//                    etextbookService.createEtextbook(textbookId, title);
//                    // Automatically proceed to add a chapter
////                    displayChapterMenu(textbookId);
//                    addChapter(scanner, textbookId);
//                }
//                case 2 -> displayAdminMenu();
//                default -> System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 2);
//    }
//
//    private void modifyEtextbook(Scanner scanner) {
//        System.out.print("Enter Unique E-textbook ID to Modify: ");
//        int textbookId = scanner.nextInt();
//
//        int choice;
//        do {
//            System.out.println("\n--- Modify E-textbook ---");
//            System.out.println("1. Add New Chapter");
//            System.out.println("2. Modify Existing Chapter");
//            System.out.println("3. Go Back");
//            System.out.print("Enter choice (1-3): ");
//            choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1 -> addChapter(scanner, textbookId);
//                case 2 -> modifyChapter(scanner, textbookId);
//                case 3 -> System.out.println("Returning to Admin Menu...");
//                default -> System.out.println("Invalid choice. Please try again.");
//            }
//        } while (choice != 3);
//    }
//
//    private void addChapter(Scanner scanner, int textbookId) {
//        System.out.print("Enter Chapter Number: ");
//        String chapterNumber = scanner.next();
//
//    // Validate chapter number to ensure it follows the "chapXX" format
//    if (!chapterNumber.matches("chap\\d{2}")) {
//        System.out.println("Invalid chapter number. Please enter a value in the format 'chapXX' (e.g., 'chap01', 'chap12').");
//        return;
//    }
//
//        System.out.print("Enter Chapter Title: ");
//        String title = scanner.next();
//
//        // Check if title is empty
//        if (title.trim().isEmpty()) {
//            System.out.println("Chapter title cannot be empty. Please try again.");
//            return;
//        }
//
//        // Proceed with adding the chapter
//        etextbookService.addChapter(chapterNumber, textbookId, title);
//    }
//
//
//    private void modifyChapter(Scanner scanner, int textbookId) {
//        System.out.print("Enter Chapter Number to Modify: ");
//        String chapterNumber = scanner.next();
//        System.out.print("Enter New Chapter Title: ");
//        String title = scanner.next();
//
////        etextbookService.modifyChapter(textbookId, chapterNumber, title);
//        System.out.println("Chapter modified successfully.");
//    }
//
//    private void createActiveCourse(Scanner scanner) {
//        System.out.println("Creating Active Course");
//        System.out.print("Enter Course ID: ");
//        int courseId = scanner.nextInt();
//        System.out.print("Enter Course Title: ");
//        String courseTitle = scanner.next();
//        System.out.print("Enter Faculty ID: ");
//        int facultyId = scanner.nextInt();
//        System.out.print("Enter Start Date (yyyy-mm-dd): ");
//        String startDate = scanner.next();
//        System.out.print("Enter End Date (yyyy-mm-dd): ");
//        String endDate = scanner.next();
//        System.out.print("Enter Course Capacity: ");
//        int capacity = scanner.nextInt();
//
////        courseService.createActiveCourse(courseId, courseTitle, facultyId, startDate, endDate, capacity);
//        System.out.println("Active course created successfully.");
//    }
//
//    private void createEvaluationCourse(Scanner scanner) {
//        System.out.println("Creating Evaluation Course");
//        System.out.print("Enter Course ID: ");
//        int courseId = scanner.nextInt();
//        System.out.print("Enter Course Title: ");
//        String courseTitle = scanner.next();
//        System.out.print("Enter Faculty ID: ");
//        int facultyId = scanner.nextInt();
//        System.out.print("Enter Start Date (yyyy-mm-dd): ");
//        String startDate = scanner.next();
//        System.out.print("Enter End Date (yyyy-mm-dd): ");
//        String endDate = scanner.next();
//
////        courseService.createEvaluationCourse(courseId, courseTitle, facultyId, startDate, endDate);
//        System.out.println("Evaluation course created successfully.");
//    }
//}
