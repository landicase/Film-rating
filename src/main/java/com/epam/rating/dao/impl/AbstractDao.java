package com.epam.rating.dao.impl;

import com.epam.rating.dao.api.Dao;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.Identifiable;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T>{

    @Override
    public Optional<T> findById(int id) throws DaoException {
        return null;
    }

    public List<T> getAll() throws DaoException{
        return null;
    }
    public void deleteById(int id){
        throw new UnsupportedOperationException();
    }
}
