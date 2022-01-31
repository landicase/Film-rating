package com.epam.rating.service.impl;


import com.epam.rating.dao.impl.ReviewDaoImpl;
import com.epam.rating.entity.Review;
import com.epam.rating.entity.enums.Rating;
import com.epam.rating.exception.DaoException;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService {
    private static final Logger LOGGER = LogManager.getLogger(ReviewService.class);

    private static final ReviewDaoImpl reviewDao = ReviewDaoImpl.INSTANCE;

    @Override
    public List<Review> findAll() throws ServiceException {
        List<Review> reviewList;
        try {
            reviewList = reviewDao.findAll();
            if (!reviewList.isEmpty()) {
                return reviewList;
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return reviewList;
    }

    @Override
    public Optional<Review> getById(Integer id) throws ServiceException {
        try {
            Optional<Review> optionalReview = reviewDao.getById(id);
            if (optionalReview.isPresent()) {
                return optionalReview;
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Review entity) throws ServiceException {
        try {
            return reviewDao.update(entity);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean deleteById(Integer id) throws ServiceException {
        try {
            return reviewDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Review> findAllByFilmId(Integer id) throws ServiceException {
        List<Review> reviewList;
        try {
            reviewList = reviewDao.findAllByFilmId(id);
            if (!reviewList.isEmpty()) {
                return reviewList;
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return reviewList;
    }

    @Override
    public List<Review> findAllByUserId(Integer id) throws ServiceException {
        List<Review> reviewList;
        try {
            reviewList = reviewDao.findAllByUserId(id);
            if (!reviewList.isEmpty()) {
                return reviewList;
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return reviewList;
    }

    @Override
    public boolean create(String text, Rating rating, Integer filmId, Integer userId) throws ServiceException {
        try {
            reviewDao.create(Review.builder().setfilmID(filmId).setUserID(userId).setRating(rating).setReview(text).build());
            return true;
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<Review> findByFilmIdUserId(Integer filmId, Integer userId) throws ServiceException {
        try {
            return reviewDao.findByFilmIdUserId(filmId, userId);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Double getAverageRating(Integer id) throws ServiceException {
        try {
            Double averageRating = reviewDao.getAverageRating(id);
            if (!averageRating.isNaN()) {
                BigDecimal bigDecimal = BigDecimal.valueOf(averageRating);
                bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
                return bigDecimal.doubleValue();
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return 0.0;
    }

    @Override
    public boolean deleteByFilmIdUserId(Integer filmId, Integer userId) throws ServiceException {
        try {
            return reviewDao.deleteByFilmIdUserId(filmId, userId);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }
}
