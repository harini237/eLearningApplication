package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/<unity id>";
    private static final String USERNAME = "<unity id>";
    private static final String PASSWORD = "<db password>";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
