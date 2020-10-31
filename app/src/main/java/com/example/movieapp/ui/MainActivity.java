package com.example.movieapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.R;
import com.example.movieapp.mvp.presenter.MainPresenter;
import com.example.movieapp.mvp.view.IMainView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final NavigatorHolder navigatorHolder = MovieApp.instance.getNavigatorHolder();
    private final Navigator navigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.container);

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    MainPresenter provideMainPresenter() {
        Logger.showLog(Logger.VERBOSE, TAG, "provideMainPresenter");
        Router router = MovieApp.instance.getRouter();
        return new MainPresenter(router);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.showLog(Logger.VERBOSE, TAG, "onCreate");
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
        Logger.showLog(Logger.VERBOSE, TAG, "onResumeFragments");
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
        Logger.showLog(Logger.VERBOSE, TAG, "onPause");
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