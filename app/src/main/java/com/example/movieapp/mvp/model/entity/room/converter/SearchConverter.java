package com.example.movieapp.mvp.model.entity.room.converter;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchConverter {

    @TypeConverter
    public List<String> toIdList(String data) {
        return Arrays.asList(data.split(","));
    }

    @TypeConverter
    public String fromIdList(List<String> idList) {
        return idList.stream().collect(Collectors.joining(","));
    }
}