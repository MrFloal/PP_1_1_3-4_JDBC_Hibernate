package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String HOST = "jdbc:mysql://localhost:3306/mydbtest" +
            "?verifyServerCertificate=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory = null;

    public Util() {

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) return sessionFactory;
        try {
            Properties prop = new Properties();
            prop.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            prop.put(Environment.URL, HOST);
            prop.put(Environment.USER, USERNAME);
            prop.put(Environment.PASS, PASSWORD);
            prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            prop.put(Environment.SHOW_SQL, "true");

            sessionFactory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (HibernateException e) {
            System.out.println("Не удалось установить соединение.");
            e.printStackTrace();
        }
        return sessionFactory;
    }



    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Не удалось установить соединение.");
            e.printStackTrace();
        }
        return connection;
    }
}