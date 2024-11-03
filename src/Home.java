import java.sql.Connection;
import java.util.Scanner;

import Menu.AdminMenu;

public class Home {
    Connection con;
    public Home(Connection conn) {
        this.con = conn;
        //display options
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the e-learning experience!");
        System.out.println("Login options:\n1. Admin\n2. Faculty\n3. TA\n4. Student\n5. Exit");
        System.out.println("Enter your choice (1-5): ");
        int choice = scanner.nextInt();

        //redirect to chosen homepage
        switch(choice) {
            case 1:
                AdminMenu adminUser = new AdminMenu();
                adminUser.displayAdminMenu(); // Call the method to display the admin menu
                break;
                //redirect to admin
            case 2:
                //redirect to faculty
                break;
            case 3:
                //redirect to ta
                break;
            case 4:
                //redirect to student
                new Student(this.con);
                break;
            case 5:
                System.out.println("Have a nice day!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }
}
