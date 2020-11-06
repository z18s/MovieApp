package com.example.movieapp.mvp.model.entity.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "basic_titles")
public class RoomBasicTitle {

    @PrimaryKey
    @NonNull
    public String id;

    public String name;

    public String type;

    public String year;

    @ColumnInfo(name = "image_url")
    public String imageUrl;

    public RoomBasicTitle(@NonNull String id, String name, String type, String year, String imageUrl) {
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