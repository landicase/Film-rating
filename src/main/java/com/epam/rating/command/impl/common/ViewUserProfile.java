package com.epam.rating.command.impl.common;

import com.epam.rating.command.*;
import com.epam.rating.entity.Review;
import com.epam.rating.entity.User;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.ReviewService;
import com.epam.rating.service.UserService;
import com.epam.rating.service.impl.ReviewServiceImpl;
import com.epam.rating.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ViewUserProfile implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(ViewUserProfile.class);
    private static final ReviewService reviewService = new ReviewServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            User user = (User) requestData.getSessionAttribute(AttributeName.USER);
            Optional<User> optionalUser = userService.findByLogin(user.getLogin());
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
            requestData.addSessionAttribute(AttributeName.USER, user);
            List<Review> reviewList = reviewService.findAllByUserId(user.getId());
            requestData.addRequestAttribute(AttributeName.REVIEW, reviewList);

            if (requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
                String page = requestData.getRequestParameter(AttributeName.PAGE);
                requestData.addSessionAttribute(AttributeName.PAGE, Integer.parseInt(page));
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while command define", e);
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.PROFILE.getPath());
    }
}
