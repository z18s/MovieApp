package com.example.movieapp.di.titles;

import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.repo.ITitlesRepo;
import com.example.movieapp.mvp.model.repo.retrofit.RetrofitTitlesRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class TitlesModule {

    @Provides
    ITitlesRepo titlesRepo(IDataSource api) {
        return new RetrofitTitlesRepo(api);
    }
}