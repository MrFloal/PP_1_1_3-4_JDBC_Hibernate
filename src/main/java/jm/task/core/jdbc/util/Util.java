package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String HOST = "jdbc:mysql://localhost:3306/mydbtest" +
            "?verifyServerCertificate=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Util() {

    }
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Не удалось установить соединение.");
            e.printStackTrace();
        }
        return connection;
    }
}