package com.example.movieapp.di.search;

import com.example.movieapp.di.title.TitleSubcomponent;
import com.example.movieapp.mvp.presenter.search.SearchFieldPresenter;
import com.example.movieapp.mvp.presenter.search.SearchResultPresenter;

import dagger.Subcomponent;

@SearchScope
@Subcomponent(modules = SearchModule.class)
public interface SearchSubcomponent {
    TitleSubcomponent titleSubcomponent();

    void inject(SearchFieldPresenter searchFieldPresenter);
    void inject(SearchResultPresenter searchResultPresenter);
}