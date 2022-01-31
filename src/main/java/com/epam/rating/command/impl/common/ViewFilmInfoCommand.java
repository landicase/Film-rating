package com.epam.rating.command.impl.common;


import com.epam.rating.command.*;
import com.epam.rating.entity.Film;
import com.epam.rating.entity.Review;
import com.epam.rating.entity.User;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.FilmService;
import com.epam.rating.service.ReviewService;
import com.epam.rating.service.UserService;
import com.epam.rating.service.impl.FilmServiceImpl;
import com.epam.rating.service.impl.ReviewServiceImpl;
import com.epam.rating.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ViewFilmInfoCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(ViewFilmInfoCommand.class);
    private static final FilmService filmService = new FilmServiceImpl();
    private static final ReviewService reviewService = new ReviewServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            Optional<Film> filmOptional;
            Integer filmID;
            if (requestData.getRequestParametersValues().containsKey(AttributeName.ID)) {
                filmID = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
                filmOptional = filmService.getById(filmID);
            } else {
                Film film = (Film) requestData.getSessionAttribute(AttributeName.FILM);
                filmID = film.getId();
                filmOptional = Optional.of(film);
            }

            filmOptional.ifPresent(value -> {
                try {
                    if (requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
                        String page = requestData.getRequestParameter(AttributeName.PAGE);
                        requestData.addSessionAttribute(AttributeName.PAGE, Integer.parseInt(page));
                    }

//                    List<MovieCrewMember> filmActors = movieCrewService.findAllActorsByMovieId(value.getId());
//                    if (!filmActors.isEmpty()) {
//                        requestData.addRequestAttribute(AttributeName.ACTOR, filmActors);
//                    }
//                    MovieCrewMember director = movieCrewService.findDirectorByMovieId(value.getId());
//                    if (director.getName() != null) {
//                        requestData.addRequestAttribute(AttributeName.DIRECTOR, director);
//                    }

                    List<Review> reviewList = reviewService.findAllByFilmId(filmOptional.get().getId());

                    Double averageRating = reviewService.getAverageRating(filmID);
                    if (averageRating != null && averageRating != 0.0 && !reviewList.isEmpty()) {
                        BigDecimal bd = new BigDecimal(Double.toString(averageRating));
                        bd = bd.setScale(2, RoundingMode.HALF_UP);
                        requestData.addRequestAttribute(AttributeName.RATING, bd.doubleValue());
                    }

                    List<Review> reviewListWithText = reviewList.stream().filter(review -> !review.getReview().isEmpty()).collect(Collectors.toList());
                    requestData.addSessionAttribute(AttributeName.REVIEW, reviewListWithText);

                    List<User> users = userService.findAllUsersByFilmId(filmID);
                    requestData.deleteSessionAttribute(AttributeName.USERS);
                    requestData.addSessionAttribute(AttributeName.USERS, users);
                    if (!reviewList.isEmpty()) {
                        requestData.addRequestAttribute(AttributeName.APPRAISAL, reviewList.size());
                    }

                    if (requestData.getSessionAttributes().containsKey(AttributeName.ERROR_REVIEW)) {
                        requestData.addRequestAttribute(AttributeName.ERROR_REVIEW, requestData.getSessionAttribute(AttributeName.ERROR_REVIEW));
                        requestData.deleteSessionAttribute(AttributeName.ERROR_REVIEW);
                    }
                    filmOptional.ifPresent(film -> requestData.addSessionAttribute(AttributeName.FILM, film));
                } catch (ServiceException e) {
                    LOGGER.error("Error while trying to load film page", e);
                }
            });
        } catch (Exception e) {
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.FILM_PAGE.getPath());
    }
}
