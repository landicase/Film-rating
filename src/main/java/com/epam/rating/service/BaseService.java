package com.epam.rating.service;

import com.epam.rating.entity.Identifiable;
import com.epam.rating.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends Identifiable> {

    List<T> findAll() throws ServiceException;

    Optional<T> getById(Integer id) throws ServiceException;

    boolean deleteById(Integer id) throws ServiceException;

    boolean update(T entity) throws ServiceException;
}
