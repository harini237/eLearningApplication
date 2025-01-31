package Menu;

import java.util.Scanner;
//import Menu.FacultyMenu;
//import Menu.TAMenu;
//import Menu.StudentMenu;
import Service.UserService;
import Entity.User;
import Util.PasswordUtil;

public class Home {
    private final UserService userService;
    private  final FacultyReportMenu facultyReportMenu;

    public Home() {
        this.userService = new UserService();  // Initialize the user service for login validation
        this.facultyReportMenu = new FacultyReportMenu();
        displayHomeScreen();
    }

    private void displayHomeScreen() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the e-learning experience!");
        System.out.println("Login options:\n1. Admin\n2. Faculty\n3. TA\n4. Student \n5. FacultyReport(Queries)\n6. Exit");
        System.out.print("Enter your choice (1-5): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> loginUser(scanner, "admin");
            case 2 -> loginUser(scanner, "faculty");
            case 3 -> loginUser(scanner, "ta");
            case 4 -> {
                System.out.println("Would you like to 1. SignIn or 2. Create Account ?");
                //redirect as per option
                int option = scanner.nextInt();
                if (option == 2) {
                    System.out.println("Creating Student Account");
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
                    newUser.setRoleId(4);
                    try {
                        userService.createUser(newUser);
                        System.out.println("Student registered! Please Login Now");
                    }catch (Exception ignored){
                        System.out.println("Error While creating user account Error: " + ignored.getMessage());
                    }
                }
                loginUser(scanner, "student");

            }
            case 5 -> facultyReportMenu.displayMenu();
            case 6 -> {
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
     * @param role The role of the user (Admin, Faculty, Menu.TA, Menu.Student).
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
                case "admin" -> new AdminMenu().displayAdminMenu(user);
                case "faculty" -> new FacultyMenu().displayFacultyMenu(user);
                case "ta" -> new TA(user);
                case "student" -> new Student(user);
            }
        } else {
                System.out.println("Invalid credentials or role. Please try again.");
                displayHomeScreen();  // Retry by displaying the home screen
        }
    }
}

//import java.sql.Connection;
//import java.util.Scanner;
//
//import Menu.AdminMenu;
//
//public class Menu.Home {
//    Connection con;
//    public Menu.Home(Connection conn) {
//        this.con = conn;
//        //display options
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome to the e-learning experience!");
//        System.out.println("Login options:\n1. Admin\n2. Faculty\n3. Menu.TA\n4. Menu.Student\n5. Exit");
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
//                new Menu.Student(this.con);
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
