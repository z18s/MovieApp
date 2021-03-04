package com.example.movieapp.mvp.model.search.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("Search")
    @Expose
    List<SearchResult> list;

    public Search(List<SearchResult> list) {
        this.list = list;
    }

    public List<SearchResult> getList() {
        return list;
    }

    public void setList(List<SearchResult> list) {
        this.list = list;
    }
}