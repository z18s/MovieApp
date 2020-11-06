package com.example.movieapp.di.title;

import com.example.movieapp.mvp.presenter.PosterPresenter;
import com.example.movieapp.mvp.presenter.TitlePresenter;

import dagger.Subcomponent;

@TitleScope
@Subcomponent(modules = TitleModule.class)
public interface TitleSubcomponent {

    void inject(TitlePresenter titlePresenter);
    void inject(PosterPresenter posterPresenter);
}