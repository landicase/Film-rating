package com.epam.rating.dao;

import com.epam.rating.entity.enums.Genre;
import com.epam.rating.exception.DaoException;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll() throws DaoException;
}
