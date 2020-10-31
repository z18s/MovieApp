package com.example.movieapp.di.module;

import com.example.movieapp.MovieApp;

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
}