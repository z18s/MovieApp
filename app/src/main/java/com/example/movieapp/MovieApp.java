package com.example.movieapp;

import android.app.Application;

import com.example.movieapp.mvp.model.api.IDataSource;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class MovieApp extends Application {

    public static MovieApp instance;

    private ApiHolder apiHolder;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        cicerone = Cicerone.create();
        apiHolder = new ApiHolder();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public IDataSource getApi() {
        return apiHolder.getDataSource();
    }
}