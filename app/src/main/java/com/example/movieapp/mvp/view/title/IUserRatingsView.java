package com.example.movieapp.mvp.view.title;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface IUserRatingsView extends MvpView {
    void init();
    void updateUserRatingsData();
}