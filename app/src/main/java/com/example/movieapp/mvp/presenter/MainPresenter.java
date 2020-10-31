package com.example.movieapp.mvp.presenter;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.mvp.view.IMainView;
import com.example.movieapp.navigation.Screens;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<IMainView> {

    private static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    Router router;

    public MainPresenter () {
        super();
        MovieApp.instance.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Logger.showLog(Logger.VERBOSE, TAG, "replaceScreen.TitlesScreen");
        router.replaceScreen(new Screens.SearchScreen());
    }

    public void backClicked() {
        router.exit();
    }
}