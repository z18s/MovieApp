package com.example.movieapp.mvp.presenter.base;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.application.Settings;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.view.base.ISettingsView;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class SettingsPresenter extends MvpPresenter<ISettingsView> implements ILogger {

    @Inject
    Router router;

    public SettingsPresenter() {
        super();
        MovieApp.instance.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public void onDayNightModeButtonClick() {
        Settings.nightMode = !Settings.nightMode;
        getViewState().update(Settings.nightMode);
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}