package com.epam.rating.dao.impl;

import com.epam.rating.dao.api.UserDao;
import com.epam.rating.entity.user.User;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    @Override
    public List<User> getAll() throws DaoException {
        return null;
    }

    @Override
    public void save(User entity) throws DaoException {

    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        return Optional.empty();
    }
}
