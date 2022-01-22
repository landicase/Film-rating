package com.epam.rating.dao.api;

import com.epam.rating.entity.Identifiable;
import com.epam.rating.exception.ConnectionPoolException;
import com.epam.rating.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T extends Identifiable>{

    List<T> getAll() throws DaoException, ConnectionPoolException, SQLException;

    void save(T entity) throws DaoException;

    void deleteById(int id) throws DaoException;

    Optional<T> findById(int id) throws DaoException;

}
