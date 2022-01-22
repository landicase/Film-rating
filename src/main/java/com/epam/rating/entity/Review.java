package com.epam.rating.entity;

import java.io.Serializable;

public class Review implements Serializable, Identifiable {
    private int id;
    private String review;
    private int mark;
    private int likesAmount;
    private int dislikesAmount;

    public Review() {
    }

    public Review(int id, String review, int mark, int likesAmount,
                  int dislikesAmount) {
        this.id = id;
        this.review = review;
        this.mark = mark;
        this.likesAmount = likesAmount;
        this.dislikesAmount = dislikesAmount;

    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getLikesAmount() {
        return likesAmount;
    }

    public void setLikesAmount(int likesAmount) {
        this.likesAmount = likesAmount;
    }

    public int getDislikesAmount() {
        return dislikesAmount;
    }

    public void setDislikesAmount(int dislikesAmount) {
        this.dislikesAmount = dislikesAmount;
    }

}
