package com.example.movieapp.di.title;

import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.cache.ITitleCache;
import com.example.movieapp.mvp.model.cache.room.RoomTitleCache;
import com.example.movieapp.mvp.model.entity.room.Database;
import com.example.movieapp.mvp.model.network.INetworkStatus;
import com.example.movieapp.mvp.model.repo.ITitleRepo;
import com.example.movieapp.mvp.model.repo.retrofit.RetrofitTitleRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class TitleModule {

    @Provides
    ITitleRepo titleRepo(IDataSource api, INetworkStatus networkStatus, ITitleCache cache) {
        return new RetrofitTitleRepo(api, networkStatus, cache);
    }

    @Provides
    ITitleCache titleCache(Database db) {
        return new RoomTitleCache(db);
    }
}