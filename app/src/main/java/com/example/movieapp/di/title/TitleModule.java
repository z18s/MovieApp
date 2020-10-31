package com.example.movieapp.di.title;

import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.repo.ITitleRepo;
import com.example.movieapp.mvp.model.repo.retrofit.RetrofitTitleRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class TitleModule {

    @Provides
    ITitleRepo titleRepo(IDataSource api) {
        return new RetrofitTitleRepo(api);
    }
}