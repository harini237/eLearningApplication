import java.sql.Connection;
import java.util.Scanner;

public class TA {
    Connection con;
    Scanner scanner = new Scanner(System.in);
    Helper helper;
    String user = "";
    String pwd = "";

    public TA(Connection conn) {
        this.con = conn;
        this.helper = new Helper(this.con);

        boolean valid = false;

        do {
            //get ta credentials and validate
            String[] creds = helper.getCredentials();
            String user = creds[0];
            String pwd = creds[1];

            if(this.user.isEmpty() || this.pwd.isEmpty())
                valid = helper.validateCredentials(user, pwd);
            else
                valid = true;

            System.out.println("Welcome TA!");
            System.out.println("1. Sign In\n2. Go Back\nEnter your choice (1-2): ");
            int choice = scanner.nextInt();

            switch(choice) {
                case 1:
                    if(valid) {
                        System.out.println("Sign in successful.");
                        this.user = user;
                        this.pwd = pwd;
                        this.landing();
                    } else {
                        System.out.println("Login incorrect, try again.");
                    }
                    break;
                case 2:
                    new Home(this.con);
                    break;
                default:
                    System.out.println("Invalid entry, exiting application.");
                    System.exit(0);
                    break;
            }
        } while(!valid);
    }

    //function for landing page
    private void landing() {
        System.out.println("1. Go to active courses\n2. View courses\n3. Change password\n4. Logout");
        System.out.println("Enter your choice (1-4): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to active courses page
                break;
            case 2:
                //redirect to view courses page
                break;
            case 3:
                //redirect to change pwd page
                break;
            case 4:
                //redirect to home page
                new Home(this.con);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to view active courses
    private void viewActiveCourses() {

    }
}
