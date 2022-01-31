package com.epam.rating.dao;

import com.epam.rating.entity.User;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {

    boolean checkLogin(String login) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    boolean findByEmailPassword(String email, String password) throws DaoException;

    Integer getRoleId(String login) throws DaoException;

    List<User> findAllByFilmId(Integer id) throws DaoException;
}
