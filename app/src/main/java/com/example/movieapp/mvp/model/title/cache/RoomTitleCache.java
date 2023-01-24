package com.example.movieapp.mvp.model.title.cache;

import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.title.database.RoomFavorites;
import com.example.movieapp.mvp.model.title.database.RoomTitle;
import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.mvp.model.base.database.Database;
import com.example.movieapp.mvp.model.title.database.RoomUserRatings;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomTitleCache implements ITitleCache, ILogger {

    private final Database db;

    public RoomTitleCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<Title> getTitle(String id) {
        return Single.fromCallable(() -> {
            showVerboseLog(this, "getTitle");

            RoomTitle roomTitle = db.titleDao().findById(id);
            Boolean favoriteStatus = getFavoriteStatus(id).blockingGet();
            String userRating = getUserRating(id).blockingGet();

            return new Title(
                    roomTitle.getId(),
                    roomTitle.getName(),
                    roomTitle.getImageUrl(),
                    roomTitle.getType(),
                    roomTitle.getYear(),
                    roomTitle.getCountry(),
                    roomTitle.getDirector(),
                    roomTitle.getRating(),
                    roomTitle.getPlot(),
                    favoriteStatus,
                    userRating
            );
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable putTitle(Title title) {
        return Completable.fromAction(() -> {
            showVerboseLog(this, "putTitle");

            db.titleDao().insert(new RoomTitle(
                    title.getId(),
                    title.getName(),
                    title.getImageUrl(),
                    title.getType(),
                    title.getYear(),
                    title.getCountry(),
                    title.getDirector(),
                    title.getRating(),
                    title.getPlot()
            ));
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<RoomFavorites>> getFavorites() {
        return Single.fromCallable(() -> {
            showVerboseLog(this, "getFavorites");
            return db.favoritesDao().getAll();
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> getFavoriteStatus(String id) {
        return Single.fromCallable(() -> {
            showVerboseLog(this, "getFavoriteStatus");
            return db.favoritesDao().isTitleExists(id);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable putFavorite(String id) {
        return Completable.fromAction(() -> {
            showVerboseLog(this, "putFavorite");
            db.favoritesDao().insert(new RoomFavorites(id));
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteFavorite(String id) {
        return Completable.fromAction(() -> {
            showVerboseLog(this, "deleteFavorite");
            RoomFavorites roomFavorites = new RoomFavorites(id);
            db.favoritesDao().delete(roomFavorites);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<RoomUserRatings>> getUserRatings() {
        return Single.fromCallable(() -> {
            showVerboseLog(this, "getUserRatings");
            return db.userRatingsDao().getAll();
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<String> getUserRating(String id) {
        return Single.fromCallable(() -> {
            showVerboseLog(this, "getUserRating");
            return (db.userRatingsDao().isTitleExists(id)) ? db.userRatingsDao().findById(id).rating : EMPTY_STRING;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setUserRating(String id, String rating) {
        return Completable.fromAction(() -> {
            showVerboseLog(this, "setUserRating");
            db.userRatingsDao().insert(new RoomUserRatings(id, rating));
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteUserRating(String id) {
        return Completable.fromAction(() -> {
            showVerboseLog(this, "deleteUserRating");
            RoomUserRatings roomUserRatings = new RoomUserRatings(id);
            db.userRatingsDao().delete(roomUserRatings);
        }).subscribeOn(Schedulers.io());
    }
}