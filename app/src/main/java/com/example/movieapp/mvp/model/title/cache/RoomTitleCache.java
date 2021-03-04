package com.example.movieapp.mvp.model.title.cache;

import com.example.movieapp.mvp.model.title.database.RoomTitle;
import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.mvp.model.base.database.Database;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomTitleCache implements ITitleCache {

    private final Database db;

    public RoomTitleCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<Title> getTitle(String id) {
        return Single.fromCallable(() -> {

            RoomTitle roomTitle = db.titleDao().findById(id);

            return new Title(
                    roomTitle.getId(),
                    roomTitle.getName(),
                    roomTitle.getImageUrl(),
                    roomTitle.getType(),
                    roomTitle.getYear(),
                    roomTitle.getCountry(),
                    roomTitle.getDirector(),
                    roomTitle.getRating(),
                    roomTitle.getPlot()
            );
        });
    }

    @Override
    public Completable putTitle(Title title) {
        return Completable.fromAction(() -> {

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
}