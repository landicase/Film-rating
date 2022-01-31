package com.epam.rating.dao;

import com.epam.rating.dao.Dao;
import com.epam.rating.entity.Review;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ReviewDao extends Dao<Review> {
    List<Review> findAllByFilmId(Integer filmId) throws DaoException;

    List<Review> findAllByUserId(Integer userId) throws DaoException;

    Optional<Review> findByFilmIdUserId(Integer filmId, Integer userId) throws DaoException;

    Double getAverageRating(Integer filmId) throws DaoException;

    boolean deleteByFilmIdUserId(Integer filmId, Integer userID) throws  DaoException;
}
