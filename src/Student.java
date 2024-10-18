import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student {
    Connection con;
    Scanner scanner = new Scanner(System.in);
    Helper helper;
    String user = "";
    String pwd = "";
    public Student(Connection conn) {
        this.con = conn;
        this.helper = new Helper(this.con);

        System.out.println("1. Enroll in a course\n2. Sign in\n3. Go back\nEnter your choice (1-3): ");
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                //redirect to enrollment page
                this.enrollPage();
                break;
            case 2:
                //redirect to sign in page
                this.signIn();
                break;
            case 3:
                //redirect to home page
                new Home(this.con);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }

    }

    //function for enrollment page
    private void enrollPage() {
        Map<String,String> studentDetails = this.getDetails();

        System.out.println("1. Enroll\n2. Go Back\nEnter your choice (1-2): ");
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                //TODO: enrollment process
                break;
            case 2:
                new Student(this.con);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function for sign in page
    private void signIn() {
        boolean valid = false;
        do {
            //get student credentials and validate
            String[] creds = helper.getCredentials();
            String user = creds[0];
            String pwd = creds[1];

            if(this.user.isEmpty() || this.pwd.isEmpty())
                valid = helper.validateCredentials(user, pwd);
            else
                valid = true;

            System.out.println("Welcome student!");
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
        //TODO: display contents and invoke subsequent methods
    }

    //function to get student details for enrollment
    private Map<String,String> getDetails() {
        Map<String,String> studentDetails = new HashMap<>();
        System.out.println("Enter your details:\n1. First Name: ");
        studentDetails.put("firstName",scanner.nextLine());
        System.out.println("2. Last Name: ");
        studentDetails.put("lastName",scanner.nextLine());
        System.out.println("3. Email: ");
        studentDetails.put("email",scanner.nextLine());
        System.out.println("4. Course Token: ");
        studentDetails.put("courseToken",scanner.nextLine());

        return studentDetails;
    }

    //TODO: view section, view block, view participation activity points

}
