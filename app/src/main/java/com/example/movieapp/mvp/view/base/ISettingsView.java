package com.example.movieapp.mvp.view.base;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface ISettingsView extends MvpView {
    void init();
    void update(boolean nightModeStatus);
}