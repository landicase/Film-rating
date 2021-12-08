package com.epam.rating.dao.impl;

import com.epam.rating.dao.api.ReviewDao;
import com.epam.rating.entity.Review;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl extends AbstractDao<Review> implements ReviewDao {
    @Override
    public List<Review> getAll() throws DaoException {
        return null;
    }

    @Override
    public void save(Review entity) throws DaoException {

    }

    @Override
    public Optional<Review> findById(int id) throws DaoException {
        return Optional.empty();
    }
}
