package com.epam.rating.builder.impl;

import com.epam.rating.builder.Builder;
import com.epam.rating.entity.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewBuilder implements Builder<Review> {

    private static final String ID_COLUMN = "id";
    private static final String REVIEW_COLUMN = "review";
    private static final String MARK_COLUMN = "mark";
    private static final String LIKES_AMOUNT_COLUMN = "likesAmount";
    private static final String DISLIKES_AMOUNT_COLUMN = "dislikesAmount";

    @Override
    public Review build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        String review = resultSet.getString(REVIEW_COLUMN);
        int mark = resultSet.getInt(MARK_COLUMN);
        int likesAmount = resultSet.getInt(LIKES_AMOUNT_COLUMN);
        int dislikesAmount = resultSet.getInt(DISLIKES_AMOUNT_COLUMN);
        return new Review(id, review, mark, likesAmount, dislikesAmount);
    }
}
