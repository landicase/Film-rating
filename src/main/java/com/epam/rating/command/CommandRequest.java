package com.epam.rating.command;

public interface CommandRequest {
    CommandExecute executeCommand(RequestData requestData);
}