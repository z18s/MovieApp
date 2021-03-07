package com.example.movieapp.mvp.presenter.title;

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
    private String imageUrl;
    private boolean favoriteStatus;

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
                            title.isFavorite()
                    );
                    imageUrl = title.getImageUrl();
                    favoriteStatus = title.isFavorite();
                },
                (e) -> {
                    showVerboseLog(this, "setData.onError " + e.getMessage());
                }
        );
    }

    public void onPosterClick() {
        if (imageUrl != null) {
            router.navigateTo(new Screens.PosterScreen(imageUrl));
        }
    }

    public void onStarClick() {
        if (favoriteStatus) {
            titleRepo.deleteFavorite(titleId).observeOn(scheduler).subscribe(this::updateFavoriteStatus);
        } else {
            titleRepo.putFavorite(titleId).observeOn(scheduler).subscribe(this::updateFavoriteStatus);
        }
    }

    public void updateFavoriteStatus() {
        favoriteStatus = !favoriteStatus;
        getViewState().updateFavoriteIcon(favoriteStatus);
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}