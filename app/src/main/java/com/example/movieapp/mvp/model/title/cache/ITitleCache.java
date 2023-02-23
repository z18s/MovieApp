package com.example.movieapp.mvp.model.title.cache;

import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.mvp.model.title.database.RoomFavorites;
import com.example.movieapp.mvp.model.title.database.RoomUserRatings;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ITitleCache {
    Single<Title> getTitle(String id);
    Completable putTitle(Title title);

    Single<List<RoomFavorites>> getFavorites();
    Single<Boolean> getFavoriteStatus(String id);
    Completable putFavorite(String id);
    Completable deleteFavorite(String id);
    Completable clearFavorites();

    Single<List<RoomUserRatings>> getUserRatings();
    Single<String> getUserRating(String id);
    Completable setUserRating(String id, String rating);
    Completable deleteUserRating(String id);
    Completable clearUserRatings();
}