package com.example.movieapp.mvp.model.title.cache;

import com.example.movieapp.mvp.model.title.data.Title;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ITitleCache {
    Single<Title> getTitle(String id);
    Completable putTitle(Title title);
}