package com.example.movieapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.movieapp.R;
import com.example.movieapp.application.MovieApp;
import com.example.movieapp.application.Settings;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.SettingsConstants;
import com.example.movieapp.mvp.presenter.base.MainPresenter;
import com.example.movieapp.mvp.view.base.IMainView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;

    @InjectPresenter
    MainPresenter presenter;
    @Inject
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieApp.instance.getAppComponent().inject(this);
        actionBar = getSupportActionBar();

        initSettings();
        initActionBar();
        initViews();
        initListeners();
        showVerboseLog(this, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.menu_main_settings):
                presenter.openSettingsScreen();
                break;
            case (R.id.menu_main_exit):
                presenter.exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSettings() {
        Settings.nightMode = preferences.getBoolean(SettingsConstants.NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(Settings.nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void initActionBar() {
        actionBar.setTitle(R.string.app_name);
        actionBar.setSubtitle(R.string.menu_search);
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case (R.id.menu_bn_search):
                    presenter.moveToSearchScreen();
                    actionBar.setSubtitle(R.string.menu_search);
                    item.setChecked(true);
                    break;
                case (R.id.menu_bn_favorites):
                    presenter.moveToFavoritesScreen();
                    actionBar.setSubtitle(R.string.menu_favorites);
                    item.setChecked(true);
                    break;
                case (R.id.menu_bn_ratings):
                    presenter.moveToUserRatingsScreen();
                    actionBar.setSubtitle(R.string.menu_ratings);
                    item.setChecked(true);
                    break;
            }
            return false;
        });
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
        showVerboseLog(this, "onResumeFragments");
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
        preferences.edit().putBoolean(SettingsConstants.NIGHT_MODE, Settings.nightMode).apply();
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