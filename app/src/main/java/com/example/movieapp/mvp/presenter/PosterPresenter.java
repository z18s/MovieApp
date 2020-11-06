package com.example.movieapp.mvp.presenter;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.mvp.view.IPosterView;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class PosterPresenter extends MvpPresenter<IPosterView> {

    private static final String TAG = PosterPresenter.class.getSimpleName();

    @Inject
    Router router;

    private final String imageUrl;

    public PosterPresenter(String imageUrl) {
        this.imageUrl = imageUrl;
        MovieApp.instance.getTitleSubcomponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Logger.showLog(Logger.VERBOSE, TAG, "onFirstViewAttach");
        getViewState().init();
        setData();
    }

    private void setData() {
        Logger.showLog(Logger.VERBOSE, TAG, "setData");
        getViewState().setImage(imageUrl);
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}