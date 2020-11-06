package com.example.movieapp.mvp.model.api;

import com.example.movieapp.mvp.model.entity.DetailedTitle;
import com.example.movieapp.mvp.model.entity.Search;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDataSource {

    @GET(ApiKey.KEY)
    Single<Search> getSearch(@Query("s") String query);

    @GET(ApiKey.KEY)
    Single<DetailedTitle> getDetailedTitle(@Query("i") String id);
}