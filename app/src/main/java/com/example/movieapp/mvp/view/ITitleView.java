package com.example.movieapp.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface ITitleView extends MvpView {
    void init();
    void setData(String name, String imageUrl, String type, String year, String country, String director, String rating, String plot);
    void release();
}