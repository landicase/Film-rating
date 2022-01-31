package com.epam.rating.dao.impl;

import com.epam.rating.dao.UserDao;
import com.epam.rating.entity.User;
import com.epam.rating.entity.enums.Role;
import com.epam.rating.exception.ConnectionPoolException;
import com.epam.rating.exception.DaoException;
import com.epam.rating.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    public static final UserDaoImpl INSTANCE = new UserDaoImpl(ConnectionPool.getInstance());

    protected UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    private static final String SQL_CREATE = "INSERT INTO user (password, login, name, surname, role_id)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE id = ? ";
    private static final String SQL_UPDATE_LOGIN = "UPDATE user SET login = ? WHERE password = ? ";

    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ? ";

    private static final String SQL_FIND_ALL = "SELECT * FROM user";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM user WHERE id = ? ";
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login= ?";
    private static final String SQL_FIND_BY_EMAIL_PASSWORD = "SELECT * FROM user WHERE email= ? AND password = ?";
    private static final String SQL_FIND_USERS_BY_FILM_ID = "SELECT * FROM user JOIN film_review mr on user.id = mr.user_id where film_id = ?";


    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedStatement.setString(1, entity.getPassword());
        preparedStatement.setString(2, entity.getLogin());
        preparedStatement.setString(3, entity.getName());
        preparedStatement.setString(4,entity.getSurname());
        preparedStatement.setInt(5, entity.getRole().getId());
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_PASSWORD;
    }

    @Override
    protected String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    protected String getCreateSql() {
        return SQL_CREATE;
    }

    @Override
    protected String getDeleteSql() {
        return SQL_DELETE;
    }

    @Override
    protected String getFindByIdSql() {
        return SQL_FIND_BY_ID;
    }

    protected static String getUpdateLoginSql() {
        return SQL_UPDATE_LOGIN;
    }

    protected static String getFindByLoginSql() {
        return SQL_FIND_BY_LOGIN;
    }


    protected static String getFindByEmailPasswordSql() {
        return SQL_FIND_BY_EMAIL_PASSWORD;
    }

    public static String getFindUsersByFilmIdSql() {
        return SQL_FIND_USERS_BY_FILM_ID;
    }

    @Override
    protected Optional<User> parseResultSet(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .setName(resultSet.getString("name"))
                .setSurname(resultSet.getString("surname"))
                .setLogin(resultSet.getString("login"))
                .setPassword(resultSet.getString("password"))
                .setRole(Role.resolveRoleById(resultSet.getInt("role_id")));
        user.setId(resultSet.getInt("id"));
        return Optional.of(user);
    }

    @Override
    public boolean update(User entity) throws DaoException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, entity.getPassword());
                preparedStatement.setInt(2, entity.getId());
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Failed to update entity", new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }


    @Override
    public Integer getRoleId(String login) throws DaoException {
        int roleId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByLoginSql())) {
                connection.setAutoCommit(false);
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    roleId = resultSet.getInt("role_id");
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return roleId;
    }

    @Override
    public List<User> findAllByFilmId(Integer id) throws DaoException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindUsersByFilmIdSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<User> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(userList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return userList;
    }


    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindByLoginSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    entityOptional = parseResultSet(resultSet);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e));
            Thread.currentThread().interrupt();
        }
        return entityOptional;
    }

    @Override
    public boolean checkLogin(String login) throws DaoException {
        boolean state = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByLoginSql())) {
                connection.setAutoCommit(false);
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                state = resultSet.next();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return state;
    }

    @Override
    public boolean findByEmailPassword(String email, String password) throws DaoException {
        boolean state = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByEmailPasswordSql())) {
                connection.setAutoCommit(false);
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                state = resultSet.next();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return state;
    }
}