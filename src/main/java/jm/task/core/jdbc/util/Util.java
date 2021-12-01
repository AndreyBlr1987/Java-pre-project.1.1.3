package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static String dbURL = "jdbc:mysql://localhost:3306/mydbtest";
    private static   String dbUSERNAME = "root";
    private static String dbPASSWORD = "1234567";
    private static String DIALECT = "org.hibernate.dialect.MySQL5Dialect";
    private static String MYDRIVER = "com.mysql.cj.jdbc.Driver";


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
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, MYDRIVER);
                settings.put(Environment.URL, dbURL);
                settings.put(Environment.USER, dbUSERNAME);
                settings.put(Environment.PASS, dbPASSWORD);
                settings.put(Environment.DIALECT, DIALECT);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  sessionFactory;
    }

}
