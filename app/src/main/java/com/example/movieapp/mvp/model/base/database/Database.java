package com.example.movieapp.mvp.model.base.database;

import androidx.room.RoomDatabase;

import com.example.movieapp.mvp.model.search.database.dao.SearchResultDao;
import com.example.movieapp.mvp.model.search.database.dao.SearchDao;
import com.example.movieapp.mvp.model.title.database.dao.TitleDao;
import com.example.movieapp.mvp.model.search.database.RoomSearch;
import com.example.movieapp.mvp.model.search.database.RoomSearchResult;
import com.example.movieapp.mvp.model.title.database.RoomTitle;

@androidx.room.Database(entities = {RoomSearch.class, RoomSearchResult.class, RoomTitle.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public static final String DB_NAME = "database.db";

    public abstract SearchDao searchDao();
    public abstract SearchResultDao searchResultDao();
    public abstract TitleDao titleDao();
}