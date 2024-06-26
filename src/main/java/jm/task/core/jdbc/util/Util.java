package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_for_kata";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException exception) {
            System.out.println("Connection FAIL");
            exception.printStackTrace();
        }
        return connection;
    }

    public static void shutdownConn() {
        try {
            getConnection().close();
            System.out.println("Connection CLOSE");
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии соединения");
            e.printStackTrace();
        }
    }


    // Hibernate setting equivalent to hibernate.cfg.xml's properties
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties setting = new Properties();
                setting.put(Environment.DRIVER, DB_DRIVER);
                setting.put(Environment.URL, DB_URL);
                setting.put(Environment.USER, DB_USERNAME);
                setting.put(Environment.PASS, DB_PASSWORD);
                setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                setting.put(Environment.SHOW_SQL, "true");
                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                setting.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(setting);
                configuration.addAnnotatedClass(User.class); // add JPA entity mapping class

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("ConnectionHib OK");
            } catch (Exception e) {
                System.out.println("Error in Hibernate Util connection");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdownHib() {
        getSessionFactory().close();
        System.out.println("sessionFactory CLOSE");
    }

}
