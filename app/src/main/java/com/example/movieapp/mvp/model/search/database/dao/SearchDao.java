package com.example.movieapp.mvp.model.search.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.mvp.model.search.database.RoomSearch;

import java.util.List;

@Dao
public interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomSearch search);

    @Insert
    void insert(List<RoomSearch> search);

    @Update
    void update(RoomSearch search);

    @Update
    void update(List<RoomSearch> search);

    @Delete
    void delete(RoomSearch search);

    @Delete
    void delete(List<RoomSearch> search);

    @Query("SELECT * FROM searches")
    List<RoomSearch> getAll();

    @Query("SELECT * FROM searches WHERE search_query = :query")
    RoomSearch findByQuery(String query);

    @Query("SELECT * FROM searches WHERE search_query LIKE :chars ORDER BY search_query")
    List<RoomSearch> findByChars(String chars);
}