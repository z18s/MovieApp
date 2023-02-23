package com.example.movieapp.mvp.presenter.title;

import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.title.repo.ITitleRepo;
import com.example.movieapp.mvp.view.title.ITitleView;
import com.example.movieapp.navigation.Screens;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class TitlePresenter extends MvpPresenter<ITitleView> implements ILogger {

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ITitleRepo titleRepo;

    private final String titleId;
    private String titleName;
    private String imageUrl;
    private boolean favoriteStatus;
    private String userRating;

    public TitlePresenter(String titleId) {
        this.titleId = titleId;
        MovieApp.instance.getTitleSubcomponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        setData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewState().release();
    }

    private void setData() {
        showVerboseLog(this, "setData");
        if (!titleId.equals(EMPTY_STRING)) {
            titleRepo.getTitle(titleId).observeOn(scheduler).subscribe(
                    (title) -> {
                        getViewState().setData(
                                title.getName(),
                                title.getImageUrl(),
                                title.getType(),
                                title.getYear(),
                                title.getCountry(),
                                title.getDirector(),
                                title.getRating(),
                                title.getPlot(),
                                title.isFavorite(),
                                title.getUserRating()
                        );
                        titleName = title.getName();
                        imageUrl = title.getImageUrl();
                        favoriteStatus = title.isFavorite();
                        userRating = title.getUserRating();
                    },
                    (e) -> {
                        showVerboseLog(this, "setData.onError " + e.getMessage());
                    }
            );
        }
    }

    public void onPosterClick() {
        if (imageUrl != null) {
            router.navigateTo(new Screens.PosterScreen(imageUrl));
        }
    }

    public void onHeartClick() {
        if (favoriteStatus) {
            titleRepo.deleteFavorite(titleId).observeOn(scheduler).subscribe(this::updateFavoriteStatus);
        } else {
            titleRepo.putFavorite(titleId).observeOn(scheduler).subscribe(this::updateFavoriteStatus);
        }
    }

    public void updateFavoriteStatus() {
        favoriteStatus = !favoriteStatus;
        getViewState().updateFavoriteIcon(titleName, favoriteStatus);
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
        titleRepo.setUserRating(titleId, userRating).observeOn(scheduler).subscribe(this::updateUserRatingIcon);
    }

    public void deleteUserRating() {
        if (userRating != null) {
            userRating = null;
            titleRepo.deleteUserRating(titleId).observeOn(scheduler).subscribe(this::updateUserRatingIcon);
        }
    }

    public void updateUserRatingIcon() {
        getViewState().updateUserRatingIcon(titleName, userRating);
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}