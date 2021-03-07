package com.example.movieapp.mvp.view.title;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface IFavoritesView extends MvpView {
    void init();
    void updateFavoritesData();
}