package com.example.movieapp.mvp.view.title;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface ITitleView extends MvpView {
    void init();
    void setData(String name, String imageUrl, String type, String year, String country, String director, String rating, String plot, boolean favorite, String userRating);
    void updateFavoriteIcon(String titleName, boolean favoriteStatus);
    public void updateUserRatingIcon(String titleName, String userRating);
    void release();
}