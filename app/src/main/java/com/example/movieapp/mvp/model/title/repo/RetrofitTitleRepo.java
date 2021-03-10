package com.example.movieapp.mvp.model.title.repo;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.api.IDataSource;
import com.example.movieapp.mvp.model.title.cache.ITitleCache;
import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.mvp.model.title.database.RoomFavorites;
import com.example.movieapp.utils.network.INetworkStatus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitTitleRepo implements ITitleRepo, ILogger {

    private final IDataSource api;
    private final INetworkStatus networkStatus;
    private final ITitleCache cache;

    public RetrofitTitleRepo(IDataSource api, INetworkStatus networkStatus, ITitleCache cache) {
        this.api = api;
        this.networkStatus = networkStatus;
        this.cache = cache;
    }

    @Override
    public Single<Title> getTitle(String id) {
        showVerboseLog(this, "getTitle");
        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
            if (isOnline) {
                return api.getTitle(id).flatMap((title) -> {
                    boolean favoriteStatus = getFavoriteStatus(id).blockingGet();
                    title.setFavorite(favoriteStatus);
                    return cache.putTitle(title).toSingleDefault(title);
                });
            } else {
                return cache.getTitle(id);
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Title>> getFavorites() {
        showVerboseLog(this, "getFavorites");
        return Single.fromCallable(() -> {
            List<RoomFavorites> roomFavorites = cache.getFavorites().blockingGet();
            List<Title> favoritesTitles = new ArrayList<>();

            for (RoomFavorites roomFavorite: roomFavorites) {
                String id = roomFavorite.getId();
                Title favoritesTitle = getTitle(id).blockingGet();
                favoritesTitles.add(favoritesTitle);
            }
            return favoritesTitles;
        });
    }

    @Override
    public Single<Boolean> getFavoriteStatus(String id) {
        showVerboseLog(this, "getFavoriteStatus");
        return cache.getFavoriteStatus(id);
    }

    @Override
    public Completable putFavorite(String id) {
        showVerboseLog(this, "putFavorite");
        return cache.putFavorite(id);
    }

    @Override
    public Completable deleteFavorite(String id) {
        showVerboseLog(this, "deleteFavorite");
        return cache.deleteFavorite(id);
    }
}