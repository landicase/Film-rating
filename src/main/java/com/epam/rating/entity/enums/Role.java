package com.epam.rating.entity.enums;

import com.epam.rating.exception.UnknownEntityException;

import java.util.Locale;

public enum Role {
    ADMIN(1),
    USER(2);

    private final Integer id;

    Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Role resolveRoleById(Integer id) {
        Role[] values = values();
        for (Role userRole :
                values) {
            if (userRole.getId().equals(id)) return userRole;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }

}
