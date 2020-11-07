package com.example.movieapp.mvp.model.repo.retrofit;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.cache.ISearchCache;
import com.example.movieapp.mvp.model.entity.Search;
import com.example.movieapp.mvp.model.network.INetworkStatus;
import com.example.movieapp.mvp.model.repo.ISearchRepo;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitSearchRepo implements ISearchRepo, ILogger {

    private static final String TAG = RetrofitSearchRepo.class.getSimpleName();

    private final IDataSource api;
    private final INetworkStatus networkStatus;
    private final ISearchCache cache;

    public RetrofitSearchRepo(IDataSource api, INetworkStatus networkStatus, ISearchCache cache) {
        this.api = api;
        this.networkStatus = networkStatus;
        this.cache = cache;
    }

    @Override
    public Single<Search> getSearch(String query) {
        showVerboseLog(TAG, "getSearch");

        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
            if (isOnline) {
                return api.getSearch(query).flatMap((search) -> {
                    return cache.putSearch(query, search).toSingleDefault(search);
                });
            } else {
                return cache.getSearch(query);
            }
        }).subscribeOn(Schedulers.io());
    }
}