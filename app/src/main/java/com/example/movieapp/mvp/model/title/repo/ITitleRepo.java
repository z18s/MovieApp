package com.example.movieapp.mvp.model.title.repo;

import com.example.movieapp.mvp.model.title.data.Title;

import io.reactivex.rxjava3.core.Single;

public interface ITitleRepo {
    Single<Title> getTitle(String id);
}