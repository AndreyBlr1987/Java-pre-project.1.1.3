package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String dbURL = "jdbc:mysql://localhost:3306/mydbtest";
    private static   String dbUSERNAME = "root";
    private static String dbPASSWORD = "1234567";


    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL, dbUSERNAME, dbPASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ОШИБКА");
        }
        return connection;
    }
}
