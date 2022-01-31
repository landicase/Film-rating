package com.epam.rating.command.impl.admin;

import com.epam.rating.command.*;
import com.epam.rating.entity.User;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.ReviewService;
import com.epam.rating.service.UserService;
import com.epam.rating.service.impl.ReviewServiceImpl;
import com.epam.rating.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DeleteUserReview implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserReview.class);
    private static final ReviewService reviewService = new ReviewServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            Integer filmId = Integer.parseInt(requestData.getRequestParameter("filmId"));
            Integer userId = Integer.parseInt(requestData.getRequestParameter("userId"));

            if (!reviewService.deleteByFilmIdUserId(filmId, userId)) {
                return new CommandExecute(RouteType.FORWARD, Destination.ERROR.getPath());
            } else {
                requestData.addRequestAttribute(AttributeName.RATING, reviewService.getAverageRating(filmId));
                requestData.addRequestAttribute(AttributeName.APPRAISAL, reviewService.findAllByFilmId(filmId).size());

                List<User> users = userService.findAllUsersByFilmId(filmId);
                requestData.deleteSessionAttribute(AttributeName.USERS);
                requestData.addSessionAttribute(AttributeName.USERS, users);

                requestData.addRequestAttribute(AttributeName.RELOAD, AttributeName.RELOAD);
                requestData.addSessionAttribute(AttributeName.PAGE, 0);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return new CommandExecute(RouteType.FORWARD, Destination.FILM_PAGE.getPath());
    }
}
