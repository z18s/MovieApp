package com.example.movieapp.mvp.model.title.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.movieapp.mvp.model.search.database.RoomSearchResult;

@Entity(tableName = "titles",
        foreignKeys = {@ForeignKey(
                entity = RoomSearchResult.class,
                parentColumns = "id",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE)})
public class RoomTitle {

    @PrimaryKey
    @NonNull
    public String id;

    public String name;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    public String type;

    public String year;

    public String country;

    public String director;

    public String rating;

    public String plot;

    public RoomTitle(@NonNull String id, String name, String imageUrl, String type, String year, String country, String director, String rating, String plot) {
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

    @NonNull
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