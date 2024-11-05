import Util.Helper;

import java.sql.Connection;
import java.util.Map;
import java.util.Scanner;

public class TA {
    Scanner scanner = new Scanner(System.in);
    Helper helper;
    String user = "";
    String pwd = "";

    public TA() {
        this.helper = new Helper();

        System.out.println("Welcome TA!");
        this.landing();
    }

    //function for landing page
    private void landing() {
        System.out.println("1. Go to active courses\n2. View courses\n3. Change password\n4. Logout");
        System.out.println("Enter your choice (1-4): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to active courses page
                this.goToActiveCourses();
                break;
            case 2:
                //redirect to view courses page
                this.viewCourses();
                break;
            case 3:
                //redirect to change pwd page
                this.changePassword();
                break;
            case 4:
                //redirect to home page
                new Home();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to go to active courses
    private void goToActiveCourses() {
        System.out.println("Enter course ID: ");
        String courseID = scanner.next();

        System.out.println("1. View students\n2. Add new chapter\n3. Modify chapters\n4. Go back");
        System.out.println("Enter your choice (1-4): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to view students
                this.viewStudents(courseID);
                break;
            case 2:
                //redirect to add chapter
                this.addChapter(courseID);
                break;
            case 3:
                //redirect to modify chapter
                this.modifyChapter(courseID);
                break;
            case 4:
                //redirect to landing page
                this.landing();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to view courses
    public void viewCourses() {
        //TODO: fetch and display courses
        System.out.println("1. Go Back");
        System.out.println("Enter your choice (1): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            this.landing();
        } else {
            System.out.println("Invalid entry, exiting application.");
            System.exit(0);
        }
    }

    //function to change password
    public void changePassword() {
        System.out.println("Enter current password: ");
        String currentPwd = scanner.next();
        System.out.println("Enter new password: ");
        String newPwd = scanner.next();
        System.out.println("Confirm new password: ");
        String confirmPwd = scanner.next();

        System.out.println("1. Update\n2. Go Back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                if(newPwd.equals(confirmPwd)) {
                    //TODO: check if current pwd is valid
                    //TODO: handle pwd update
                } else {
                    System.out.println("Passwords don't match, try again.");
                    this.landing();
                }
                break;
            case 2:
                //redirect to landing page
                this.landing();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to view students
    private void viewStudents(String courseID) {
        //TODO: fetch and display students
        System.out.println("1. Go Back");
        System.out.println("Enter your choice (1): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            this.landing();
        } else {
            System.out.println("Invalid entry, exiting application.");
            System.exit(0);
        }
    }

    //function to add chapter
    private void addChapter(String courseID) {
        System.out.println("Enter unique chapter ID: ");
        String chapterID = scanner.next();
        System.out.println("Enter chapter title: ");
        String chapterTitle = scanner.next();

        System.out.println("1. Add new section\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add section
                System.out.println("Success.");
                this.addSection("addChapter", courseID, chapterID, chapterTitle);
                break;
            case 2:
                //redirect to previous page
                this.goToActiveCourses();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to modify chapter
    private void modifyChapter(String courseID) {
        System.out.println("Enter unique chapter ID: ");
        String chapterID = scanner.next();

        System.out.println("1. Add new section\n2. Add new activity\n3. Modify Section\n4. Go back");
        System.out.println("Enter your choice (1-4): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add section
                System.out.println("Success.");
                this.addSection("modifyChapter", courseID, chapterID, "");
                break;
            case 2:
                //redirect to add activity
                this.addActivity("modifyChapter", courseID, chapterID, "");
                break;
            case 3:
                //redirect to modify section
                this.modifySection("modifyChapter", courseID, chapterID, "");
            case 4:
                //redirect to previous page
                this.goToActiveCourses();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to add section
    private void addSection(String callingFunction, String courseID, String chapterID, String chapterTitle) {
        System.out.println("Enter section number: ");
        String sectionNum = scanner.next();
        System.out.println("Enter section title: ");
        String sectionTitle = scanner.next();

        System.out.println("1. Add new content block\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add content block
                this.addContentBlock("addSection", courseID, chapterID, chapterTitle);
                break;
            case 2:
                //redirect to previous page
                if(callingFunction.equals("addChapter")) {
                    this.addChapter(courseID);
                } else if(callingFunction.equals("modifyChapter")) {
                    this.modifyChapter(courseID);
                } else {
                    this.landing();
                }
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to add content block
    private void addContentBlock(String callingFunction, String courseID, String chapterID, String chapterTitle) {
        System.out.println("Enter content block ID: ");
        String contentId = scanner.next();

        System.out.println("1. Add text\n2. Add picture\n3. Add activity\n4. Hide activity\n5. Go back");
        System.out.println("Enter your choice (1-5): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add text
                String text = helper.getText();
                if(!text.isEmpty()) {
                    //TODO: handle adding text
                } else {
                    this.addContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                }
                break;
            case 2:
                //redirect to add picture
                String picture = helper.getPicture();
                if(!picture.isEmpty()) {
                    //TODO: handle adding picture
                } else {
                    this.addContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                }
                break;
            case 3:
                //redirect to add activity
                this.addActivity(callingFunction, courseID, chapterID, chapterTitle);
                break;
            case 4:
                //redirect to hide activity
                this.hideActivity(callingFunction, courseID, chapterID, chapterTitle, contentId);
                break;
            case 5:
                //redirect to previous menu (add section menu)
                if(callingFunction.equals("addSection")) {
                    this.addSection(callingFunction, courseID, chapterID, chapterTitle);
                } else if(callingFunction.equals("modifyChapter")) {
                    this.modifySection(callingFunction, courseID, chapterID, chapterTitle);
                } else {
                    this.landing();
                }
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to modify section
    private void modifySection(String callingFunction, String courseID, String chapterID, String chapterTitle) {
        System.out.println("Enter section number: ");
        String sectionNum = scanner.next();
        System.out.println("Enter section title: ");
        String sectionTitle = scanner.next();
        System.out.println("Enter chapter ID: ");
        String chapterId = scanner.next();
        System.out.println("Enter book: ");
        String bookId = scanner.next();

        System.out.println("1. Add new content block\n2. Modify content block\n3. Delete content block\n4. Hide content block\n5. Go back");
        System.out.println("Enter your choice (1-5): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add content block
                this.addContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                break;
            case 2:
                //redirect to modify content block
                this.modifyContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                break;
            case 3:
                //redirect to delete content block
                this.deleteContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                break;
            case 4:
                //redirect to hide content block
                this.hideContentBlock(callingFunction,  courseID, chapterID, chapterTitle);
                break;
            case 5:
                //redirect to previous page (modify chapter)
                this.modifyChapter(courseID);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to modify content block
    private void modifyContentBlock(String callingFunction, String courseID, String chapterID, String chapterTitle) {
        System.out.println("Enter content block ID: ");
        String contentId = scanner.next();

        System.out.println("1. Add text\n2. Add picture\n3. Add activity\n4. Hide activity\n5. Go back\n6. Landing Page");
        System.out.println("Enter your choice (1-6): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add text
                String text = helper.getText();
                if(!text.isEmpty()) {
                    //TODO: handle adding text
                } else {
                    this.addContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                }
                break;
            case 2:
                //redirect to add picture
                String picture = helper.getPicture();
                if(!picture.isEmpty()) {
                    //TODO: handle adding picture
                } else {
                    this.addContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                }
                break;
            case 3:
                //redirect to add activity
                this.addActivity(callingFunction, courseID, chapterID, chapterTitle);
                break;
            case 4:
                //redirect to hide activity
                this.hideActivity(callingFunction, courseID, chapterID, chapterTitle, contentId);
                break;
            case 5:
                //redirect to previous menu
                if (callingFunction.equals("addSection")) {
                    this.addSection(callingFunction, courseID, chapterID, chapterTitle);
                } else if (callingFunction.equals("modifyChapter")) {
                    this.modifySection(callingFunction, courseID, chapterID, chapterTitle);
                }
                break;
            case 6:
                //redirect to landing page
                this.landing();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to delete content block
    private void deleteContentBlock(String callingFunction, String courseID, String chapterID, String chapterTitle) {
        System.out.println("Enter content block ID: ");
        String contentId = scanner.next();

        System.out.println("1. Delete\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //TODO: handle deletion
                break;
            case 2:
                //redirect to previous page (modify section)
                this.modifySection(callingFunction, courseID, chapterID, chapterTitle);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to hide content block
    private void hideContentBlock(String callingFunction, String courseID, String chapterID, String chapterTitle) {
        System.out.println("Enter content block ID: ");
        String contentId = scanner.next();

        System.out.println("1. Hide\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //TODO: handle hiding
                break;
            case 2:
                //redirect to previous page (modify section)
                this.modifySection(callingFunction, courseID, chapterID, chapterTitle);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    private void addActivity(String callingFunction, String courseID, String chapterID, String chapterTitle) {
        Map<String, String> activity = helper.getActivity();
        if(activity.isEmpty()) {
            this.addSection(callingFunction, courseID, chapterID, chapterTitle);
        } else {
            //TODO: handle adding activity
        }
    }

    private void hideActivity(String callingFunction, String courseID, String chapterID, String chapterTitle, String contentID) {
        System.out.println("1. Save\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //TODO: handle hide content
                break;
            case 2:
                if (callingFunction.equals("modifyChapter")) {
                    this.modifyContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                } else if (callingFunction.equals("addSection")) {
                    this.addContentBlock(callingFunction, courseID, chapterID, chapterTitle);
                } else {
                    this.landing();
                }
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }
}
