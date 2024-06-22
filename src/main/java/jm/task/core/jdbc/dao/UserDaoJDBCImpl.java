package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection = getConnection();

    public void createUsersTable() throws SQLException {
        Statement statement = null;
        String sqlCreateUsersTable = "CREATE TABLE users(id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name NVARCHAR(20), lastName NVARCHAR(20), age TINYINT);";
        try {
            statement = connection.createStatement();
            statement.execute(sqlCreateUsersTable);
        } catch (SQLException e) {
            System.out.println("createUsersTable");
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }

    public void dropUsersTable() throws SQLException {
        Statement statement = null;
        String sqlDropUsersTable = "DROP TABLE users;";
        try {
            statement = connection.createStatement();
            statement.execute(sqlDropUsersTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sqlSaveUser = "INSERT users(name, lastName, age) " +
                "VALUES ('" + name + "', '" + lastName + "', " + age + " );";
        try {
            preparedStatement = connection.prepareStatement(sqlSaveUser);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sqlRemoveUserById = "DELETE FROM users WHERE id=?;";
        try {
            preparedStatement = connection.prepareStatement(sqlRemoveUserById);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sqlGetAllUsers = "SELECT * FROM users LIMIT 100;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
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
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        Statement statement = null;
        String sqlCleanUsersTable = "TRUNCATE TABLE users;";
        try {
            statement = connection.createStatement();
            statement.execute(sqlCleanUsersTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
//            if (connection != null) {
//                connection.close();
//            }
        }
    }
}
