package Menu;

import Repository.FacultyReportRepository;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Stack;

public class FacultyReportMenu {

    private final FacultyReportRepository reportRepository = new FacultyReportRepository();
    private final Stack<Runnable> navigationStack = new Stack<>();

    public FacultyReportMenu(){
    }

    public void displayMenu() {
        goToMainMenu();

        while (!navigationStack.isEmpty()) {
            navigationStack.peek().run();
        }
    }

    private void goToMainMenu() {
        navigationStack.clear();
        navigationStack.push(this::mainMenu);
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Faculty Report Menu ---");
        System.out.println("1. Count Sections of the First Chapter of a Textbook");
        System.out.println("2. Print Faculty and TAs of All Courses with Roles");
        System.out.println("3. Active Courses with Faculty and Total Number of Students");
        System.out.println("4. Course with the Largest Waiting List");
        System.out.println("5. Print Contents of Chapter 02 of Textbook 101");
        System.out.println("6. Incorrect Answers for Q2 of Activity0");
        System.out.println("7. Books in Active and Evaluation Status by Different Instructors");
        System.out.println("8. Go Back");
        System.out.print("Enter choice (1-8): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> reportRepository.countSectionsInFirstChapter();
            case 2 -> reportRepository.printFacultyAndTAsWithRoles();
            case 3 -> reportRepository.printActiveCoursesWithStudentCount();
            case 4 -> reportRepository.printCourseWithLargestWaitingList();
            case 5 -> reportRepository.printChapter02Contents();
            case 6 -> reportRepository.printIncorrectAnswersForQ2();
            case 7 -> reportRepository.printBooksInActiveAndEvalStatus();
            case 8 -> {
                navigationStack.clear();
                System.out.println("Going back...");
            }
            default -> System.out.println("Invalid choice. Returning to Faculty Report Menu.");
        }
    }
}
