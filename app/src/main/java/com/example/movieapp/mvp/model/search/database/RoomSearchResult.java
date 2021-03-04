package com.example.movieapp.mvp.model.search.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_result")
public class RoomSearchResult {

    @PrimaryKey
    @NonNull
    public String id;

    public String name;

    public String type;

    public String year;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    public RoomSearchResult(@NonNull String id, String name, String type, String year, String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}