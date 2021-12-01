package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private  String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS newTable(id  BigInt PRIMARY KEY AUTO_INCREMENT, name VARCHAR(55),lastName VARCHAR(55), age TinyInt )";
    private static String INSERT_USER = "INSERT INTO newTable (name,lastname, age) VALUES (?,?,?)";
    private static String DELETE_USER = "DELETE FROM   newTable  WHERE id = ?";
    private static String CLEAN_USER = "DELETE  FROM newTable";
   private static String DROP_TABLE = "DROP TABLE IF EXISTS  newTable";
    private static String GET_ALL = "SELECT * FROM newTable ";
    Connection connection ;

    public UserDaoJDBCImpl(Connection connection){
        this.connection = connection;
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE);
        ) {
            preparedStatement.execute();
            System.out.println("Таблица создана");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
         public void dropUsersTable() {
             try(Connection connection = Util.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE);
            ){
                 preparedStatement.executeUpdate();
                 System.out.println("Таблица удалена");

             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             }

        }
    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
        ){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.println("User с именем – "+ name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
        ){
            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
        ){
            ResultSet res =  preparedStatement.executeQuery();
            while(res.next()){
                User user = new User(res.getString("name"),res.getString("lastname"),res.getByte("age"));
                user.setId(res.getLong("id"));
                ;users.add(user);

            }
        } catch(SQLException e ) {
            System.out.println("Ошибка запроса");

        }
        System.out.println(users);
        return users;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_USER);
        ){
            preparedStatement.execute();
            System.out.println("Таблица очищена");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
