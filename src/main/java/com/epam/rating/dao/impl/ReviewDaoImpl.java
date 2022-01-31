package com.epam.rating.dao.impl;

import com.epam.rating.dao.ReviewDao;
import com.epam.rating.entity.Review;
import com.epam.rating.entity.enums.Rating;
import com.epam.rating.exception.ConnectionPoolException;
import com.epam.rating.exception.DaoException;
import com.epam.rating.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl extends AbstractDaoImpl<Review> implements ReviewDao {
    private static final Logger LOGGER = LogManager.getLogger(ReviewDaoImpl.class);
    public static final ReviewDaoImpl INSTANCE = new ReviewDaoImpl(ConnectionPool.getInstance());

    private static final String SQL_CREATE = "INSERT INTO film_review (film_id, user_id, rating, review)" + "VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM film_review";
    private static final String SQL_FIND_ALL_BY_FILM_ID = "SELECT * FROM film_review WHERE film_id = ?";
    private static final String SQL_FIND_ALL_BY_USER_ID = "SELECT * FROM film_review WHERE user_id = ?";
    private static final String SQL_FIND_BY_FILM_ID_USER_ID = "SELECT * FROM film_review WHERE film_id = ? AND user_id = ?";
    private static final String SQL_DELETE = "DELETE FROM film_review WHERE review_id = ? AND user_id = ? ";
    private static final String SQL_UPDATE = "UPDATE film_review SET text = ? WHERE review_id = ? user_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM film_review WHERE user_id = ? ";
    private static final String SQL_FIND_AVERAGE = "SELECT AVG(rating) FROM film_review WHERE film_id = ?";


    protected ReviewDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, Review entity) throws SQLException {
        preparedStatement.setInt(1, entity.getFilmID());
        preparedStatement.setInt(2, entity.getUserID());
        preparedStatement.setInt(3, entity.getRating().getId());
        preparedStatement.setString(4, entity.getReview());
    }

    @Override
    protected Optional<Review> parseResultSet(ResultSet resultSet) throws SQLException, DaoException {
        Review review = Review.builder().setfilmID(resultSet.getInt("film_id"))
                .setUserID(resultSet.getInt("user_id"))
                .setRating(Rating.resolveGenreById(resultSet.getInt("rating")))
                .setReview(resultSet.getString("text"))
                .build();
        return Optional.of(review);
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE;
    }

    @Override
    protected String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    protected String getCreateSql() {
        return SQL_CREATE;
    }

    @Override
    protected String getDeleteSql() {
        return SQL_DELETE;
    }

    @Override
    protected String getFindByIdSql() {
        return SQL_FIND_BY_ID;
    }

    protected String getFindAllByfilmIdSql() {
        return SQL_FIND_ALL_BY_FILM_ID;
    }

    protected String getFindAllByUserIdSql() {
        return SQL_FIND_ALL_BY_USER_ID;
    }

    protected String getFindByfilmIdUserIdSql() {
        return SQL_FIND_BY_FILM_ID_USER_ID;
    }

    public static String getFindAverageSql() {
        return SQL_FIND_AVERAGE;
    }

    @Override
    public boolean update(Review entity) throws DaoException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, entity.getReview());
                preparedStatement.setInt(2, entity.getFilmID());
                preparedStatement.setInt(3, entity.getUserID());
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Failed to update entity", new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }


    @Override
    public List<Review> findAllByFilmId(Integer filmId) throws DaoException {
        List<Review> reviewList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindAllByfilmIdSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, filmId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Review> optionalReview = parseResultSet(resultSet);
                    optionalReview.ifPresent(reviewList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return reviewList;
    }

    @Override
    public List<Review> findAllByUserId(Integer userId) throws DaoException {
        List<Review> reviewList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindAllByUserIdSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Review> optionalReview = parseResultSet(resultSet);
                    optionalReview.ifPresent(reviewList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return reviewList;
    }

    @Override
    public Optional<Review> findByFilmIdUserId(Integer filmId, Integer userId) throws DaoException {
        Optional<Review> optionalReview = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(getFindByfilmIdUserIdSql())) {
                statement.setInt(1, filmId);
                statement.setInt(2, userId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    optionalReview = parseResultSet(resultSet);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return optionalReview;
    }

    @Override
    public Double getAverageRating(Integer filmId) throws DaoException {
        double averageAppraisal = 0.0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindAverageSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, filmId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    averageAppraisal = resultSet.getDouble("AVG(rating)");
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return averageAppraisal;
    }

    @Override
    public boolean deleteByFilmIdUserId(Integer filmId, Integer userID) throws DaoException {
        boolean state = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getDeleteSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setInt(1, filmId);
                preparedStatement.setInt(2, userID);
                if (!preparedStatement.execute()) {
                    state = true;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Failed to delete entity", new DaoException(e));
            Thread.currentThread().interrupt();
        }
        return state;
    }

}
