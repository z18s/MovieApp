package com.example.movieapp.mvp.presenter;

import com.example.movieapp.Logger;
import com.example.movieapp.MovieApp;
import com.example.movieapp.mvp.model.entity.BasicTitle;
import com.example.movieapp.mvp.model.repo.ITitleRepo;
import com.example.movieapp.mvp.view.ITitleView;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class TitlePresenter extends MvpPresenter<ITitleView> {

    private static final String TAG = TitlePresenter.class.getSimpleName();

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ITitleRepo titleRepo;

    private final BasicTitle basicTitle;

    public TitlePresenter(BasicTitle basicTitle) {
        this.basicTitle = basicTitle;
        MovieApp.instance.getTitleSubcomponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Logger.showLog(Logger.VERBOSE, TAG, "onFirstViewAttach");
        getViewState().init();
        setData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewState().release();
    }

    private void setData() {
        Logger.showLog(Logger.VERBOSE, TAG, "setData");
        titleRepo.getTitle(basicTitle.getId()).observeOn(scheduler).subscribe(
                (detailedTitle) -> {
                    getViewState().setData(
                            detailedTitle.getName(),
                            detailedTitle.getImageUrl(),
                            detailedTitle.getType(),
                            detailedTitle.getYear(),
                            detailedTitle.getCountry(),
                            detailedTitle.getDirector(),
                            detailedTitle.getRating(),
                            detailedTitle.getPlot()
                    );
                },
                (e) -> {
                    Logger.showLog(Logger.VERBOSE, TAG, "setData.onError " + e.getMessage());
                }
        );
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}