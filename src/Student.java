import Util.Helper;

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
        Map<String,String> studentDetails = this.getEnrollDetails();

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
        //TODO: fetch and display textbooks contents
        System.out.println("1. View a section\n2. View participation activity points\n3. Logout");
        System.out.println("Enter your choice (1-3): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to view section
                this.viewSection();
                break;
            case 2:
                //redirect to view points
                this.viewParticipationActivityPoints();
                break;
            case 3:
                new Home(this.con);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to get student details for enrollment
    private Map<String,String> getEnrollDetails() {
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

    //TODO: view block, view participation activity points
    //function to view section
    private void viewSection() {
        System.out.println("Enter chapter ID: ");
        String chapterId = scanner.nextLine();
        System.out.println("Enter section ID: ");
        String sectionId = scanner.nextLine();

        System.out.println("1. View block\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to view block
                this.viewBlock(chapterId, sectionId);
                break;
            case 2:
                //redirect to previous page (landing)
                this.landing();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to view block
    private void viewBlock(String chapterId, String sectionId) {
        //TODO: fetch block details from db
        String menuOption = "";
        /*
        IF BLOCK IS CONTENT
            this.contentBlock();
        IF BLOCK IS ACTIVTY
            this.activityBlock();
        */
    }

    //function for content block
    private void contentBlock() {
        //TODO: display contents
        System.out.println("1. Next\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //TODO: go to next block or landing page
                break;
            case 2:
                //redirect to previous page (view section)
                this.viewSection();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function for activity block
    private void activityBlock() {
        //TODO: display activity
        System.out.println("Enter answer ID: ");
        String answerId = scanner.nextLine();

        System.out.println("1. Submit\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //TODO: validate and update points
                //TODO: go to next block or landing
                break;
            case 2:
                //redirect to previous page (view section)
                this.viewSection();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to show points
    private void viewParticipationActivityPoints() {
        //TODO: fetch and display points of user
        System.out.println("1. Go back");
        System.out.println("Enter your choice (1): ");
        int choice = scanner.nextInt();

        if(choice == 1){
            //redirect to landing
            this.landing();
        } else {
            System.out.println("Invalid entry, exiting application.");
            System.exit(0);
        }
    }
}
