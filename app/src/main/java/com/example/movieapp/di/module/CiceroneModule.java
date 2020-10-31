package com.example.movieapp.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class CiceroneModule {

    private final Cicerone<Router> cicerone = Cicerone.create();

    @Provides
    Cicerone<Router> cicerone() {
        return cicerone;
    }

    @Singleton
    @Provides
    NavigatorHolder navigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Singleton
    @Provides
    Router router() {
        return cicerone.getRouter();
    }
}