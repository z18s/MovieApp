package com.example.movieapp.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.mvp.model.base.TagConstants;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;

@Module
public class AppModule {

    private final MovieApp app;

    public AppModule(MovieApp app) {
        this.app = app;
    }

    @Provides
    public MovieApp app() {
        return app;
    }

    @Provides
    public Scheduler mainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    public SharedPreferences appSharedPreferences() {
        return app.getSharedPreferences(TagConstants.SETTINGS_TAG, Context.MODE_PRIVATE);
    }
}