package com.example.movieapp.di.module;

import androidx.room.Room;

import com.example.movieapp.MovieApp;
import com.example.movieapp.mvp.model.entity.room.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    Database database() {
        return Room.databaseBuilder(MovieApp.instance, Database.class, Database.DB_NAME)
                .build();
    }
}