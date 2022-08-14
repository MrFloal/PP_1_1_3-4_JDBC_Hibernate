package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("name1", "lastname1", (byte) 10);
        userService.saveUser("name2", "lastname2", (byte) 20);
        userService.saveUser("name3", "lastname3", (byte) 30);
        userService.saveUser("name4", "lastname4", (byte) 40);

        List<User> list = userService.getAllUsers();
        list.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
