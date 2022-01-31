package com.epam.rating.entity;


import com.epam.rating.entity.enums.Role;

import java.io.Serializable;
import java.util.Objects;

public class User extends AbstractIdentifiable implements Serializable {


    private String login;

    private String password;

    private String name;

    private String surname;

    private Role role;

    private Double rating;

    public User() {};

    private User(Builder builder) {
        this.login = Objects.requireNonNull(builder.login, "login");
        this.password = Objects.requireNonNull(builder.password, "password");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.surname = Objects.requireNonNull(builder.surname, "surname");
        this.role = Objects.requireNonNull(builder.role, "role");
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {return surname;}

    public Role getRole() {
        return role;
    }

    public static class Builder extends User {
        private String login;
        private String password;
        private String name;
        private String surname;
        private Role role;
        private String email;

        private Builder() {
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder of(User user) {
            this.login = user.login;
            this.password = user.password;
            this.name = user.name;
            this.surname = user.surname;
            this.role = user.role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) && password.equals(user.password) && name.equals(user.name) && surname.equals(user.surname) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, surname, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                '}';
    }
}

