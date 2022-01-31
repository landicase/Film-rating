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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LeaveReviewCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(LeaveReviewCommand.class);
    private static final ReviewService reviewService = new ReviewServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {

            Integer filmId = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));

            String rating = requestData.getRequestParameter(AttributeName.RATING);

            String text = "";
            List<Review> reviewList;
            Double avrRating;
            if (requestData.getRequestParametersValues().containsKey(AttributeName.COMMENT)) {
                text = requestData.getRequestParameter(AttributeName.COMMENT).trim();
            }
            if (requestData.getSessionAttributes().containsKey(AttributeName.REVIEW)) {
                reviewList = (List<Review>) requestData.getSessionAttribute(AttributeName.REVIEW);
            } else {
                reviewList = new ArrayList<>();
            }

            User user = (User) requestData.getSessionAttribute(AttributeName.USER);
            Optional<User> optionalUser = userService.findByLogin(user.getLogin());
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }

            List<User> users = (List<User>) requestData.getSessionAttribute(AttributeName.USERS);
            String login = user.getLogin();


            requestData.addRequestAttribute(AttributeName.RATING, reviewService.getAverageRating(filmId));
            if (!requestData.getRequestAttributeValues().containsKey(AttributeName.ERROR_REVIEW)) {
                Optional<Review> newReview = reviewService.findByFilmIdUserId(filmId, user.getId());
                newReview.ifPresent(reviewList::add);
            }

            requestData.addRequestAttribute(AttributeName.ID, filmId);

            List<Review> reviewListWithText = reviewList.stream().filter(review -> !review.getReview().isEmpty()).sorted().collect(Collectors.toList());
            requestData.addSessionAttribute(AttributeName.REVIEW, reviewListWithText);

            requestData.addRequestAttribute(AttributeName.APPRAISAL, reviewService.findAllByFilmId(filmId).size());


        } catch (ServiceException e) {
            LOGGER.error("Error adding comment", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.FILM_PAGE.getPath());
    }
}
