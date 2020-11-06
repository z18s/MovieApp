package com.example.movieapp.mvp.model.repo;

import com.example.movieapp.mvp.model.entity.Search;

import io.reactivex.rxjava3.core.Single;

public interface ISearchRepo {
    Single<Search> getSearch(String query);
}