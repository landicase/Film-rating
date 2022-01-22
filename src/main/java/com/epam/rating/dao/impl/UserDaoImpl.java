package com.epam.rating.dao.impl;

import com.epam.rating.builder.Builder;
import com.epam.rating.dao.api.UserDao;
import com.epam.rating.entity.user.User;
import com.epam.rating.entity.user.Role;
import com.epam.rating.exception.DaoException;
import com.epam.rating.dao.api.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * <p>An implementation of the user dao interface to
 * provide access to the user entity in the MySql database.</p>
 *
 * @see User
 */
public class UserDaoImpl implements UserDao {

    public UserDaoImpl(Connection connection, Builder<User> builder){
        super(connection, builder);
    }
