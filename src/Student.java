import Entity.User;
import Service.CourseService;
import Service.EtextbookService;
import Util.Helper;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student {
    Scanner scanner = new Scanner(System.in);
    Helper helper;
    private final CourseService courseService = new CourseService();
    private final EtextbookService etextbookService = new EtextbookService();
    private final User user;
    public Student(User user) {
        this.user = user;
        this.helper = new Helper();

        System.out.println("Student Menu:\n1. Enroll in a course\n2. Sign In (Student Landing Page)\n3. Go back\nEnter your choice (1-3): ");
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                //redirect to enrollment page
                this.enrollPage();
                break;
            case 2:
                //redirect to student landing page
                this.landing();
                break;
            case 3:
                //redirect to home page
                new Home();
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

        System.out.println("Enrollment Menu:\n1. Enroll\n2. Go Back\nEnter your choice (1-2): ");
        int choice = scanner.nextInt();
        switch(choice) {
            case 1:
                this.courseService.enrollStudent(studentDetails, this.user);
                new Student(this.user);
                break;
            case 2:
                new Student(this.user);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function for landing page
    private void landing() {
        this.etextbookService.listAllEtextbooks();
        System.out.println("Landing Menu:\n1. View a section\n2. View participation activity points\n3. Logout");
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
                new Home();
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
        System.out.println("Enter your enrollment details:\n1. First Name: ");
        studentDetails.put("firstName",scanner.next());
        System.out.println("2. Last Name: ");
        studentDetails.put("lastName",scanner.next());
        System.out.println("3. Email: ");
        studentDetails.put("email",scanner.next());
        System.out.println("4. Course Token: ");
        studentDetails.put("courseToken",scanner.next());

        return studentDetails;
    }

    //function to view section
    private void viewSection() {
        System.out.println("Enter chapter ID: ");
        String chapterId = scanner.next();
        System.out.println("Enter section ID: ");
        String sectionId = scanner.next();

        System.out.println("Section Menu\n1. View block\n2. Go back");
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
        System.out.println("Block Menu:\n1. Next\n2. Go back");
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
        String answerId = scanner.next();

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
        System.out.println("Score Menu:\n1. Go back");
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
