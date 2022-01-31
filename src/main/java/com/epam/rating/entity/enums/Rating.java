package com.epam.rating.entity.enums;

import com.epam.rating.exception.UnknownEntityException;

public enum Rating {
    AWESOME(5),
    GOOD(4),
    MEH(3),
    BAD(2),
    AWFUL(1);

    private final int stars;

    Rating(int stars) {
        this.stars = stars;
    }

    public Integer getId() {
        return stars;
    }

    public static Rating resolveGenreById(Integer id) {
        Rating[] values = values();
        for (Rating appraisal :
                values) {
            if (appraisal.getId().equals(id)) return appraisal;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }

}
