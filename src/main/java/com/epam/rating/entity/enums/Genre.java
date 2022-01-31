package com.epam.rating.entity.enums;

import com.epam.rating.entity.Identifiable;
import com.epam.rating.exception.UnknownEntityException;

public enum Genre {
    ADVENTURE(3),
    ANIMATION(4),
    BIOGRAPHY(5),
    CARTOON(1),
    COMEDY(6),
    CRIME(7),
    DOCUMENTARY(8),
    DRAMA(9),
    FAMILY(10),
    FANTASY(11),
    GAME_SHOW(12),
    HISTORY(13),
    HORROR(14),
    LIFESTYLE(15),
    MUSIC(16),
    MUSICAL(17),
    MYSTERY(18),
    NEWS(19),
    REALITY_TV(20),
    ROMANCE(21),
    SCI_FI(22),
    SHORT(23),
    SPORT(24),
    TALK_SHOW(25),
    THRILLER(26),
    WAR(27),
    WESTERN(28);

    private final Integer id;

    Genre(Integer id) {
        this.id = id;
    }

    public static Genre resolveGenreById(Integer id) {
        Genre[] values = values();
        for (Genre genre :
                values) {
            if (genre.getId().equals(id)) return genre;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }

    public static Genre resolveGenreByName(String name) {
        Genre[] values = values();
        for (Genre genre :
                values) {
            if (genre.toString().equals(name)) return genre;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return name();
    }
}
