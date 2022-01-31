package com.epam.rating.service.impl;

import com.epam.rating.dao.GenreDao;
import com.epam.rating.dao.impl.GenreDaoImpl;
import com.epam.rating.entity.enums.Genre;
import com.epam.rating.exception.DaoException;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class GenreServiceImpl implements GenreService {
    private static final Logger LOGGER = LogManager.getLogger(GenreService.class);
    private static final GenreDao genreDao = GenreDaoImpl.INSTANCE;

    public List<Genre> findAll() throws ServiceException {
        List<Genre> genres;
        try {
            genres = genreDao.findAll();
            if (!genres.isEmpty()) {
                Collections.sort(genres);
                return genres;
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return genres;
    }
}
