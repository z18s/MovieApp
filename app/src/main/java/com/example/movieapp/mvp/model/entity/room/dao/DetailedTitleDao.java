package com.example.movieapp.mvp.model.entity.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.mvp.model.entity.room.RoomDetailedTitle;

import java.util.List;

@Dao
public interface DetailedTitleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomDetailedTitle title);

    @Insert
    void insert(List<RoomDetailedTitle> titles);

    @Update
    void update(RoomDetailedTitle title);

    @Update
    void update(List<RoomDetailedTitle> titles);

    @Delete
    void delete(RoomDetailedTitle title);

    @Delete
    void delete(List<RoomDetailedTitle> titles);

    @Query("SELECT * FROM detailed_titles")
    List<RoomDetailedTitle> getAll();

    @Query("SELECT * FROM detailed_titles WHERE id = :id LIMIT 1")
    RoomDetailedTitle findById(String id);
}