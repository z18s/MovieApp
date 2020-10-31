package com.example.movieapp.mvp.model.repo.retrofit;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.entity.DetailedTitle;
import com.example.movieapp.mvp.model.repo.ITitleRepo;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitTitleRepo implements ITitleRepo {

    private static final String TAG = RetrofitTitleRepo.class.getSimpleName();

    private final IDataSource api;

    public RetrofitTitleRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<DetailedTitle> getTitle(String id) {
        Logger.showLog(Logger.VERBOSE, TAG, "getTitle");
        return api.getTitle(id).subscribeOn(Schedulers.io());
    }
}