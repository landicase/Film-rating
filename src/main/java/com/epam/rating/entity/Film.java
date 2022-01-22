package com.epam.rating.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Film implements Serializable, Identifiable {
    private int id;
    private int productionYear;
    private String name;
    private String description;
    private double rating;
    private int reviewAmount;
    private String type;
    private String ageRating;
    private List<String> poster;
    private List<String> trailer;
    private List<String> genre;
    private List<String> countryOfOrigin;

    public Film(int id, int production_year, String name, String description, double film_rating, int age_rating, String type, int review_amount) {
    }

        public Film(int id, int productionYear, String name, String description,
                double rating, int reviewAmount, String type, String ageRating) {
        this.id = id;
        this.productionYear = productionYear;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.reviewAmount = reviewAmount;
        this.type = type;
        this.ageRating = ageRating;
    }
    @Override
    public Integer getId() {
    return id;
}

    public void setId(int id) {
        this.id = id;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewAmount() {
        return reviewAmount;
    }

    public void setReviewAmount(int reviewAmount) {
        this.reviewAmount = reviewAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public List<String> getPoster() {
        return poster;
    }

    public void setPoster(List<String> poster) {
        this.poster = poster;
    }

    public List<String> getTrailer() {
        return trailer;
    }

    public void setTrailer(List<String> trailer) {
        this.trailer = trailer;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public List<String> getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(List<String> countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && productionYear == film.productionYear && Double.compare(film.rating, rating) == 0 && reviewAmount == film.reviewAmount && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(type, film.type) && Objects.equals(ageRating, film.ageRating) && Objects.equals(poster, film.poster) && Objects.equals(trailer, film.trailer) && Objects.equals(genre, film.genre) && Objects.equals(countryOfOrigin, film.countryOfOrigin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productionYear, name, description, rating, reviewAmount, type, ageRating, poster, trailer, genre, countryOfOrigin);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", productionYear=" + productionYear +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", reviewAmount=" + reviewAmount +
                ", type='" + type + '\'' +
                ", ageRating='" + ageRating + '\'' +
                ", poster=" + poster +
                ", trailer=" + trailer +
                ", genre=" + genre +
                ", countryOfOrigin=" + countryOfOrigin +
                '}';
    }
}
