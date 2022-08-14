package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            String sql = """
                        CREATE TABLE IF NOT EXISTS user (
                          `ID` INT NOT NULL AUTO_INCREMENT,
                          `NAME` VARCHAR(45) NULL,
                          `LASTNAME` VARCHAR(45) NULL,
                          `AGE` TINYINT(3) NULL,
                          PRIMARY KEY (`ID`),
                          UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) VISIBLE);
                    """;
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Не удалось создать таблицу.");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Не удалось выполнить удаление таблицы.");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.out.println("Не удалось добавить User в таблицу.");
            e.printStackTrace();
            try {
                if (session != null) session.getTransaction().rollback();
            } catch (HibernateException ex) {
                System.out.println("Не удалось откатить изменения.");
                ex.printStackTrace();
            }
        } finally {
            try {
                if (session != null) session.close();
            } catch (HibernateException exc) {
                System.out.println("Не удалось освободить ресурсы");
                exc.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Не удалось очистить таблицу. ");
            e.printStackTrace();
            try {
                if (session != null) session.getTransaction().rollback();
            } catch (HibernateException ex) {
                System.out.println("Не удалось откатить изменения");
                ex.printStackTrace();
            }
        } finally {
            try {
                if (session != null) session.close();
            } catch (HibernateException exc) {
                System.out.println("Не удалось освободить ресурсы");
                exc.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            result = session.createQuery("FROM User").getResultList();
        } catch (HibernateException e) {
            System.out.println("Не удалось вернуть список User.");
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM user").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Не удалось очистить таблицу.");
            e.printStackTrace();
            try {
                if (session != null) session.getTransaction().rollback();
            } catch (HibernateException ex) {
                System.out.println("Не удалось откатить изменения.");
                ex.printStackTrace();
            }
        } finally {
            try {
                if (session != null) session.close();
            } catch (HibernateException exc) {
                System.out.println("Не удалось освободить ресурсы");
                exc.printStackTrace();
            }

        }
    }
}
