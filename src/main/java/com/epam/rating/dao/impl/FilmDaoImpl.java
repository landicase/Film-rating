package com.epam.rating.dao.impl;

import com.epam.rating.dao.FilmDao;
import com.epam.rating.entity.Country;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.enums.Genre;
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

public class FilmDaoImpl extends AbstractDaoImpl<Film> implements FilmDao {
    private static final Logger LOGGER = LogManager.getLogger(FilmDaoImpl.class);
    public static final FilmDaoImpl INSTANCE = new FilmDaoImpl(ConnectionPool.getInstance());

    private static final String SQL_CREATE = "INSERT INTO film (name, production_year, duration, country_id, tagline, genre_id, poster_path)"
            + "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM film WHERE id = ? ";

    private static final String SQL_FIND_ALL = "SELECT * FROM film join country c on c.id = film.country_id";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM film join country c on c.id = film.country_id WHERE film.id = ?";
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM film join country c on c.id = film.country_id  WHERE film.name = ?";
    private static final String SQL_FIND_BY_COUNTRY = "SELECT * FROM film join country c on c.id = film.country_id  WHERE country_id = ?";
    private static final String SQL_FIND_BY_PUBLICATION_YEAR = "SELECT * FROM film join country c on c.id = film.country_id WHERE publication_year = ?";
    private static final String SQL_FIND_BY_GENRE = "SELECT * FROM film join country c on c.id = film.country_id  WHERE genre_id = ?";
    private static final String SQL_UPDATE_NAME = "UPDATE film SET name = ? WHERE id = ?";


    protected FilmDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, Film entity) throws SQLException {
        preparedAllFilmStatements(preparedStatement, entity);
    }

    @Override
    protected Optional<Film> parseResultSet(ResultSet resultSet) throws SQLException {
        Country country = Country.builder().setName(resultSet.getString("c.name")).build();
        country.setId(resultSet.getInt("country_id"));
        Film film = Film.builder()
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setPoster(resultSet.getString("poster_path"))
                .setReleaseYear(resultSet.getInt("release_year"))
                .setDuration(resultSet.getTime("duration"))
                .setReleaseCountry(country)
                .setTagline(resultSet.getString("tagline"))
                .setGenre(Genre.resolveGenreById(resultSet.getInt("genre_id"))).build();
        film.setId(resultSet.getInt("id"));
        return Optional.of(film);
    }

    private void preparedAllFilmStatements(PreparedStatement preparedStatement, Film film) throws SQLException {
        preparedStatement.setInt(1, film.getId());
        preparedStatement.setString(2, film.getName());
        preparedStatement.setInt(3, film.getReleaseYear());
        preparedStatement.setTime(4, film.getDuration());
        preparedStatement.setInt(5, film.getCountry().getId());
        preparedStatement.setString(6, film.getTagline());
        preparedStatement.setInt(7, film.getGenre().getId());
    }


    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_NAME;
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

    protected static String getFindByNameSql() {
        return SQL_FIND_BY_NAME;
    }

    protected static String getFindByGenreSql() {
        return SQL_FIND_BY_GENRE;
    }

    protected static String getFindByProductionYearSql() {
        return SQL_FIND_BY_PUBLICATION_YEAR;
    }

    protected static String getFindByCountrySql() {
        return SQL_FIND_BY_COUNTRY;
    }

    @Override
    public boolean update(Film entity) throws DaoException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setInt(2, entity.getId());
                if ((preparedStatement.executeUpdate() != 0)) {
                    updated = true;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Failed to update film", new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }

    @Override
    public Optional<Film> findByName(String name) throws DaoException {
        Optional<Film> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(getFindByNameSql())) {
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    entityOptional = parseResultSet(resultSet);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e));
            Thread.currentThread().interrupt();
        }
        return entityOptional;
    }

    @Override
    public List<Film> findAllByGenre(Genre genre) throws DaoException {
        List<Film> films = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(getFindByGenreSql())) {
                statement.setInt(1, genre.getId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Film> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(films::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return films;
    }

    @Override
    public List<Film> findAllByProductionYear(Integer year) throws DaoException {
        List<Film> films = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(getFindByProductionYearSql())) {
                statement.setInt(1, year);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Film> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(films::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return films;
    }

    @Override
    public List<Film> findAllByCountry(Country country) throws DaoException {
        List<Film> films = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(getFindByCountrySql())) {
                statement.setInt(1, country.getId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<Film> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(films::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(new DaoException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return films;
    }
}
