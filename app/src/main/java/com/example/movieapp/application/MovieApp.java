package com.example.movieapp.application;

import android.app.Application;

import com.example.movieapp.di.AppComponent;
import com.example.movieapp.di.DaggerAppComponent;
import com.example.movieapp.di.module.AppModule;
import com.example.movieapp.di.search.SearchSubcomponent;
import com.example.movieapp.di.title.TitleSubcomponent;

public class MovieApp extends Application {

    public static MovieApp instance;

    private AppComponent appComponent;
    private SearchSubcomponent searchSubcomponent;
    private TitleSubcomponent titleSubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    private void initSearchSubcomponent() {
        AppComponent appComponent = this.appComponent;
        if (appComponent == null) {
            throw new IllegalStateException("AppComponent must be initialized");
        }
        this.searchSubcomponent = appComponent.searchSubcomponent();
    }

    private void initTitleSubcomponent() {
        SearchSubcomponent searchSubcomponent = this.searchSubcomponent;
        if (searchSubcomponent == null) {
            initSearchSubcomponent();
        }
        this.titleSubcomponent = searchSubcomponent.titleSubcomponent();
    }

    public void releaseSearchSubcomponent() {
        searchSubcomponent = null;
    }

    public void releaseTitleSubcomponent() {
        titleSubcomponent = null;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public SearchSubcomponent getSearchSubcomponent() {
        if (searchSubcomponent == null) {
            initSearchSubcomponent();
        }
        return searchSubcomponent;
    }

    public TitleSubcomponent getTitleSubcomponent() {
        if (titleSubcomponent == null) {
            initTitleSubcomponent();
        }
        return titleSubcomponent;
    }
}