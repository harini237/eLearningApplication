import Util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Connection con = DatabaseConnection.getConnection();
            System.out.println("Successfully connected!");
            new Home(con);
            con.close();
        } catch (Exception e) {
            System.out.println("Could not establish jdbc connection.");
        }
    }
}