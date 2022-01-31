package com.epam.rating.service;

import com.epam.rating.entity.Review;
import com.epam.rating.entity.enums.Rating;
import com.epam.rating.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ReviewService extends BaseService<Review>{
    List<Review> findAllByFilmId(Integer id) throws ServiceException;
    List<Review> findAllByUserId(Integer id) throws ServiceException;
    boolean create(String text, Rating rating, Integer filmId, Integer userId) throws ServiceException;
    Optional<Review> findByFilmIdUserId(Integer filmId, Integer userId) throws ServiceException;
    Double getAverageRating(Integer id) throws ServiceException;
    boolean deleteByFilmIdUserId(Integer filmId, Integer userId) throws ServiceException;
}
