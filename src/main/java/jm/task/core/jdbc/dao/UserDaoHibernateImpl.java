package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try {
             session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS newTable " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
        session.getTransaction().commit();}
        finally {
            session.close();
        }


    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS  newTable";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();}
        finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name,lastName,age);
        Session session = null;
        try{
            SessionFactory factory = Util.getSessionFactory();
         session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        System.out.println("User с именем – "+ name + " добавлен в базу данных");
        } finally {
          session.close();
        }
    }



    @Override
    public void removeUserById(long id) {
        Session session = null;
        try{
            SessionFactory sessionFactory = Util.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        Session session = null;
        try{
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            for(User user: list){
                System.out.println(user);
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return list ;

    }

    @Override
    public void cleanUsersTable() {
        List<User> list = null;
        Session session = null;
        try{
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            for(User user: list){
                session.delete(user);
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
