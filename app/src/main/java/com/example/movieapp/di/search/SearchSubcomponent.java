package com.example.movieapp.di.search;

import com.example.movieapp.di.titles.TitlesSubcomponent;
import com.example.movieapp.mvp.presenter.SearchPresenter;

import dagger.Subcomponent;

@SearchScope
@Subcomponent(modules = SearchModule.class)
public interface SearchSubcomponent {
    TitlesSubcomponent titlesSubcomponent();

    void inject(SearchPresenter searchPresenter);

}