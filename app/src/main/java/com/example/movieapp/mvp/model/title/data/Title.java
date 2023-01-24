package com.example.movieapp.mvp.model.title.data;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;
import static com.example.movieapp.mvp.model.base.SearchConstants.NO_RESULT;

public class Title {

    @SerializedName("imdbID")
    @Expose
    String id;

    @SerializedName("Title")
    @Expose
    String name;

    @SerializedName("Poster")
    @Expose
    String imageUrl;

    @SerializedName("Type")
    @Expose
    String type;

    @SerializedName("Year")
    @Expose
    String year;

    @SerializedName("Country")
    @Expose
    String country;

    @SerializedName("Director")
    @Expose
    String director;

    @SerializedName("imdbRating")
    @Expose
    String rating;

    @SerializedName("Plot")
    @Expose
    String plot;

    @SerializedName("FavoriteStatus")
    boolean favoriteStatus;

    @SerializedName("UserRating")
    String userRating;

    public Title(String id, String name, String imageUrl, String type, String year, String country, String director, String rating, String plot, boolean favoriteStatus, String userRating) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.year = year;
        this.country = country;
        this.director = director;
        this.rating = rating;
        this.plot = plot;
        this.favoriteStatus = favoriteStatus;
        this.userRating = userRating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public String getRating() {
        return rating;
    }

    public String getPlot() {
        return plot;
    }

    public boolean isFavorite() {
        return favoriteStatus;
    }

    public void setFavorite(boolean favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public static class EmptyTitle extends Title {

        public EmptyTitle() {
            super(EMPTY_STRING, NO_RESULT, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, false, EMPTY_STRING);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            EmptyTitle that = (EmptyTitle) obj;
            return (that.getId().equals(EMPTY_STRING) && that.getName().equals(NO_RESULT));
        }
    }
}