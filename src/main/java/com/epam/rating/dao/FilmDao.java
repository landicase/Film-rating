package com.epam.rating.dao;

import com.epam.rating.dao.Dao;
import com.epam.rating.entity.Country;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.enums.Genre;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface FilmDao extends Dao<Film> {
    Optional<Film> findByName(String name) throws DaoException;

    List<Film> findAllByGenre(Genre genre) throws DaoException;

    List<Film> findAllByProductionYear(Integer year) throws DaoException;

    List<Film> findAllByCountry(Country country) throws DaoException;
}
