package com.example.movieapp.mvp.model.entity.room;

import androidx.room.RoomDatabase;

import com.example.movieapp.mvp.model.entity.room.dao.BasicTitleDao;
import com.example.movieapp.mvp.model.entity.room.dao.SearchDao;
import com.example.movieapp.mvp.model.entity.room.dao.DetailedTitleDao;

@androidx.room.Database(entities = {RoomSearch.class, RoomBasicTitle.class, RoomDetailedTitle.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public static final String DB_NAME = "database.db";

    public abstract SearchDao searchDao();
    public abstract BasicTitleDao basicTitleDao();
    public abstract DetailedTitleDao detailedTitleDao();
}