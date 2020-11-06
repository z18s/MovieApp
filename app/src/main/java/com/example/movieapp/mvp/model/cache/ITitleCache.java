package com.example.movieapp.mvp.model.cache;

import com.example.movieapp.mvp.model.entity.DetailedTitle;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface ITitleCache {
    Single<DetailedTitle> getTitle(String id);
    Completable putTitle(DetailedTitle detailedTitle);
}