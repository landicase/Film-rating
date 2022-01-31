package com.epam.rating.context.impl;

import com.epam.rating.context.ApplicationContext;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.Identifiable;
import com.epam.rating.exception.UnknownEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class FilmRatingsContext implements ApplicationContext {

    private static final Logger logger = LogManager.getLogger(FilmRatingsContext.class);
    private static final FilmRatingsContext instance = new FilmRatingsContext();
    private Collection<Film> films = new ArrayList<>();


    @Override
    public <T extends Identifiable> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "Film": {
                return (Collection<T>) films;
            }
            default: {
                throw new UnknownEntityException("Invalid entity - " + tClass);
            }
        }
    }

    @Override
    public void init() throws UnknownEntityException {
        logger.info("FilmRatings context launched");
    }

    public static FilmRatingsContext getInstance() {
        return instance;
    }
}
