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
    private Connection connection;

    public Util() {
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Не удалось установить соединение." + e);
        }
    }
    public Connection getConnection() {
        return connection;
    }
}