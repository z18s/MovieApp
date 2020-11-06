package com.example.movieapp.mvp.model.cache;

import com.example.movieapp.mvp.model.entity.Search;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ISearchCache {
    Single<Search> getSearch(String query);
    Completable putSearch(String query, Search search);
}