package com.example.movieapp.mvp.model.title.cache;

import com.example.movieapp.mvp.model.title.data.Title;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ITitleCache {
    Single<Title> getTitle(String id);
    Completable putTitle(Title title);

    Single<List<Title>> getFavorites();
    Single<Boolean> getFavoriteStatus(String id);
    Completable putFavorite(String id);
    Completable deleteFavorite(String id);
}