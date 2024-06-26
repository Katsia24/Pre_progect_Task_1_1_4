package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userServiceIml = new UserServiceImpl();

        userServiceIml.createUsersTable();
        userServiceIml.createUsersTable();

        userServiceIml.saveUser("Name1", "LastName1", (byte) 20);
        userServiceIml.saveUser("Name2", "LastName2", (byte) 25);
        userServiceIml.saveUser("Name3", "LastName3", (byte) 31);
        userServiceIml.saveUser("Name4", "LastName4", (byte) 38);

        userServiceIml.removeUserById(1);

        for (User user : userServiceIml.getAllUsers()) {
            System.out.println(user);
        }

        userServiceIml.cleanUsersTable();

        for (User user : userServiceIml.getAllUsers()) {
            System.out.println(user);
        }

        userServiceIml.dropUsersTable();

        Util.shutdownHib(); // getSessionFactory().close();
    }
}
