package com.example.movieapp.mvp.presenter;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.presenter.button.ISearchButtonPresenter;
import com.example.movieapp.mvp.view.ISearchView;
import com.example.movieapp.navigation.Screens;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class SearchPresenter extends MvpPresenter<ISearchView> {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private final Router router;

    public SearchPresenter(Router router) {
        this.router = router;
    }

    private class SearchButtonPresenter implements ISearchButtonPresenter {

        @Override
        public void onClick(String query) {
            router.navigateTo(new Screens.TitlesScreen(query));
        }
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Logger.showLog(Logger.VERBOSE, TAG, "onFirstViewAttach");
        getViewState().init();
    }

    private final SearchPresenter.SearchButtonPresenter searchButtonPresenter = new SearchPresenter.SearchButtonPresenter();

    public ISearchButtonPresenter getPresenter() {
        return searchButtonPresenter;
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}