package com.example.movieapp.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface IPosterView extends MvpView {
    void init();
    void setImage(String imageUrl);
}