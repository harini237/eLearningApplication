import java.sql.Connection;
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


}
