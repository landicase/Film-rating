package com.epam.rating.command.impl.admin;


import com.epam.rating.command.*;
import com.epam.rating.entity.User;
import com.epam.rating.exception.ServiceException;
import com.epam.rating.service.UserService;
import com.epam.rating.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowUsersCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(ShowUsersCommand.class);
    private static final UserService userService = new UserServiceImpl();


    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.USERS.getPath());
        try {
            List<User> userList = userService.findAll();
            if (!userList.isEmpty()) {
                requestData.addSessionAttribute(AttributeName.USERS_L, userList);
            } else {
                commandExecute.setPagePath(Destination.ERROR.getPath());
                requestData.addRequestAttribute(AttributeName.ERROR, "error.message.400");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error loading user list", e);
            commandExecute.setPagePath(Destination.ERROR.getPath());
            return commandExecute;
        }
        return commandExecute;
    }
}
