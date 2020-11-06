package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.R;
import com.example.movieapp.mvp.model.Tags;
import com.example.movieapp.mvp.model.entity.BasicTitle;
import com.example.movieapp.mvp.presenter.TitlePresenter;
import com.example.movieapp.mvp.view.ITitleView;
import com.example.movieapp.mvp.view.image.GlideImageLoader;
import com.example.movieapp.mvp.view.image.IImageLoader;
import com.example.movieapp.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class TitleFragment extends MvpAppCompatFragment implements ITitleView, BackButtonListener {

    private static final String TAG = TitleFragment.class.getSimpleName();

    private static final IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    private View view;
    private TextView nameTextView;
    private ImageView posterImageView;
    private TextView typeTextView;
    private TextView yearTextView;
    private TextView countryTextView;
    private TextView directorTextView;
    private TextView ratingTextView;
    private TextView plotTextView;

    @InjectPresenter
    TitlePresenter presenter;

    @ProvidePresenter
    TitlePresenter provideTitlePresenter() {
        return new TitlePresenter(getTitle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_title, container, false);
        Logger.showLog(Logger.VERBOSE, TAG, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        Logger.showLog(Logger.VERBOSE, TAG, "init");
        nameTextView = view.findViewById(R.id.name_title);
        posterImageView = view.findViewById(R.id.poster_title);
        typeTextView = view.findViewById(R.id.type_title);
        yearTextView = view.findViewById(R.id.year_title);
        countryTextView = view.findViewById(R.id.country_title);
        directorTextView = view.findViewById(R.id.director_title);
        ratingTextView = view.findViewById(R.id.rating_title);
        plotTextView = view.findViewById(R.id.plot_title);
        initListeners();
    }

    private void initListeners() {
        posterImageView.setOnClickListener((view) -> {
            presenter.onImageClick();
        });
    }

    @Override
    public void setData(String name, String imageUrl, String type, String year, String country, String director, String rating, String plot) {
        Logger.showLog(Logger.VERBOSE, TAG, "setData.nameTextView = " + nameTextView);
        nameTextView.setText(name);
        imageLoader.loadImage(imageUrl, posterImageView);
        typeTextView.setText(type);
        yearTextView.setText(year);
        countryTextView.setText(country);
        directorTextView.setText(director);
        ratingTextView.setText(rating);
        plotTextView.setText(plot);
    }

    @Override
    public void release() {
        MovieApp.instance.releaseTitleSubcomponent();
    }

    private BasicTitle getTitle() {
        return getArguments().getParcelable(Tags.TITLE_TAG);
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}