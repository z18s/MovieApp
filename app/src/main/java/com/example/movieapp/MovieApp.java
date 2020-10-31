package com.example.movieapp;

import android.app.Application;

import com.example.movieapp.di.AppComponent;
import com.example.movieapp.di.DaggerAppComponent;
import com.example.movieapp.di.module.AppModule;
import com.example.movieapp.di.search.SearchSubcomponent;
import com.example.movieapp.di.title.TitleSubcomponent;
import com.example.movieapp.di.titles.TitlesSubcomponent;

public class MovieApp extends Application {

    public static MovieApp instance;

    private AppComponent appComponent;
    private SearchSubcomponent searchSubcomponent;
    private TitlesSubcomponent titlesSubcomponent;
    private TitleSubcomponent titleSubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public void initSearchSubcomponent() {
        AppComponent appComponent = this.appComponent;
        if (appComponent == null) {
            throw new IllegalStateException("AppComponent must be initialized");
        }
        this.searchSubcomponent = appComponent.searchSubcomponent();
    }

    public void initTitlesSubcomponent() {
        SearchSubcomponent searchSubcomponent = this.searchSubcomponent;
        if (searchSubcomponent == null) {
            initSearchSubcomponent();
        }
        this.titlesSubcomponent = searchSubcomponent.titlesSubcomponent();
    }

    public void initTitleSubcomponent() {
        TitlesSubcomponent titlesSubcomponent = this.titlesSubcomponent;
        if (titlesSubcomponent == null) {
            initTitlesSubcomponent();
        }
        this.titleSubcomponent = titlesSubcomponent.titleSubcomponent();
    }

    public void releaseSearchSubcomponent() {
        searchSubcomponent = null;
    }
    public void releaseTitlesSubcomponent() {
        titlesSubcomponent = null;
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

    public TitlesSubcomponent getTitlesSubcomponent() {
        if (titlesSubcomponent == null) {
            initTitlesSubcomponent();
        }
        return titlesSubcomponent;
    }

    public TitleSubcomponent getTitleSubcomponent() {
        if (titleSubcomponent == null) {
            initTitleSubcomponent();
        }
        return titleSubcomponent;
    }
}