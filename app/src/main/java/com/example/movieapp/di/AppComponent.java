package com.example.movieapp.di;

import com.example.movieapp.di.module.ApiModule;
import com.example.movieapp.di.module.AppModule;
import com.example.movieapp.di.module.CiceroneModule;
import com.example.movieapp.di.search.SearchSubcomponent;
import com.example.movieapp.mvp.presenter.MainPresenter;
import com.example.movieapp.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
                AppModule.class,
                ApiModule.class,
                CiceroneModule.class
})
public interface AppComponent {
    SearchSubcomponent searchSubcomponent();

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
}