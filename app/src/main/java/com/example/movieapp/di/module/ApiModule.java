package com.example.movieapp.di.module;

import com.example.movieapp.mvp.model.api.IDataSource;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Named("baseUrl")
    @Provides
    String baseUrl() {
        return "https://www.omdbapi.com/";
    }

    @Singleton
    @Provides
    Gson gson() {
        return new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Singleton
    @Provides
    IDataSource api(@Named("baseUrl") String baseUrl, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(IDataSource.class);
    }
}