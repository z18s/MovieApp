package com.example.movieapp.mvp.presenter.base;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.mvp.view.base.IMainView;
import com.example.movieapp.navigation.Screens;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<IMainView> {

    @Inject
    Router router;

    public MainPresenter () {
        super();
        MovieApp.instance.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        router.replaceScreen(new Screens.SearchScreen());
    }

    public void backClicked() {
        router.exit();
    }
}