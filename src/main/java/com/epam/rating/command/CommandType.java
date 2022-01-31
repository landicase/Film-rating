package com.epam.rating.command;

import com.epam.rating.command.impl.admin.*;
import com.epam.rating.command.impl.common.*;

public enum CommandType {
    MAIN(new MainCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    FILM_INFO(new ViewFilmInfoCommand()),
    FILM_SEARCH(new FindFilmCommand()),
    SORT_FILM_BY_GENRE(new SortFilmByGenre()),
    LEAVE_REVIEW(new LeaveReviewCommand()),
    VIEW_USER_PROFILE(new ViewUserProfile()),
    DELETE_USER_REVIEW(new DeleteUserReview()),
    CHANGE_LANGUAGE(new ChangeLanguage());

    CommandRequest command;

    CommandType(CommandRequest command) {
        this.command = command;
    }

    public CommandRequest getCommand() {
        return command;
    }
}
