package com.example.movieapp.mvp.model.title.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieapp.mvp.model.title.database.RoomUserRatings;

import java.util.List;

@Dao
public interface UserRatingsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RoomUserRatings userRatings);

    @Insert
    void insert(List<RoomUserRatings> userRatingsList);

    @Update
    void update(RoomUserRatings userRatings);

    @Update
    void update(List<RoomUserRatings> userRatingsList);

    @Delete
    void delete(RoomUserRatings userRatings);

    @Delete
    void delete(List<RoomUserRatings> userRatingsList);

    @Query("DELETE FROM user_ratings_titles")
    void deleteAll();

    @Query("SELECT * FROM user_ratings_titles")
    List<RoomUserRatings> getAll();

    @Query("SELECT * FROM user_ratings_titles WHERE id = :id LIMIT 1")
    RoomUserRatings findById(String id);

    @Query("SELECT EXISTS(SELECT id FROM user_ratings_titles WHERE id = :id)")
    boolean isTitleExists(String id);
}