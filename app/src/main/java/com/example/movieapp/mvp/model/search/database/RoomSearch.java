package com.example.movieapp.mvp.model.search.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.movieapp.mvp.model.search.database.converter.SearchConverter;

import java.util.List;

@Entity(tableName = "searches")
public class RoomSearch {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "search_query")
    public String query;

    @TypeConverters({SearchConverter.class})
    @ColumnInfo(name = "titles_id")
    public List<String> idList;

    public RoomSearch(@NonNull String query, List<String> idList) {
        this.query = query;
        this.idList = idList;
    }

    @NonNull
    public String getQuery() {
        return query;
    }

    public List<String> getIdList() {
        return idList;
    }
}