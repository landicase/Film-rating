package com.epam.rating.service;

import com.epam.rating.entity.Country;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.enums.Genre;
import com.epam.rating.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface FilmService extends BaseService<Film>{
    Optional<Film> findByName(String name) throws ServiceException;

    List<Film> findAllByGenre(Genre genre) throws ServiceException;

    List<Film> findAllByProductionYear(Integer year) throws ServiceException;

    List<Film> findAllByCountry(Country country) throws ServiceException;
}