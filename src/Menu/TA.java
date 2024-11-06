package Menu;

import Entity.User;
import Service.CourseService;
import Service.EtextbookService;
import Service.UserService;
import Util.Helper;

import java.util.Map;
import java.util.Scanner;

public class TA {
    Scanner scanner = new Scanner(System.in);
    Helper helper;
    User user;
    private final UserService userService = new UserService();
    private final CourseService courseService = new CourseService();
    private final EtextbookService etextbookService = new EtextbookService();

    public TA(User user) {
        this.helper = new Helper();
        this.user = user;

        System.out.println("Welcome Menu.TA!");
        this.landing();
    }

    //function for landing page
    private void landing() {
        System.out.println("TA Landing Menu:\n1. Go to active courses\n2. View courses\n3. Change password\n4. Logout");
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

        System.out.println("Active Courses Menu:\n1. View students\n2. Add new chapter\n3. Modify chapter\n4. Go back");
        System.out.println("Active Courses Menu:\n1. View students\n2. Add new chapter\n3. Modify chapter\n4. Go back");
        System.out.println("Enter your choice (1-4): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to view students

                if (this.courseService.checkTaByCourseId(this.user.getId(), courseID)) {
                    this.viewStudents(courseID);
                } else {
                    System.out.println("Access restricted. You can only view students in your course.");
                    this.landing();
                }
                break;
            case 2:
                //redirect to add chapter
                if (this.courseService.checkTaByCourseId(this.user.getId(), courseID)) {
                    this.addChapter(courseID);
                } else {
                    System.out.println("Access restricted. You can only add chapters in your course.");
                    this.landing();
                }
                if (this.courseService.checkTaByCourseId(this.user.getId(), courseID)) {
                    this.addChapter(courseID);
                } else {
                    System.out.println("Access restricted. You can only add chapters in your course.");
                    this.landing();
                }
                break;
            case 3:
                //redirect to modify chapter
                if (this.courseService.checkTaByCourseId(this.user.getId(), courseID)) {
                    this.modifyChapter(courseID);
                } else {
                    System.out.println("Access restricted. You can only modify chapters in your course.");
                    this.landing();
                }
                if (this.courseService.checkTaByCourseId(this.user.getId(), courseID)) {
                    this.modifyChapter(courseID);
                } else {
                    System.out.println("Access restricted. You can only modify chapters in your course.");
                    this.landing();
                }
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
        this.courseService.viewAssignedCoursesByTa(this.user.getId());
        this.courseService.viewAssignedCoursesByTa(this.user.getId());
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
        System.out.println("Reset Password Questions:\nEnter current password: ");
        System.out.println("Reset Password Questions:\nEnter current password: ");
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
                    this.userService.resetPassword(this.user.getId(), currentPwd, newPwd);
                    this.landing();
                    this.userService.resetPassword(this.user.getId(), currentPwd, newPwd);
                    this.landing();
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
        System.out.println("Students in course "+ courseID + ":");
        this.courseService.viewStudentsByCourse(courseID);
        System.out.println("Students in course "+ courseID + ":");
        this.courseService.viewStudentsByCourse(courseID);
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
        System.out.println("Add Chapter:\nEnter unique chapter ID: ");
        String chapterID = scanner.next();
        // Validate chapter number to ensure it follows the "chapXX" format
        if (!chapterID.matches("chap\\d{2}")) {
            System.out.println("Invalid chapter number. Please enter a value in the format.");
            this.goToActiveCourses();
        }
        System.out.println("Enter chapter title: ");
        String title = scanner.next();
        // Check if title is empty
        if (title.trim().isEmpty()) {
            System.out.println("Chapter title cannot be empty. Please try again.");
            this.goToActiveCourses();
        }

        Integer textbookId = this.courseService.getTextbookByCourse(courseID);

        this.etextbookService.addChapter(chapterID, textbookId, title);

        System.out.println("1. Add new section\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add section
                System.out.println("Success.");
                this.addSection("addChapter", courseID, chapterID, textbookId);
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
        System.out.println("Modify chapter:\nEnter unique chapter ID: ");
        String chapterID = scanner.next();

        Integer textbookId = this.courseService.getTextbookByCourse(courseID);

        System.out.println("1. Add new section\n2. Modify Section\n3. Go back");
        System.out.println("Enter your choice (1-3): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add section
                System.out.println("Success.");
                this.addSection("modifyChapter", courseID, chapterID, textbookId);
                break;
            case 2:
                //redirect to modify section
                this.modifySection("modifyChapter", courseID, chapterID, textbookId);
            case 3:
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
    private void addSection(String callingFunction, String courseID, String chapterID, Integer textbookId) {
        System.out.println("Add section:\nEnter section number: ");
        String sectionNumber = scanner.next();
        if (!sectionNumber.trim().matches("(?i)Sec\\d{2}")) { // '(?i)' makes the regex case-insensitive
            System.out.println("Invalid section number. Please enter a value in the format.");
            if(callingFunction.equals("addChapter")) {
                this.addChapter(courseID);
            } else if(callingFunction.equals("modifyChapter")) {
                this.modifyChapter(courseID);
            } else {
                this.landing();
            }
        }

        System.out.println("Enter section title: ");
        String title = scanner.next();
        // Check if title is empty
        if (title.trim().isEmpty()) {
            System.out.println("Section title cannot be empty. Please try again.");
            if(callingFunction.equals("addChapter")) {
                this.addChapter(courseID);
            } else if(callingFunction.equals("modifyChapter")) {
                this.modifyChapter(courseID);
            } else {
                this.landing();
            }
        }

        this.etextbookService.addSection(textbookId, chapterID, sectionNumber, title);

        System.out.println("1. Add new content block\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add content block
                this.addContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
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
    private void addContentBlock(String callingFunction, String courseID, String chapterID, Integer textbookId, String sectionNumber) {
        System.out.println("Add content block:\nEnter content block ID: ");
        String contentId = scanner.next();

        System.out.println("1. Add text\n2. Add picture\n3. Add activity\n4. Hide activity\n5. Go back");
        System.out.println("Enter your choice (1-5): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add text
                String text = helper.getText();
                if(!text.isEmpty()) {
                    etextbookService.addContentBlock(contentId, sectionNumber, chapterID, textbookId, text, "text", this.user.getId(), this.user.getId());
                }
                this.addContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
                break;
            case 2:
                //redirect to add picture
                String picture = helper.getPicture();
                if(!picture.isEmpty()) {
                    etextbookService.addContentBlock(contentId, sectionNumber, chapterID, textbookId, picture,  "picture", this.user.getId(), this.user.getId());
                }
                this.addContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
                break;
            case 3:
                //redirect to add activity
                this.addActivity(callingFunction, courseID, chapterID, textbookId, sectionNumber, contentId);
                break;
            case 4:
                //redirect to hide activity
                this.hideActivity(callingFunction, courseID, chapterID, textbookId, sectionNumber, contentId);
                break;
            case 5:
                //redirect to previous menu (add section menu)
                if(callingFunction.equals("addChapter")) {
                    this.addSection(callingFunction, courseID, chapterID, textbookId);
                } else if(callingFunction.equals("modifyChapter")) {
                    this.modifySection(callingFunction, courseID, chapterID, textbookId);
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
    private void modifySection(String callingFunction, String courseID, String chapterID, Integer textbookId) {
        System.out.println("Modify section:\nEnter section number: ");
        String sectionNumber = scanner.next();
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
                this.addContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
                break;
            case 2:
                //redirect to modify content block
                this.modifyContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
                break;
            case 3:
                //redirect to delete content block
                this.deleteContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
                break;
            case 4:
                //redirect to hide content block
                this.hideContentBlock(callingFunction,  courseID, chapterID, textbookId, sectionNumber);
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
    private void modifyContentBlock(String callingFunction, String courseID, String chapterID, Integer textbookId, String sectionNumber) {
        System.out.println("Modify content block:\nEnter content block ID: ");
        String contentId = scanner.next();

        System.out.println("1. Add text\n2. Add picture\n3. Add activity\n4. Hide activity\n5. Go back\n6. Landing Page");
        System.out.println("Enter your choice (1-6): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //redirect to add text
                String text = helper.getText();
                if(!text.isEmpty()) {
                    etextbookService.addContentBlock(contentId, sectionNumber, chapterID, textbookId, text, "text", this.user.getId(), this.user.getId());
                } else {
                    this.addContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
                }
                break;
            case 2:
                //redirect to add picture
                String picture = helper.getPicture();
                if(!picture.isEmpty()) {
                    etextbookService.addContentBlock(contentId, sectionNumber, chapterID, textbookId, picture, "picture",  this.user.getId(), this.user.getId());
                } else {
                    this.addContentBlock(callingFunction, courseID, chapterID, textbookId, sectionNumber);
                }
                break;
            case 3:
                //redirect to add activity
                this.addActivity(callingFunction, courseID, chapterID, textbookId, sectionNumber, contentId);
                break;
            case 4:
                //redirect to hide activity
                this.hideActivity(callingFunction, courseID, chapterID, textbookId, sectionNumber, contentId);
                break;
            case 5:
                //redirect to previous menu (modify section)
                this.modifySection(callingFunction, courseID, chapterID, textbookId);
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
    private void deleteContentBlock(String callingFunction, String courseID, String chapterID, Integer textbookId, String sectionNumber) {
        System.out.println("Delete content block:\nEnter content block ID: ");
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
                this.modifySection(callingFunction, courseID, chapterID, textbookId);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    //function to hide content block
    private void hideContentBlock(String callingFunction, String courseID, String chapterID, Integer textbookId, String sectionNumber) {
        System.out.println("Enter content block ID: ");
        String contentId = scanner.next();

        System.out.println("1. Hide\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                etextbookService.hideContentBlock(contentId, sectionNumber, chapterID, textbookId);
                break;
            case 2:
                //redirect to previous page (modify section)
                this.modifySection(callingFunction, courseID, chapterID, textbookId);
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }

    private void addActivity(String callingFunction, String courseID, String chapterID, Integer textbookId, String sectionNumber, String contentId) {
        Map<String, String> activity = helper.getActivity();
        if(!activity.isEmpty()) {
            //TODO: handle adding activity
            System.out.println("Added activity");
        }
        this.goToActiveCourses();
    }

    private void hideActivity(String callingFunction, String courseID, String chapterID, Integer textbookId, String sectionNumber, String contentID) {
        System.out.println("1. Save\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                //TODO: handle hide activity
                break;
            case 2:
                this.goToActiveCourses();
                break;
            default:
                System.out.println("Invalid entry, exiting application.");
                System.exit(0);
                break;
        }
    }
}
