package com.epam.rating.service;

import com.epam.rating.entity.User;
import com.epam.rating.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User>{

    int registerUser(String login, String password, String name, String surname) throws ServiceException;

    boolean changePassword(Integer id, String password) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    int getRoleId(String login) throws ServiceException;

    boolean checkLogin(String login) throws ServiceException;

    boolean login(String email, String password) throws ServiceException;

    List<User> findAllUsersByFilmId(Integer id) throws ServiceException;

}