package com.epam.rating.service.impl;

import com.epam.rating.dao.api.UserDao;
import com.epam.rating.dao.factory.DaoFactory;
import com.epam.rating.entity.user.User;
import com.epam.rating.exception.DaoException;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.api.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(DaoFactory factory){
        this.userDao = factory.createUserDao();
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        try {
            String md5Password = DigestUtils.md5Hex(password);
            return userDao.findUserByEmailAndPassword(email, md5Password);
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<User> findUsersById(int trainerId) throws ServiceException {
        return null;
    }


    @Override
    public List<User> getAllUsers() throws ServiceException {
        try{
            return userDao.getAllUsers();
        } catch (DaoException ex){
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public void setUser(int id, int discount) throws ServiceException {

    }

}
