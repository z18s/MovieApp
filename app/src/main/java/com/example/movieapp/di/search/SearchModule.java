package com.example.movieapp.di.search;

import com.example.movieapp.mvp.model.base.api.IDataSource;
import com.example.movieapp.mvp.model.search.cache.IHistoryCache;
import com.example.movieapp.mvp.model.search.cache.ISearchCache;
import com.example.movieapp.mvp.model.search.cache.RoomHistoryCache;
import com.example.movieapp.mvp.model.search.cache.RoomSearchCache;
import com.example.movieapp.mvp.model.base.database.Database;
import com.example.movieapp.utils.network.INetworkStatus;
import com.example.movieapp.mvp.model.search.repo.ISearchRepo;
import com.example.movieapp.mvp.model.search.repo.RetrofitSearchRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    ISearchRepo searchRepo(IDataSource api, INetworkStatus networkStatus, ISearchCache cache) {
        return new RetrofitSearchRepo(api, networkStatus, cache);
    }

    @Provides
    ISearchCache searchCache(Database db) {
        return new RoomSearchCache(db);
    }

    @Provides
    IHistoryCache historyCache(Database db) {
        return new RoomHistoryCache(db);
    }
}