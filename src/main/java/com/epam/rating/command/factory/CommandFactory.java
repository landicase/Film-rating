package com.epam.rating.command.factory;

import com.epam.rating.command.AttributeName;
import com.epam.rating.command.CommandRequest;
import com.epam.rating.command.CommandType;
import com.epam.rating.command.RequestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Optional;


public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public Optional<CommandRequest> defineCommand(RequestData requestData) {
        Optional<CommandRequest> optionalCommand = Optional.empty();
        try {
            String command = requestData.getRequestParameter(AttributeName.COMMAND);
            CommandType commandType = CommandType.valueOf(upper(command));
            optionalCommand = Optional.of(commandType.getCommand());
        } catch (IllegalArgumentException | NullPointerException e) {
            requestData.addRequestAttribute(AttributeName.ERROR, "error.message.400");
            LOGGER.error("Exception while command define", e);
        }
        return optionalCommand;
    }

    public static String upper(String name) {
        return name.replace("-", "_").toUpperCase(Locale.ROOT);
    }
}