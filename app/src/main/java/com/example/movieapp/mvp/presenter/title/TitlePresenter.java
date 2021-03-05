package com.example.movieapp.mvp.presenter.title;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.search.data.SearchResult;
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

    private final SearchResult searchResult;
    private String imageUrl;

    public TitlePresenter(SearchResult searchResult) {
        this.searchResult = searchResult;
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
        titleRepo.getTitle(searchResult.getId()).observeOn(scheduler).subscribe(
                (title) -> {
                    getViewState().setData(
                            title.getName(),
                            title.getImageUrl(),
                            title.getType(),
                            title.getYear(),
                            title.getCountry(),
                            title.getDirector(),
                            title.getRating(),
                            title.getPlot()
                    );
                    imageUrl = title.getImageUrl();
                },
                (e) -> {
                    showVerboseLog(this, "setData.onError " + e.getMessage());
                }
        );
    }

    public void onImageClick() {
        if (imageUrl != null) {
            router.navigateTo(new Screens.PosterScreen(imageUrl));
        }
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}