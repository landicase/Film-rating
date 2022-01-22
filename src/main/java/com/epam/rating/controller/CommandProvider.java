package com.epam.rating.controller;

import com.epam.rating.controller.impl.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put("login", new Login());
        commands.put("Registration", new Registration());
        commands.put("gotolog", new GoToLoginPage());
        commands.put("gotoreg", new GoToRegistrationPage());
        commands.put("changeLanguage", new ChangeLanguage());
        commands.put("mainpage", new GoToMainPage());
        commands.put("show", new FindFilmsByParameters());
        commands.put("gotofilm", new GoToFilm());
    }

    public final Command getCommand(String commandName) {
        Command command = commands.get(commandName);
        return command;
    }
}
