package com.epam.rating.tag;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * The tag is used to form pagination on the page
 * the default number of items per page is 3
 * when displaying films - 1 item is displayed separately.
 */

public class PaginationTag extends SimpleTagSupport {

    private static final int entitiesPerPage = 3;

    private Integer reviewOnProfileListSize;

    private Integer filmListSize;

    private Integer filmReviewListSize;

    public void setReviewOnProfileListSize(int reviewOnProfileListSize) {
        this.reviewOnProfileListSize = reviewOnProfileListSize;
    }

    public void setFilmListSize(Integer filmListSize) {
        this.filmListSize = filmListSize;
    }

    public void setFilmReviewListSize(Integer filmReviewListSize) {
        this.filmReviewListSize = filmReviewListSize;
    }

    @Override
    public void doTag() {
        if (reviewOnProfileListSize != null) {
            try {
                int pages = reviewOnProfileListSize / entitiesPerPage;
                if (pages * entitiesPerPage + 1 == reviewOnProfileListSize) {
                    pages++;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < pages + 1; i++) {
                    int k = (i - 1) * entitiesPerPage;
                    stringBuilder.append("<a href=\"/film_rating_2.0_war_exploded/controller?command=view-user-profile&page=")
                            .append(k)
                            .append("\">")
                            .append(i)
                            .append("</a>")
                            .append("\n");
                }

                getJspContext().getOut().write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (filmReviewListSize != null) {
            try {
                int pages = filmReviewListSize / entitiesPerPage;
                if (pages * entitiesPerPage + 1 <= filmReviewListSize) {
                    pages++;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < pages + 1; i++) {
                    int k = (i - 1) * entitiesPerPage;
                    stringBuilder.append("<a href=\"/jwdFilms_war_exploded/controller?command=film-info&page=")
                            .append(k)
                            .append("\">")
                            .append(i)
                            .append("</a>")
                            .append("\n");
                }

                getJspContext().getOut().write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (filmListSize != null) {
            try {
                int pages = (filmListSize - 1) / entitiesPerPage;
                if (pages * entitiesPerPage + 1 <= (filmListSize - 1)) {
                    pages++;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < pages + 1; i++) {
                    int k = (i - 1) * entitiesPerPage;
                    stringBuilder.append("<a href=\"/jwdFilms_war_exploded/controller?command=main&page=")
                            .append(k)
                            .append("\">")
                            .append(i)
                            .append("</a>")
                            .append("\n");
                }

                getJspContext().getOut().write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
