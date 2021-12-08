package com.epam.rating.dao.impl;

import com.epam.rating.dao.api.CommentDao;
import com.epam.rating.entity.Comment;
import com.epam.rating.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    @Override
    public List<Comment> getAll() throws DaoException {
        return null;
    }

    @Override
    public void save(Comment entity) throws DaoException {

    }


    @Override
    public Optional<Comment> findById(int id) throws DaoException {
        return Optional.empty();
    }
}
