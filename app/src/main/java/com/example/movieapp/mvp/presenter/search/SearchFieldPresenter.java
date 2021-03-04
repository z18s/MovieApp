package com.example.movieapp.mvp.presenter.search;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.mvp.presenter.search.button.ISearchButtonPresenter;
import com.example.movieapp.mvp.view.search.ISearchFieldView;
import com.example.movieapp.navigation.Screens;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class SearchFieldPresenter extends MvpPresenter<ISearchFieldView> {

    @Inject
    Router router;

    public SearchFieldPresenter() {
        MovieApp.instance.getSearchSubcomponent().inject(this);
    }

    private class SearchButtonPresenter implements ISearchButtonPresenter {

        @Override
        public void onClick(String query) {
            router.navigateTo(new Screens.SearchResultScreen(query));
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewState().release();
    }

    private final SearchFieldPresenter.SearchButtonPresenter searchButtonPresenter = new SearchFieldPresenter.SearchButtonPresenter();

    public ISearchButtonPresenter getPresenter() {
        return searchButtonPresenter;
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}