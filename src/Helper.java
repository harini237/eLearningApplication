import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Helper {
    Connection con;
    Scanner scanner = new Scanner(System.in);

    public Helper(Connection conn) {
        this.con = conn;
    }

    public boolean validateCredentials(String user, String pass) {
        //TODO: vaidate the creds in the db and return accordingly
        return true;
    }

    public String[] getCredentials() {
        System.out.println("Enter your credentials:");
        System.out.println("User ID: ");
        String userID = scanner.nextLine();
        System.out.println("Password: ");
        String pwd = scanner.nextLine();

        String[] creds = new String[2];
        creds[0] = userID;
        creds[1] = pwd;

        return creds;
    }

    //function to get text
    public String getText() {
        String text = "";

        System.out.println("Enter text: ");
        text = scanner.nextLine();

        System.out.println("1. Add\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        if(choice == 1) {
            return text;
        } else {
            return "";
        }
    }

    //function to get picture
    public String getPicture() {
        String picture = "";

        System.out.println("Enter picture filename: ");
        picture = scanner.nextLine();

        System.out.println("1. Add\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        if(choice == 1) {
            return picture;
        } else {
            return "";
        }
    }

    //function to add activity
    public Map<String, String> getActivity() {
        System.out.println("Enter unique activity ID: ");
        String activityId = scanner.nextLine();

        System.out.println("1. Add question\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        if(choice == 1) {
            Map<String, String> question = this.getQuestion();
            if (question.isEmpty()) {
                return new HashMap<>();
            }
            question.put("activityId", activityId);
            return question;
        } else {
            return new HashMap<>();
        }
    }

    public Map<String, String> getQuestion() {
        Map<String, String> question = new HashMap<>();

        System.out.println("Enter question ID: ");
        question.put("questionId",scanner.nextLine());

        System.out.println("Enter question text: ");
        question.put("questionText",scanner.nextLine());

        System.out.println("Enter option 1: ");
        question.put("option1",scanner.nextLine());

        System.out.println("Enter option 1 explanation: ");
        question.put("option1_explanation",scanner.nextLine());

        System.out.println("Enter option 2: ");
        question.put("option2",scanner.nextLine());

        System.out.println("Enter option 2 explanation: ");
        question.put("option2_explanation",scanner.nextLine());

        System.out.println("Enter option 3: ");
        question.put("option3",scanner.nextLine());

        System.out.println("Enter option 3 explanation: ");
        question.put("option3_explanation",scanner.nextLine());

        System.out.println("Enter option 4: ");
        question.put("option4",scanner.nextLine());

        System.out.println("Enter option 4 explanation: ");
        question.put("option4_explanation",scanner.nextLine());

        System.out.println("Enter answer: ");
        question.put("answer",scanner.nextLine());

        System.out.println("1. Save\n2. Go back");
        System.out.println("Enter your choice (1-2): ");
        int choice = scanner.nextInt();

        if(choice == 1) {
            return question;
        } else {
            return new HashMap<>();
        }
    }
}
