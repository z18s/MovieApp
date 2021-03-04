package com.example.movieapp.mvp.model.search.cache;

import com.example.movieapp.mvp.model.search.data.Search;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ISearchCache {
    Single<Search> getSearch(String query);
    Completable putSearch(String query, Search search);
}