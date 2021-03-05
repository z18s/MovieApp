package com.example.movieapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.application.MovieApp;
import com.example.movieapp.R;
import com.example.movieapp.mvp.presenter.base.MainPresenter;
import com.example.movieapp.mvp.view.base.IMainView;

import javax.inject.Inject;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMainView, ILogger {

    @Inject
    NavigatorHolder navigatorHolder;
    Navigator navigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.container);

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieApp.instance.getAppComponent().inject(this);
        showVerboseLog(this, "onCreate");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
        showVerboseLog(this, "onResumeFragments");
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
        showVerboseLog(this, "onPause");
    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BackButtonListener && ((BackButtonListener) fragment).backPressed()) {
                return;
            }
        }
        presenter.backClicked();
    }
}