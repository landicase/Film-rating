package com.epam.rating.dao.api;

import com.epam.rating.entity.user.User;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    List<User> getAllUsers() throws DaoException;
}
