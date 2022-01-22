package com.epam.rating.builder.impl;

import com.epam.rating.builder.Builder;
import com.epam.rating.entity.user.Role;
import com.epam.rating.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilder implements Builder<User> {

    private static final String ID_COLUMN = "id";
    private static final String LOGIN_COLUMN = "login";
    private static final String PASSWORD_COLUMN = "password";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String NICKNAME_COLUMN = "nickname";
    private static final String PHONE_NUMBER_COLUMN ="phone_number";
    private static final String EMAIL_COLUMN = "email";
    private static final String IS_BANNED_COLUMN = "is_banned";
    private static final String ROLE_COLUMN = "role";

    @Override
    public User build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        String login = resultSet.getString(LOGIN_COLUMN);
        String password = resultSet.getString(PASSWORD_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        String surname = resultSet.getString(SURNAME_COLUMN);
        String nickname = resultSet.getString(NICKNAME_COLUMN);
        String phoneNumber = resultSet.getString(PHONE_NUMBER_COLUMN);
        String email = resultSet.getString(EMAIL_COLUMN);
        boolean isBanned = resultSet.getBoolean(IS_BANNED_COLUMN);
        String roleValue = resultSet.getString(ROLE_COLUMN);
        Role role = Role.valueOf(roleValue.toUpperCase());
        return new User(id, login, password, name, surname, nickname, phoneNumber, email, isBanned, role);
    }
}