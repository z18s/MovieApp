package com.example.movieapp.mvp.model.search.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.mvp.model.search.database.RoomSearchResult;

import java.util.List;

@Dao
public interface SearchResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomSearchResult title);

    @Insert
    void insert(List<RoomSearchResult> titles);

    @Update
    void update(RoomSearchResult title);

    @Update
    void update(List<RoomSearchResult> titles);

    @Delete
    void delete(RoomSearchResult title);

    @Delete
    void delete(List<RoomSearchResult> titles);

    @Query("SELECT * FROM search_result")
    List<RoomSearchResult> getAll();

    @Query("SELECT * FROM search_result WHERE id = :id")
    RoomSearchResult findById(String id);
}