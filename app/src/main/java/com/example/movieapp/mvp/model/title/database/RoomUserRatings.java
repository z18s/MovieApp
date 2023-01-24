package com.example.movieapp.mvp.model.title.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_ratings_titles")
public class RoomUserRatings {

    @PrimaryKey
    @NonNull
    public String id;
    public String rating;

    public RoomUserRatings(@NonNull String id, String rating) {
        this.id = id;
        this.rating = rating;
    }

    @Ignore
    public RoomUserRatings(@NonNull String id) {
        this.id = id;
        this.rating = null;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getRating() {
        return rating;
    }
}