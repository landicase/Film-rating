package com.epam.rating.command.impl.common;

import com.epam.rating.command.*;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.enums.Genre;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.FilmService;
import com.epam.rating.service.impl.FilmServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SortFilmByGenre implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(SortFilmByGenre.class);
    private static final FilmService filmService = new FilmServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            String genre = requestData.getRequestParameter(AttributeName.GENRE);
            List<Film> filmList = filmService.findAllByGenre(Genre.resolveGenreByName(genre));
            if (!filmList.isEmpty()) {
                requestData.addSessionAttribute(AttributeName.FILMS, filmList);

                if (requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
                    String page = requestData.getRequestParameter(AttributeName.PAGE);
                    requestData.addRequestAttribute(AttributeName.PAGE, Integer.parseInt(page));
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while command define", e);
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }

        return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
    }
}
