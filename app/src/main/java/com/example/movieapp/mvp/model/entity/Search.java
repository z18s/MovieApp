package com.example.movieapp.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("Search")
    @Expose
    List<BasicTitle> list;

    public Search(List<BasicTitle> list) {
        this.list = list;
    }

    public List<BasicTitle> getList() {
        return list;
    }

    public void setList(List<BasicTitle> list) {
        this.list = list;
    }
}