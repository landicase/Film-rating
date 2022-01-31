package com.epam.rating.command.impl.common;

import com.epam.rating.command.*;

import com.epam.rating.entity.Film;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.FilmService;
import com.epam.rating.service.impl.FilmServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class FindFilmCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(FindFilmCommand.class);
    private static final FilmService filmService = new FilmServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        String filmName = requestData.getRequestParameter(AttributeName.FILM);
        try {
            Optional<Film> optionalFilm = filmService.findByName(filmName);
            if (optionalFilm.isPresent()) {
                requestData.addSessionAttribute(AttributeName.FILM, optionalFilm.get());
                requestData.addRequestAttribute(AttributeName.RELOAD, AttributeName.RELOAD);
                requestData.deleteSessionAttribute(AttributeName.PAGE);

                if (requestData.getSessionAttributes().containsKey(AttributeName.REVIEW)) {
                    requestData.deleteSessionAttribute(AttributeName.REVIEW);
                    requestData.deleteSessionAttribute(AttributeName.USERS);
                }

                return new CommandExecute(RouteType.FORWARD, Destination.FILM_PAGE.getPath());
            } else {
                return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
            }
        } catch (ServiceException e) {
            LOGGER.error("Film Search Error", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
    }
}
