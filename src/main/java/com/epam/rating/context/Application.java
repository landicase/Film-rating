package com.epam.rating.context;

import com.epam.rating.context.impl.FilmRatingsContext;

public interface Application {
    static void start() {
        final ApplicationContext filmRatingsContext = new FilmRatingsContext();
        filmRatingsContext.init();
    }
}