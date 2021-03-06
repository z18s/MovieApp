package com.example.movieapp.mvp.model.search.repo;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.api.IDataSource;
import com.example.movieapp.mvp.model.search.cache.ISearchCache;
import com.example.movieapp.mvp.model.search.data.Search;
import com.example.movieapp.utils.network.INetworkStatus;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitSearchRepo implements ISearchRepo, ILogger {

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
        showVerboseLog(this, "getSearch");

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