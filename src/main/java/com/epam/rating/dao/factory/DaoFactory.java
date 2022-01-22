package com.epam.rating.dao.factory;

import com.epam.rating.dao.api.FilmDao;
import com.epam.rating.dao.api.ReviewDao;
import com.epam.rating.dao.api.UserDao;
import com.epam.rating.dao.impl.FilmDaoImpl;
import com.epam.rating.dao.impl.ReviewDaoImpl;
import com.epam.rating.dao.impl.UserDaoImpl;
import com.epam.rating.entity.Review;

public interface DaoFactory {

    UserDao createUserDao();
    FilmDao createFilmDao();
    ReviewDao createReviewDao();
}