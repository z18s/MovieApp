package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.TagConstants;
import com.example.movieapp.mvp.model.search.data.SearchResult;
import com.example.movieapp.mvp.presenter.title.TitlePresenter;
import com.example.movieapp.mvp.view.title.ITitleView;
import com.example.movieapp.utils.image.GlideImageLoader;
import com.example.movieapp.utils.image.IImageLoader;
import com.example.movieapp.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class TitleFragment extends MvpAppCompatFragment implements ITitleView, ILogger, BackButtonListener {

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
        showVerboseLog(this, "onCreateView");
        return view;
    }

    @Override
    public void init() {
        showVerboseLog(this, "init");
        nameTextView = view.findViewById(R.id.tv_title_name);
        posterImageView = view.findViewById(R.id.tv_title_poster);
        typeTextView = view.findViewById(R.id.tv_title_type);
        yearTextView = view.findViewById(R.id.tv_title_year);
        countryTextView = view.findViewById(R.id.tv_title_country);
        directorTextView = view.findViewById(R.id.tv_title_director);
        ratingTextView = view.findViewById(R.id.tv_title_rating);
        plotTextView = view.findViewById(R.id.tv_title_plot);
        initListeners();
    }

    private void initListeners() {
        posterImageView.setOnClickListener((view) -> {
            presenter.onImageClick();
        });
    }

    @Override
    public void setData(String name, String imageUrl, String type, String year, String country, String director, String rating, String plot) {
        showVerboseLog(this, "setData.nameTextView = " + nameTextView);
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

    private SearchResult getTitle() {
        return getArguments().getParcelable(TagConstants.TITLE_TAG);
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}