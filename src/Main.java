import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        //load oracle driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:localhost:8080";

            //establish jdbc connection
            Connection con;
            String user = "user";
            String pass = "pass";
            try {
                con = DriverManager.getConnection(url, user, pass);
                new Home(con);
                con.close();
            } catch (Exception e) {
                System.out.println("Could not establish jdbc connection.");
            }
        } catch (ClassNotFoundException c) {
            System.out.println("Could not find Oracle Driver class.");
        }
    }
}