package com.epam.rating.service;

import com.epam.rating.entity.Review;
import com.epam.rating.entity.enums.Genre;
import com.epam.rating.exception.ServiceException;

import java.util.List;

public interface GenreService{

    List<Genre> findAll() throws ServiceException;
}
