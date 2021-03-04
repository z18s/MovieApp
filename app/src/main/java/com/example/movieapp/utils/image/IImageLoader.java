package com.example.movieapp.utils.image;

public interface IImageLoader<T> {
    void loadImage(String url, T container);
}