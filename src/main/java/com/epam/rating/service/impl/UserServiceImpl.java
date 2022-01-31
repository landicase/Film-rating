package com.epam.rating.service.impl;

import com.epam.rating.dao.impl.UserDaoImpl;
import com.epam.rating.entity.User;
import com.epam.rating.entity.enums.Role;
import com.epam.rating.exception.DaoException;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final UserDaoImpl userDao = UserDaoImpl.INSTANCE;

    @Override
    public int registerUser(String login, String password, String name, String surname) {
        try {
            String md5Hex = DigestUtils
                    .md5Hex(password).toUpperCase();
            if (!checkLogin(login)) {
                User user = User.builder()
                        .setName(name)
                        .setSurname(surname)
                        .setLogin(login)
                        .setPassword(md5Hex)
                        .setRole(Role.USER);
                return create(user);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return 0;
    }

    @Override
    public boolean changePassword(Integer id, String newPassword) throws ServiceException {
        Optional<User> optionalUser = getById(id);
        try {
            if (optionalUser.isPresent()) {
                User user = User.builder().of(optionalUser.get()).setPassword(DigestUtils
                        .md5Hex(newPassword).toUpperCase()).build();
                return userDao.update(user);
            }
            return false;
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }


    @Override
    public int getRoleId(String login) throws ServiceException {
        int id;
        try {
            id = userDao.getRoleId(login);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return id;
    }

    public boolean checkLogin(String login) throws ServiceException {
        try {
            return userDao.checkLogin(login);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean login(String email, String password) throws ServiceException {
        try {
            return userDao.findByEmailPassword(email, DigestUtils.md5Hex(password).toUpperCase());
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<User> findAllUsersByFilmId(Integer id) throws ServiceException {
        List<User> userList = null;
        try {
            userList = userDao.findAllByFilmId(id);
            if (!userList.isEmpty()) {
                return userList;
            }
        } catch (DaoException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
        return userList;
    }

    @Override
    public boolean update(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean deleteById(Integer id) throws ServiceException {
        try {
            return userDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<User> getById(Integer id) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.getById(id);
            if (optionalUser.isPresent()) {
                return optionalUser;
            }
        } catch (DaoException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> userList = null;
        try {
            userList = userDao.findAll();
            if (!userList.isEmpty()) {
                return userList;
            }
        } catch (DaoException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
        return userList;
    }


    private int create(User user) {
        int id = 0;
        try {
            id = userDao.create(user);
        } catch (DaoException e) {
            LOGGER.error("Exception during data update", e);

        }
        return id;
    }

}
