package com.epam.rating.dao.factory;

import com.epam.rating.dao.api.*;

public interface DaoFactory {
    UserDao createUserDao();
    FilmDao createFilmDao();
    ReviewDao createReviewDao();
    CommentDao createCommentDao();
}
