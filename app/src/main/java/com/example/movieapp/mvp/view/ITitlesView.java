package com.example.movieapp.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface ITitlesView extends MvpView {
    void init();
    void updateData();
    void release();
}