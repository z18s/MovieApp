package com.example.movieapp.ui.fragment;

import static com.example.movieapp.mvp.model.base.SearchConstants.BLANK_STRING;
import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieapp.R;
import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.base.TagConstants;
import com.example.movieapp.mvp.presenter.title.TitlePresenter;
import com.example.movieapp.mvp.view.title.ITitleView;
import com.example.movieapp.ui.BackButtonListener;
import com.example.movieapp.utils.image.GlideImageLoader;
import com.example.movieapp.utils.image.IImageLoader;

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
    private ImageView favoriteImageView;
    private ImageView userRatingImageView;
    private TextView userRatingTextView;

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
        initViews();
        initListeners();
        showVerboseLog(this, "init");
    }

    private void initViews() {
        nameTextView = view.findViewById(R.id.tv_title_name);
        posterImageView = view.findViewById(R.id.iv_title_poster);
        typeTextView = view.findViewById(R.id.tv_title_type);
        yearTextView = view.findViewById(R.id.tv_title_year);
        countryTextView = view.findViewById(R.id.tv_title_country);
        directorTextView = view.findViewById(R.id.tv_title_director);
        ratingTextView = view.findViewById(R.id.tv_title_rating);
        plotTextView = view.findViewById(R.id.tv_title_plot);
        favoriteImageView = view.findViewById(R.id.iv_title_favorite);
        userRatingImageView = view.findViewById(R.id.iv_title_user_rating_icon);
        userRatingTextView = view.findViewById(R.id.tv_title_user_rating);
    }

    private void initListeners() {
        posterImageView.setOnClickListener((view) -> {
            presenter.onPosterClick();
        });
        favoriteImageView.setOnClickListener((view) -> {
            presenter.onHeartClick();
        });
        userRatingImageView.setOnClickListener((view) -> {
            showRatingPopupMenu();
        });
    }

    private void showRatingPopupMenu() {
        PopupMenu ratingPopupMenu = new PopupMenu(requireContext(), userRatingImageView);
        ratingPopupMenu.getMenuInflater().inflate(R.menu.menu_rating, ratingPopupMenu.getMenu());

        ratingPopupMenu.setOnMenuItemClickListener((item) -> {
            if (item.getTitle().toString().equals(BLANK_STRING)) {
                presenter.deleteUserRating();
            } else {
                presenter.setUserRating(item.getTitle().toString());
            }
            return true;
        });
        ratingPopupMenu.setOnDismissListener((menu) -> {
            menu.dismiss();
        });

        ratingPopupMenu.show();
    }

    @Override
    public void setData(String name, String imageUrl, String type, String year, String country, String director, String rating, String plot, boolean favoriteStatus, String userRating) {
        showVerboseLog(this, "setData");
        nameTextView.setText(name);
        imageLoader.loadImage(imageUrl, posterImageView);
        typeTextView.setText(type);
        yearTextView.setText(year);
        countryTextView.setText(country);
        directorTextView.setText(director);
        ratingTextView.setText(rating);
        plotTextView.setText(plot);
        setFavoriteIcon(favoriteStatus);
        setUserRating(userRating);
    }

    private void setFavoriteIcon(boolean favoriteStatus) {
        favoriteImageView.setImageResource((favoriteStatus) ? (R.drawable.ic_favorite_true) : (R.drawable.ic_favorite_false));
    }

    @Override
    public void updateFavoriteIcon(String titleName, boolean favoriteStatus) {
        showToast(titleName, favoriteStatus);
        favoriteImageView.startAnimation(getIconAnimation());
        setFavoriteIcon(favoriteStatus);
    }

    private void setUserRating(String userRating) {
        showVerboseLog(this, "setUserRating");
        if (userRating == null || userRating.equals(EMPTY_STRING)) {
            userRatingImageView.setImageResource(R.drawable.ic_rating_false);
            userRatingTextView.setText(EMPTY_STRING);
        } else {
            userRatingImageView.setImageResource(R.drawable.ic_rating_true);
            userRatingTextView.setText(userRating);
        }
    }

    @Override
    public void updateUserRatingIcon(String titleName, String userRating) {
        showToast(titleName, userRating);
        userRatingImageView.startAnimation(getIconAnimation());
        setUserRating(userRating);
    }

    private void showToast(String titleName, boolean favoriteStatus) {
        Toast.makeText(requireContext(), String.format("«%s» %s Favorites", titleName, (favoriteStatus) ? "added to" : "removed from"), Toast.LENGTH_SHORT).show();
    }

    private void showToast(String titleName, String userRating) {
        if (userRating == null || userRating.equals(EMPTY_STRING) || userRating.equals(BLANK_STRING)) {
            Toast.makeText(requireContext(), String.format("«%s» unrated", titleName), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), String.format("«%s» rated on %s", titleName, userRating), Toast.LENGTH_SHORT).show();
        }
    }

    private Animation getIconAnimation() {
        return AnimationUtils.loadAnimation(this.requireContext(), android.R.anim.fade_in);
    }

    @Override
    public void release() {
        MovieApp.instance.releaseTitleSubcomponent();
    }

    private String getTitle() {
        if (getArguments() != null) {
            String arg = getArguments().getString(TagConstants.TITLE_TAG);
            if (arg != null) {
                return arg;
            }
        }
        return EMPTY_STRING;
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}