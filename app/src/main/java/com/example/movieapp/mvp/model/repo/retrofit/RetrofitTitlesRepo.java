package com.example.movieapp.mvp.model.repo.retrofit;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.model.api.IDataSource;
import com.example.movieapp.mvp.model.entity.Search;
import com.example.movieapp.mvp.model.repo.ITitlesRepo;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitTitlesRepo implements ITitlesRepo {

    private static final String TAG = RetrofitTitlesRepo.class.getSimpleName();

    private final IDataSource api;

    public RetrofitTitlesRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<Search> getTitles(String query) {
        Logger.showLog(Logger.VERBOSE, TAG, "getTitles");
        return api.getTitles(query).subscribeOn(Schedulers.io());
    }
}