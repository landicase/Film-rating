package com.epam.rating.command.impl.common;

import com.epam.rating.command.*;

public class LogoutCommand implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData request) {
        request.setInvalidated(true);
        return new CommandExecute(RouteType.REDIRECT, Destination.MAIN_REDIRECT.getPath());
    }
}
