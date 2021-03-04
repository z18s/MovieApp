package com.example.movieapp.mvp.view.search.list;

import com.example.movieapp.mvp.view.base.list.IItemView;

public interface ISearchResultItemView extends IItemView {
    void setName(String titleName);
    void loadImage(String imageUrl);
    void setType(String titleType);
    void setYear(String year);
}