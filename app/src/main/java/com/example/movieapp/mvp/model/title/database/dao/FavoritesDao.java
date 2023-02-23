package com.example.movieapp.mvp.model.title.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.mvp.model.title.database.RoomFavorites;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RoomFavorites favorites);

    @Insert
    void insert(List<RoomFavorites> favoritesList);

    @Update
    void update(RoomFavorites favorites);

    @Update
    void update(List<RoomFavorites> favoritesList);

    @Delete
    void delete(RoomFavorites favorites);

    @Delete
    void delete(List<RoomFavorites> favoritesList);

    @Query("DELETE FROM favorites_titles")
    void deleteAll();

    @Query("SELECT * FROM favorites_titles")
    List<RoomFavorites> getAll();

    @Query("SELECT * FROM favorites_titles WHERE id = :id LIMIT 1")
    RoomFavorites findById(String id);

    @Query("SELECT EXISTS(SELECT id FROM favorites_titles WHERE id = :id)")
    boolean isTitleExists(String id);
}