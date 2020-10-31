package com.example.movieapp.mvp.presenter.list;

import com.example.movieapp.mvp.view.list.IItemView;

public interface IListPresenter <V extends IItemView> {
    void onItemClick(V view);
    void bindView(V view);
    int getCount();
}