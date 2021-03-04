package com.example.movieapp.mvp.model.title.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public Title(String id, String name, String imageUrl, String type, String year, String country, String director, String rating, String plot) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.year = year;
        this.country = country;
        this.director = director;
        this.rating = rating;
        this.plot = plot;
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
}