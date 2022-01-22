package com.epam.rating.dao.api;

import com.epam.rating.entity.Film;
import com.epam.rating.exception.DaoException;

import java.util.List;

public interface FilmDao extends Dao<Film> {
    List<Film> findFilmBy(int userid, int trainerId) throws DaoException;
}
