package com.example.movieapp.mvp.model.title.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.mvp.model.title.database.RoomTitle;

import java.util.List;

@Dao
public interface TitleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomTitle title);

    @Insert
    void insert(List<RoomTitle> titles);

    @Update
    void update(RoomTitle title);

    @Update
    void update(List<RoomTitle> titles);

    @Delete
    void delete(RoomTitle title);

    @Delete
    void delete(List<RoomTitle> titles);

    @Query("SELECT * FROM titles")
    List<RoomTitle> getAll();

    @Query("SELECT * FROM titles WHERE id = :id LIMIT 1")
    RoomTitle findById(String id);
}