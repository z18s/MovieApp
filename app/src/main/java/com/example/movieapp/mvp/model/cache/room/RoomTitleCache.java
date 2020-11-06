package com.example.movieapp.mvp.model.cache.room;

import com.example.movieapp.mvp.model.cache.ITitleCache;
import com.example.movieapp.mvp.model.entity.DetailedTitle;
import com.example.movieapp.mvp.model.entity.room.Database;
import com.example.movieapp.mvp.model.entity.room.RoomDetailedTitle;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomTitleCache implements ITitleCache {

    private final Database db;

    public RoomTitleCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<DetailedTitle> getTitle(String id) {
        return Single.fromCallable(() -> {

            RoomDetailedTitle roomDetailedTitle = db.detailedTitleDao().findById(id);

            return new DetailedTitle(
                    roomDetailedTitle.getId(),
                    roomDetailedTitle.getName(),
                    roomDetailedTitle.getImageUrl(),
                    roomDetailedTitle.getType(),
                    roomDetailedTitle.getYear(),
                    roomDetailedTitle.getCountry(),
                    roomDetailedTitle.getDirector(),
                    roomDetailedTitle.getRating(),
                    roomDetailedTitle.getPlot()
            );
        });
    }

    @Override
    public Completable putTitle(DetailedTitle detailedTitle) {
        return Completable.fromAction(() -> {

            db.detailedTitleDao().insert(new RoomDetailedTitle(
                    detailedTitle.getId(),
                    detailedTitle.getName(),
                    detailedTitle.getImageUrl(),
                    detailedTitle.getType(),
                    detailedTitle.getYear(),
                    detailedTitle.getCountry(),
                    detailedTitle.getDirector(),
                    detailedTitle.getRating(),
                    detailedTitle.getPlot()
            ));
        }).subscribeOn(Schedulers.io());
    }
}