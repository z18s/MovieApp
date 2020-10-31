package com.example.movieapp.mvp.presenter;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.view.IMainView;
import com.example.movieapp.navigation.Screens;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<IMainView> {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private final Router router;

    public MainPresenter (Router router) {
        super();
        this.router = router;
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