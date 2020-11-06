package com.example.movieapp.di.search;

import com.example.movieapp.di.title.TitleSubcomponent;
import com.example.movieapp.mvp.presenter.SearchPresenter;
import com.example.movieapp.mvp.presenter.TitlesPresenter;

import dagger.Subcomponent;

@SearchScope
@Subcomponent(modules = SearchModule.class)
public interface SearchSubcomponent {
    TitleSubcomponent titleSubcomponent();

    void inject(SearchPresenter searchPresenter);
    void inject(TitlesPresenter titlesPresenter);
}