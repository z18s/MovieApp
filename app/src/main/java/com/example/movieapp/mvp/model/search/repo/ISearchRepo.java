package com.example.movieapp.mvp.model.search.repo;

import com.example.movieapp.mvp.model.search.data.Search;

import io.reactivex.rxjava3.core.Single;

public interface ISearchRepo {
    Single<Search> getSearch(String query);
}