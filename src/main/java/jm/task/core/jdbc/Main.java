package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan","Ivanov" , (byte) 20);
        userService.saveUser("Sergey","Sergeev" , (byte) 22);
        userService.saveUser("Andrey","Andreev" , (byte) 25);
        userService.saveUser("Egor","Egorov" , (byte) 30);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();



    }
}
