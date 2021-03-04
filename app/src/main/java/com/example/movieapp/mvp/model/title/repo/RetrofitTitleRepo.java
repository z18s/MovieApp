package com.example.movieapp.mvp.model.title.repo;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.api.IDataSource;
import com.example.movieapp.mvp.model.title.cache.ITitleCache;
import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.utils.network.INetworkStatus;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitTitleRepo implements ITitleRepo, ILogger {

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
    public Single<Title> getTitle(String id) {
        showVerboseLog(TAG, "getTitle");

        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
            if (isOnline) {
                return api.getTitle(id).flatMap((title) -> {
                    return cache.putTitle(title).toSingleDefault(title);
                });
            } else {
                return cache.getTitle(id);
            }
        }).subscribeOn(Schedulers.io());
    }
}