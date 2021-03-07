package com.example.movieapp.mvp.model.title.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites_titles")
public class RoomFavorites {

    @PrimaryKey
    @NonNull
    public String id;

    public RoomFavorites(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }
}