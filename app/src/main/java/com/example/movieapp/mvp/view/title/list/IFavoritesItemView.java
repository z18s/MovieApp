package com.example.movieapp.mvp.view.title.list;

import com.example.movieapp.mvp.view.base.list.IItemView;

public interface IFavoritesItemView extends IItemView {
    void setPoster(String imageUrl);
    void setName(String titleName);
    void setYear(String year);
    void setCountry(String country);
    void setUserRating(String userRating);
}