package com.example.movieapp.mvp.model.entity.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.mvp.model.entity.room.RoomBasicTitle;

import java.util.List;

@Dao
public interface BasicTitleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomBasicTitle title);

    @Insert
    void insert(List<RoomBasicTitle> titles);

    @Update
    void update(RoomBasicTitle title);

    @Update
    void update(List<RoomBasicTitle> titles);

    @Delete
    void delete(RoomBasicTitle title);

    @Delete
    void delete(List<RoomBasicTitle> titles);

    @Query("SELECT * FROM basic_titles")
    List<RoomBasicTitle> getAll();

    @Query("SELECT * FROM basic_titles WHERE id = :id")
    RoomBasicTitle findById(String id);
}