package com.example.movieapp.di.title;

import com.example.movieapp.mvp.model.base.api.IDataSource;
import com.example.movieapp.mvp.model.title.cache.ITitleCache;
import com.example.movieapp.mvp.model.title.cache.RoomTitleCache;
import com.example.movieapp.mvp.model.base.database.Database;
import com.example.movieapp.utils.network.INetworkStatus;
import com.example.movieapp.mvp.model.title.repo.ITitleRepo;
import com.example.movieapp.mvp.model.title.repo.RetrofitTitleRepo;

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