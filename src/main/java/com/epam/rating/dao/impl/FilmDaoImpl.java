package com.epam.rating.dao.impl;

import com.epam.rating.dao.api.FilmDao;
import com.epam.rating.entity.Film;
import com.epam.rating.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FilmDaoImpl implements FilmDao {

    @Override
    public List<Film> getAll() throws DaoException {
        return null;
    }

    @Override
    public void save(Film entity) throws DaoException {

    }

    @Override
    public void deleteById(int id) throws DaoException {

    }

    @Override
    public Optional<Film> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public int addTrailer(String trailerPath, int filmId) throws SQLException, InterruptedException {
        return 0;
    }

    @Override
    public List<Film> findFilmBy(int userid, int trainerId) throws DaoException {
        return null;
    }

}
