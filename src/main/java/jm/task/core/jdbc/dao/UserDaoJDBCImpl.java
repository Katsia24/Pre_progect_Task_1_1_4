package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private Connection connection = getConnection();

    public void createUsersTable() {
        String sqlCreateUsersTable = "CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name NVARCHAR(20), lastName NVARCHAR(20), age TINYINT);";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateUsersTable);
        } catch (SQLException e) {
            System.out.println("Error in createUsersTable");
            e.getMessage();
        }
    }

    public void dropUsersTable() {
        String sqlDropUsersTable = "DROP TABLE IF EXISTS users;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlDropUsersTable);
        } catch (SQLException e) {
            System.out.println("Error in dropUsersTable");
            e.getMessage();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSaveUser = "INSERT users(name, lastName, age) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSaveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error in saveUser");
            e.getMessage();
        }
    }

    public void removeUserById(long id) {
        String sqlRemoveUserById = "DELETE FROM users WHERE id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveUserById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error in removeUserById");
            e.getMessage();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sqlGetAllUsers = "SELECT * FROM users LIMIT 100;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlGetAllUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error in getAllUsers");
            e.getMessage();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sqlCleanUsersTable = "TRUNCATE TABLE users;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCleanUsersTable);
        } catch (SQLException e) {
            System.out.println("Error in cleanUsersTable");
            e.getMessage();
        }
    }
}
