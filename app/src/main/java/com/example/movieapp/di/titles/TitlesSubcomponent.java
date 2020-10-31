package com.example.movieapp.di.titles;

import com.example.movieapp.di.title.TitleSubcomponent;
import com.example.movieapp.mvp.presenter.TitlesPresenter;

import dagger.Subcomponent;

@TitlesScope
@Subcomponent(modules = TitlesModule.class)
public interface TitlesSubcomponent {
    TitleSubcomponent titleSubcomponent();

    void inject(TitlesPresenter titlesPresenter);
}