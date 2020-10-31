package com.example.movieapp.mvp.model.repo;

import com.example.movieapp.mvp.model.entity.DetailedTitle;

import io.reactivex.rxjava3.core.Single;

public interface ITitleRepo {
    Single<DetailedTitle> getTitle(String id);
}