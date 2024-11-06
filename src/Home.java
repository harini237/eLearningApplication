import java.sql.Connection;
import java.util.Scanner;
import Menu.AdminMenu;
//import Menu.FacultyMenu;
//import Menu.TAMenu;
//import Menu.StudentMenu;
import Service.UserService;
import Entity.User;
import Util.PasswordUtil;

public class Home {
    private final UserService userService;

    public Home() {
        this.userService = new UserService();  // Initialize the user service for login validation
        displayHomeScreen();
    }

    private void displayHomeScreen() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the e-learning experience!");
        System.out.println("Login options:\n1. Admin\n2. Faculty\n3. TA\n4. Student\n5. Exit");
        System.out.print("Enter your choice (1-5): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> loginUser(scanner, "Admin");
            case 2 -> loginUser(scanner, "Faculty");
            case 3 -> loginUser(scanner, "TA");
            case 4 -> loginUser(scanner, "Student");
            case 5 -> {
                System.out.println("Have a nice day!");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
            }
        }
    }

    /**
     * Handles the login process for each user type.
     *
     * @param scanner The Scanner object for input.
     * @param role The role of the user (Admin, Faculty, TA, Student).
     */
    private void loginUser(Scanner scanner, String role) {
        scanner.nextLine();  // Consume newline left by nextInt
        System.out.print("Enter username (userId): ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Use UserService to validate login with the hashed password
        User user = userService.getUserById(username);

        if (user != null && user.getRole().equalsIgnoreCase(role) && user.getPassword().equals(PasswordUtil.hashPassword(password))) {
            System.out.println("Login successful!");

            if(user.getIsPwdResetReq()){
                String oldPassword = password;
                System.out.println("Your Password needs to be reset.");
                while(true) {
                    System.out.println("Please Enter your New Password!");
                    System.out.println("Enter password: ");
                    password = scanner.nextLine();
                    System.out.println("Repeat password: ");
                    String repeatPassword = scanner.nextLine();
                    if(!password.equals(repeatPassword)){
                        System.out.println("Passwords doesn't match");
                    } else{
                        userService.resetPassword(user.getId(), oldPassword, password);
                        break;
                    }
                }
            }

            // Redirect to the appropriate menu based on role
            switch (role) {
                case "Admin" -> new AdminMenu().displayAdminMenu(user);
//                case "Faculty" -> new FacultyMenu(con).displayFacultyMenu();
//                case "TA" -> new TAMenu(con).displayTAMenu();
                case "Student" -> new Student(user);
            }
        } else {
            if (role.equals("Student")) {
                System.out.println("Would you like to register and enroll? (Y/N) : ");
                //redirect as per option
            } else {
                System.out.println("Invalid credentials or role. Please try again.");
                displayHomeScreen();  // Retry by displaying the home screen
            }
        }
    }
}

//import java.sql.Connection;
//import java.util.Scanner;
//
//import Menu.AdminMenu;
//
//public class Home {
//    Connection con;
//    public Home(Connection conn) {
//        this.con = conn;
//        //display options
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome to the e-learning experience!");
//        System.out.println("Login options:\n1. Admin\n2. Faculty\n3. TA\n4. Student\n5. Exit");
//        System.out.println("Enter your choice (1-5): ");
//        int choice = scanner.nextInt();
//
//        //redirect to chosen homepage
//        switch(choice) {
//            case 1:
//                AdminMenu adminUser = new AdminMenu();
//                adminUser.displayAdminMenu(); // Call the method to display the admin menu
//                break;
//                //redirect to admin
//            case 2:
//                //redirect to faculty
//                break;
//            case 3:
//                //redirect to ta
//                break;
//            case 4:
//                //redirect to student
//                new Student(this.con);
//                break;
//            case 5:
//                System.out.println("Have a nice day!");
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Invalid entry, exiting application.");
//                System.exit(0);
//                break;
//        }
//    }
//}
