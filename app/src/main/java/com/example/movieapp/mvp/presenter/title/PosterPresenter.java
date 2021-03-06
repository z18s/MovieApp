package com.example.movieapp.mvp.presenter.title;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.view.title.IPosterView;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class PosterPresenter extends MvpPresenter<IPosterView> implements ILogger {

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
        getViewState().init();
        setData();
    }

    private void setData() {
        showVerboseLog(this, "setData");
        getViewState().setImage(imageUrl);
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}