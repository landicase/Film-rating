package com.epam.rating.service.api;

import com.epam.rating.entity.user.User;
import com.epam.rating.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> login(String email, String password) throws ServiceException;
    List<User> findUsersById(int trainerId) throws ServiceException;
    List<User> getAllUsers() throws ServiceException;
    void setUser(int id, int discount) throws ServiceException;
}