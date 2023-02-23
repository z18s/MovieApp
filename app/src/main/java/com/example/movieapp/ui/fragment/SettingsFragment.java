package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.presenter.base.SettingsPresenter;
import com.example.movieapp.mvp.view.base.ISettingsView;
import com.example.movieapp.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class SettingsFragment extends MvpAppCompatFragment implements ISettingsView, ILogger, BackButtonListener {

    private View view;
    private Button dayNightModeButton;

    @InjectPresenter
    SettingsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        showVerboseLog(this, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        initViews();
        initListeners();
        showVerboseLog(this, "init");
    }

    private void initViews() {
        dayNightModeButton = view.findViewById(R.id.btn_settings_day_night_mode);
        showVerboseLog(this, "initViews");
    }

    private void initListeners() {
        dayNightModeButton.setOnClickListener((view) -> {
            presenter.onDayNightModeButtonClick();
        });
    }

    @Override
    public void update(boolean nightModeStatus) {
        AppCompatDelegate.setDefaultNightMode(nightModeStatus ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        showVerboseLog(this, "update");
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}