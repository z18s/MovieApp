package com.example.movieapp.mvp.model.repo.retrofit;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.cache.ITitleCache;
import com.example.movieapp.mvp.model.entity.DetailedTitle;
import com.example.movieapp.mvp.model.network.INetworkStatus;
import com.example.movieapp.mvp.model.repo.ITitleRepo;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitTitleRepo implements ITitleRepo {

    private static final String TAG = RetrofitTitleRepo.class.getSimpleName();

    private final IDataSource api;
    private final INetworkStatus networkStatus;
    private final ITitleCache cache;

    public RetrofitTitleRepo(IDataSource api, INetworkStatus networkStatus, ITitleCache cache) {
        this.api = api;
        this.networkStatus = networkStatus;
        this.cache = cache;
    }

    @Override
    public Single<DetailedTitle> getTitle(String id) {
        Logger.showLog(Logger.VERBOSE, TAG, "getTitle");

        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
            if (isOnline) {
                return api.getDetailedTitle(id).flatMap((title) -> {
                    return cache.putTitle(title).toSingleDefault(title);
                });
            } else {
                return cache.getTitle(id);
            }
        }).subscribeOn(Schedulers.io());
    }
}