package com.example.movieapp.di.search;

import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.cache.ISearchCache;
import com.example.movieapp.mvp.model.cache.room.RoomSearchCache;
import com.example.movieapp.mvp.model.entity.room.Database;
import com.example.movieapp.mvp.model.network.INetworkStatus;
import com.example.movieapp.mvp.model.repo.ISearchRepo;
import com.example.movieapp.mvp.model.repo.retrofit.RetrofitSearchRepo;

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
}