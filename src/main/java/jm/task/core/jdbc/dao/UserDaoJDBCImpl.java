package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                        CREATE TABLE user (
                          `ID` INT NOT NULL AUTO_INCREMENT,
                          `NAME` VARCHAR(45) NULL,
                          `LASTNAME` VARCHAR(45) NULL,
                          `AGE` TINYINT(3) NULL,
                          PRIMARY KEY (`ID`),
                          UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE);
                    """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу. Возможно, она уже существует.");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить удаление таблицы. Возможно, она уже удалена.");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO user (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        Connection copy = null;
        try (Connection connection = new Util().getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql)) {
            copy = connection;
            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setByte(3, age);

            pStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Не удалось добавить User в таблицу. ");
            e.printStackTrace();
            copy.rollback();
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection copy = null;
        try (Connection connection = new Util().getConnection()) {
            copy = connection;
            String sql = "DELETE FROM user WHERE ID = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setLong(1, id);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу. ");
            e.printStackTrace();
            copy.rollback();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery("SELECT * FROM user");
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("ID"));
                user.setName(res.getString("NAME"));
                user.setLastName(res.getString("LASTNAME"));
                user.setAge(res.getByte("AGE"));

                result.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Не удалось вернуть список User.");
            e.printStackTrace();
        }

        return result;
    }

    public void cleanUsersTable() throws SQLException {
        Connection copy = null;
        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {
            copy = connection;
            statement.executeUpdate("DELETE FROM user");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу.");
            e.printStackTrace();
            copy.rollback();
        }
    }
}
