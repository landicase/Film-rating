package com.epam.rating.builder;

import com.epam.rating.builder.impl.*;

public class BuilderFactory {

        private static final UserBuilder userBuilder = new UserBuilder();
        private static final RoleBuilder roleBuilder = new RoleBuilder();
        private static final FilmBuilder filmBuilder = new FilmBuilder();
        private static final ReviewBuilder reviewBuilder = new ReviewBuilder();
        private static final CommentBuilder commentBuilder = new CommentBuilder();


        public static UserBuilder getUserBuilder() {
                return userBuilder;
        }

        public static RoleBuilder getRoleBuilder() {
                return roleBuilder;
        }

        public static FilmBuilder getFilmBuilder() {
                return filmBuilder;
        }

        public static ReviewBuilder getReviewBuilder() {
                return reviewBuilder;
        }

        public static CommentBuilder getCommentBuilder() {
                return commentBuilder;
        }
}
