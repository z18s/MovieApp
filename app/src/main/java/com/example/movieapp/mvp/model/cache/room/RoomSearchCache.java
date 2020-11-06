package com.example.movieapp.mvp.model.cache.room;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.model.cache.ISearchCache;
import com.example.movieapp.mvp.model.entity.BasicTitle;
import com.example.movieapp.mvp.model.entity.Search;
import com.example.movieapp.mvp.model.entity.room.Database;
import com.example.movieapp.mvp.model.entity.room.RoomBasicTitle;
import com.example.movieapp.mvp.model.entity.room.RoomSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomSearchCache implements ISearchCache {

    private static final String TAG = RoomSearchCache.class.getSimpleName();

    private final Database db;

    public RoomSearchCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<Search> getSearch(String query) {
        Logger.showLog(Logger.VERBOSE, TAG, "getSearch");
        return Single.fromCallable(() -> {

            RoomSearch roomSearch = db.searchDao().findByQuery(query);
            List<String> ids = roomSearch.getIdList();

            List<BasicTitle> basicTitles = new ArrayList<>();

            for (String id : ids) {
                BasicTitle basicTitle = getTitle(id);
                basicTitles.add(basicTitle);
            }

            return new Search(basicTitles);
        });
    }

    @Override
    public Completable putSearch(String query, Search search) {
        Logger.showLog(Logger.VERBOSE, TAG, "putSearch");
        return Completable.fromAction(() -> {

            List<String> ids = new ArrayList<>();

            List<BasicTitle> basicTitles = search.getList();

            for (BasicTitle basicTitle : basicTitles) {
                String id = basicTitle.getId();
                ids.add(id);

                putTitle(basicTitle);
            }

            db.searchDao().insert(new RoomSearch(query, ids));
        }).subscribeOn(Schedulers.io());
    }

    private BasicTitle getTitle(String id) {
        Logger.showLog(Logger.VERBOSE, TAG, "getTitle");

            RoomBasicTitle roomBasicTitle = db.basicTitleDao().findById(id);

            return new BasicTitle(
                    roomBasicTitle.getId(),
                    roomBasicTitle.getName(),
                    roomBasicTitle.getType(),
                    roomBasicTitle.getYear(),
                    roomBasicTitle.getImageUrl()
            );
    }

    private void putTitle(BasicTitle basicTitle) {
        Logger.showLog(Logger.VERBOSE, TAG, "putTitle");

        db.basicTitleDao().insert(new RoomBasicTitle(
                basicTitle.getId(),
                basicTitle.getNameString(),
                basicTitle.getType(),
                basicTitle.getYear(),
                basicTitle.getImageUrl()
        ));
    }
}