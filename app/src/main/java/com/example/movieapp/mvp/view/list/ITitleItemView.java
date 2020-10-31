package com.example.movieapp.mvp.view.list;

public interface ITitleItemView extends IItemView {
    void setName(String titleName);
    void loadImage(String imageUrl);
    void setType(String titleType);
    void setYear(String year);
}