package com.epam.rating.dao.impl;

import com.epam.rating.builder.impl.ReviewBuilder;
import com.epam.rating.dao.api.ReviewDao;
import com.epam.rating.entity.Review;
import com.epam.rating.exception.ConnectionPoolException;
import com.epam.rating.exception.DaoException;
import com.epam.rating.pool.ConnectionPool;
import com.epam.rating.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao {
    public static final String ID = "id";
    public static final String REVIEW = "review";
    public static final String MARK = "mark";
    public static final String LIKES_AMOUNT = "likesAmount";
    public static final String DISLIKES_AMOUNT = "dislikesAmount";
    public static final String IS_LIKED = "is_liked";

    ConnectionPool connectable;

    {
        try {
            connectable = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    public ReviewDaoImpl() {
    }

    ;

    public static String GET_REVIEWS_BY_ID = "select review.id, review.review, review.mark, review.likes_amount, review.dislikes_amount from review WHERE review.film_id=? AND review.users_id=?;";

    public static String GET_REVIEWS = "select * from review;";
    public static String ADD_REVIEW = "insert into review values (review, mark, film_id, users_id) values(?, ?, ?, ?);";

    public List<Review> getAll() throws SQLException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Review> users = new ArrayList<>();
            connection = connectable.getConnection();
            preparedStatement = connection.prepareStatement(GET_REVIEWS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(ReviewBuilder.build(resultSet));
            }
            return users;
        } catch (SQLException | ConnectionPoolException sqlE) {
            throw new SQLException();
        } finally {
            connectable.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void save(Review entity) throws DaoException {

    }

    @Override
    public void deleteById(int id) throws DaoException {

    }

    @Override
    public Optional<Review> findById(int id) throws DaoException {
        return Optional.empty();
    }

    public List<Review> getReviewById(int filmId, int userId) throws SQLException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Review> reviews = new ArrayList<>();
            connection = connectable.getConnection();
            preparedStatement = connection.prepareStatement(GET_REVIEWS_BY_ID);
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviews.add(ReviewBuilder.build(resultSet));
            }

            return reviews;
        } catch (SQLException | ConnectionPoolException sqlE) {
            throw new SQLException();
        } finally {
            connectable.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    public int add(Review review) throws SQLException, InterruptedException, ConnectionPoolException {
        Connection connection = connectable.getConnection();
        PreparedStatement pr = null;

        try {
            pr = connection.prepareStatement(ADD_REVIEW);

            pr.setString(1, review.getReview());
            pr.setInt(2, review.getMark());

            pr.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            //TODO
        } finally {
            connectable.closeConnection(connection, pr);
        }
        return 1;

    }
}
