package com.epam.rating.service.impl;

import com.epam.rating.context.impl.FilmRatingsContext;
import com.epam.rating.dao.impl.FilmDaoImpl;
import com.epam.rating.entity.Country;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.enums.Genre;
import com.epam.rating.exception.DaoException;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class FilmServiceImpl implements FilmService {
    private static final Logger LOGGER = LogManager.getLogger(FilmServiceImpl.class);
    private static final FilmDaoImpl filmDao = FilmDaoImpl.INSTANCE;

    @Override
    public List<Film> findAll() throws ServiceException {
        List<Film> filmList;
        try {
            if (FilmRatingsContext.getInstance().retrieveBaseEntityList(Film.class).isEmpty()) {
                filmList = filmDao.findAll();
                for (Film film :
                        filmList) {
                    FilmRatingsContext.getInstance().retrieveBaseEntityList(Film.class).add(film);
                }
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return (List<Film>) FilmRatingsContext.getInstance().retrieveBaseEntityList(Film.class);
    }

    @Override
    public boolean update(Film entity) throws ServiceException {
        try {
            return filmDao.update(entity);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean deleteById(Integer id) throws ServiceException {
        try {
            return filmDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<Film> findByName(String name) throws ServiceException {
        try {
            return filmDao.findByName(name);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Film> findAllByGenre(Genre genre) throws ServiceException {
        try {
            return filmDao.findAllByGenre(genre);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Film> findAllByProductionYear(Integer year) throws ServiceException {
        try {
            return filmDao.findAllByProductionYear(year);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Film> findAllByCountry(Country country) throws ServiceException {
        try {
            return filmDao.findAllByCountry(country);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<Film> getById(Integer id) throws ServiceException {
        try {
            return filmDao.getById(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }
}
