package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.TagConstants;
import com.example.movieapp.mvp.presenter.title.PosterPresenter;
import com.example.movieapp.mvp.view.title.IPosterView;
import com.example.movieapp.utils.image.GlideImageLoader;
import com.example.movieapp.utils.image.IImageLoader;
import com.example.movieapp.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class PosterFragment extends MvpAppCompatFragment implements IPosterView, ILogger, BackButtonListener {

    private static final String TAG = PosterFragment.class.getSimpleName();

    private static final IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    private View view;
    private ImageView posterImageView;

    @InjectPresenter
    PosterPresenter presenter;

    @ProvidePresenter
    PosterPresenter providePosterPresenter() {
        return new PosterPresenter(getImageUrl());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_poster, container, false);
        showVerboseLog(TAG, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        showVerboseLog(TAG, "init");
        posterImageView = view.findViewById(R.id.image_poster);
    }

    @Override
    public void setImage(String imageUrl) {
        showVerboseLog(TAG, "setData");
        imageLoader.loadImage(imageUrl, posterImageView);
    }

    private String getImageUrl() {
        return getArguments().getString(TagConstants.POSTER_TAG);
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}