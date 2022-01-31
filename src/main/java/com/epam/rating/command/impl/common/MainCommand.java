package com.epam.rating.command.impl.common;

import com.epam.rating.command.*;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.FilmService;
import com.epam.rating.service.GenreService;
import com.epam.rating.service.impl.FilmServiceImpl;
import com.epam.rating.service.impl.GenreServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(MainCommand.class);
    private static final FilmService filmService = new FilmServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        if (requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
            String page = requestData.getRequestParameter(AttributeName.PAGE);
            requestData.addSessionAttribute(AttributeName.PAGE, Integer.parseInt(page));
        }
        try {
            if ((!requestData.getSessionAttributes().containsKey(AttributeName.FILMS) && !requestData.getSessionAttributes().containsKey(AttributeName.GENRES))
                    || !requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
                requestData.addSessionAttribute(AttributeName.FILMS, filmService.findAll());
                requestData.addSessionAttribute(AttributeName.GENRES, genreService.findAll());
                requestData.addSessionAttribute(AttributeName.PAGE, 0);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while trying to load information for the page", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
    }
}
