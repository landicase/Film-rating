package com.epam.rating.dao;

import com.epam.rating.exception.DaoException;
import com.epam.rating.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Identifiable>  {

    int create(T entity) throws DaoException;

    boolean deleteById(Integer id) throws DaoException;

    Optional<T> getById(Integer id) throws DaoException;

    List<T> findAll() throws DaoException;

}
