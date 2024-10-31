import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        //load mariadb driver
        String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";

        //establish jdbc connection
        Connection con;
        String user = "";
        String pass = "";
        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Successfully connected!");
            new Home(con);
            con.close();
        } catch (Exception e) {
            System.out.println("Could not establish jdbc connection.");
        }
    }
}