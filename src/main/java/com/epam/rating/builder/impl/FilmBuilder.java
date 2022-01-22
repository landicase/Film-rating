package com.epam.rating.builder.impl;

import com.epam.rating.builder.Builder;
import com.epam.rating.entity.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmBuilder implements Builder<Film> {
    private static final String ID_COLUMN = "id";
    private static final String PRODUCTION_YEAR_COLUMN = "production_year";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String NAME_COLUMN = "name";
    private static final String FILM_RATING_COLUMN = "film_rating";
    private static final String REVIEW_AMOUNT_COLUMN = "review_amount";
    private static final String AGE_RATING_COLUMN ="age_rating";
    private static final String TYPE_COLUMN ="type";


    @Override
    public Film build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        int production_year = resultSet.getInt(PRODUCTION_YEAR_COLUMN);
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        double rating = resultSet.getDouble(FILM_RATING_COLUMN);
        int review_amount = resultSet.getInt(REVIEW_AMOUNT_COLUMN);
        int age_rating = resultSet.getInt(AGE_RATING_COLUMN);
        String type = resultSet.getString(TYPE_COLUMN);
        return new Film(id, production_year, name, description, rating, review_amount, type, age_rating);
    }

}
