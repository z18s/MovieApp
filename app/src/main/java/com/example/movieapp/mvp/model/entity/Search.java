package com.example.movieapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("Search")
    @Expose
    List<Title> list;

    public List<Title> getList() {
        return list;
    }
}