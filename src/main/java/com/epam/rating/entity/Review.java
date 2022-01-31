package com.epam.rating.entity;

import com.epam.rating.entity.enums.Rating;

import java.io.Serializable;
import java.util.Objects;

public class Review extends AbstractIdentifiable implements Serializable {
    private int userID;
    private int filmID;
    private String review;
    private Rating rating;
    private int likesAmount;
    private int dislikesAmount;

    private Review(Builder builder) {
        this.userID = userID;
        this.filmID = filmID;
        this.review = review;
        this.rating = rating;
        this.likesAmount = likesAmount;
        this.dislikesAmount = dislikesAmount;
    }
    public static Builder builder() {
        return new Builder();
    }

    public int getUserID() {
        return userID;
    }
    public int getFilmID() {return filmID;}

    public String getReview() {
        return review;
    }

    public Rating getRating() {
        return rating;
    }

    public int getLikesAmount() {
        return likesAmount;
    }

    public int getDislikesAmount() {
        return dislikesAmount;
    }

    public static class Builder {
        private Integer userID;
        private Integer filmID;
        private String review;
        private Rating rating;
        private Integer likesAmount;
        private Integer dislikesAmount;

        private Builder() {
        }

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setfilmID(int filmID) {
            this.filmID = filmID;
            return this;
        }

        public Builder setReview(String review) {
            this.review = review;
            return this;
        }

        public Builder setRating(Rating rating) {
            this.rating = rating;
            return this;
        }

        public Builder setLikesAmount(int likesAmount) {
            this.likesAmount = likesAmount;
            return this;
        }

        public Builder setDislikesAmount(int dislikesAmount) {
            this.dislikesAmount = dislikesAmount;
            return this;
        }

        public Builder of(Review review) {
            this.userID = review.userID;
            this.filmID = review.filmID;
            this.review = review.review;
            this.rating = review.rating;
            this.likesAmount = review.likesAmount;
            this.dislikesAmount = review.dislikesAmount;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}

