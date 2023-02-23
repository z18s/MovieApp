package com.example.movieapp.mvp.model.search.cache;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.database.Database;
import com.example.movieapp.mvp.model.search.database.RoomSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomHistoryCache implements IHistoryCache, ILogger {

    private final Database db;

    public RoomHistoryCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<List<String>> getSearch(String chars) {
        showVerboseLog(this, "getSearch");
        return Single.fromCallable(() -> {

            List<RoomSearch> roomSearchList = db.searchDao().findByChars(chars + "%");
            List<String> searchQueryList = new ArrayList<>();

            for (RoomSearch roomSearch : roomSearchList) {
                String searchQuery = roomSearch.query;
                searchQueryList.add(searchQuery);
            }

            return searchQueryList;
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable clear() {
        showVerboseLog(this, "clear");
        return Completable.fromAction(() -> {
            db.searchDao().deleteAll();
        }).subscribeOn(Schedulers.io());
    }
}