package com.example.movieapp.mvp.model.base.api;

import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.mvp.model.search.data.Search;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDataSource {

    @GET(ApiKey.KEY)
    Single<Search> getSearch(@Query("s") String query);

    @GET(ApiKey.KEY)
    Single<Title> getTitle(@Query("i") String id);
}