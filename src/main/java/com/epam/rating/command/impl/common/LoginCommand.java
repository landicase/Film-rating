package com.epam.rating.command.impl.common;

import com.epam.rating.command.*;
import com.epam.rating.entity.User;
import com.epam.rating.entity.enums.Role;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.UserService;
import com.epam.rating.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
        try {
            String login = requestData.getRequestParameter(AttributeName.LOGIN).trim();
            String password = requestData.getRequestParameter(AttributeName.PASSWORD).trim();

            if (userService.login(login, password)) {
                Optional<User> optionalUser = userService.findByLogin(login);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    requestData.addSessionAttribute(AttributeName.USER, user);
                    requestData.addSessionAttribute(AttributeName.ROLE, user.getRole().getId());
                    if (user.getRole() == Role.ADMIN) {
                        requestData.addSessionAttribute(AttributeName.USERS_L, userService.findAll());
                        commandExecute.setPagePath(Destination.USERS.getPath());
                    }
                }
            } else {
                requestData.addRequestAttribute(AttributeName.ERROR_SIGN_IN, "error.message.login");
                commandExecute.setPagePath(Destination.LOGIN.getPath());
                commandExecute.setRouteType(RouteType.FORWARD);
            }
            return commandExecute;
        } catch (ServiceException e) {
            LOGGER.error("Error while trying to login", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
    }
}
