package com.example.movieapp.mvp.model.search.cache;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IHistoryCache {
    Single<List<String>> getSearch(String chars);
    Completable clear();
}