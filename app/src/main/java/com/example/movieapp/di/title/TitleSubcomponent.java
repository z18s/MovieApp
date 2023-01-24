package com.example.movieapp.di.title;

import com.example.movieapp.mvp.presenter.title.FavoritesPresenter;
import com.example.movieapp.mvp.presenter.title.PosterPresenter;
import com.example.movieapp.mvp.presenter.title.TitlePresenter;
import com.example.movieapp.mvp.presenter.title.UserRatingsPresenter;

import dagger.Subcomponent;

@TitleScope
@Subcomponent(modules = TitleModule.class)
public interface TitleSubcomponent {

    void inject(TitlePresenter titlePresenter);
    void inject(PosterPresenter posterPresenter);
    void inject(FavoritesPresenter favoritesPresenter);
    void inject(UserRatingsPresenter userRatingsPresenter);
}